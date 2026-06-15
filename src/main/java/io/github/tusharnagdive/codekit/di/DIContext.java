package io.github.tusharnagdive.codekit.di;

import io.github.tusharnagdive.codekit.annotate.KitComponent;
import io.github.tusharnagdive.codekit.annotate.Onwired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DIContext {

    // 1. Stores active Singleton instances (Replaces the old 'registry')
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    // 2. Stores the blueprints (Class definitions) for everything we find during the scan
    private final Map<Class<?>, Class<?>> registeredClasses = new HashMap<>();

    /**
     * Registers an already-instantiated object into the context as a Singleton.
     */
    public void register(Object instance) {
        singletons.put(instance.getClass(), instance);
        registeredClasses.put(instance.getClass(), instance.getClass());
    }

    /**
     * Scans the target object for @Onwired and injects the matching dependencies.
     */
    public void wire(Object target) {
        Class<?> clazz = target.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Onwired.class)) {

                Object dependency = findDependency(field.getType());

                if (dependency != null) {
                    try {
                        field.setAccessible(true);
                        field.set(target, dependency);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to inject dependency: " + field.getName(), e);
                    }
                } else {
                    Onwired annotation = field.getAnnotation(Onwired.class);
                    if (annotation.required()) {
                        throw new RuntimeException("No dependency found for type: " + field.getType().getSimpleName());
                    }
                }
            }
        }
    }

    /**
     * Helper method to find a dependency. Automatically handles Singletons vs. Prototypes.
     */
    private Object findDependency(Class<?> requestedType) {
        Class<?> targetBlueprint = null;

        // 1. Try to find the blueprint: exact match first
        if (registeredClasses.containsKey(requestedType)) {
            targetBlueprint = registeredClasses.get(requestedType);
        } else {
            // 2. If no exact match, loop through blueprints to find an implementing class
            for (Class<?> blueprint : registeredClasses.keySet()) {
                if (requestedType.isAssignableFrom(blueprint)) {
                    targetBlueprint = blueprint;
                    break;
                }
            }
        }

        // If we didn't find a matching blueprint, fail gracefully
        if (targetBlueprint == null) {
            return null;
        }

        // 3. Return the Singleton if one was already built and stored
        if (singletons.containsKey(targetBlueprint)) {
            return singletons.get(targetBlueprint);
        }

        // 4. If it's a known blueprint but NOT in the singletons map, it's a Prototype!
        // We generate a brand new instance right here.
        try {
            return targetBlueprint.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Prototype for: " + targetBlueprint.getName(), e);
        }
    }

    /**
     * Reads the annotation to decide if it should be built now (Singleton) or later (Prototype).
     */
    public void registerType(Class<?> clazz) {
        KitComponent annotation = clazz.getAnnotation(KitComponent.class);

        // If someone registers without an annotation, assume true to keep old services safe
        boolean isSingleton = (annotation == null) || annotation.singleton();

        // Always store the blueprint so findDependency knows it exists
        registeredClasses.put(clazz, clazz);

        // If it's a singleton, build it right now and store it forever in memory
        if (isSingleton) {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                singletons.put(clazz, instance);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate Singleton class: " + clazz.getName(), e);
            }
        }
        // If it's a Prototype (singleton = false), we do nothing else here!
        // We just wait for findDependency() to call .newInstance() when it's needed.
    }

    /**
     * Scans a given package directory for any classes marked with @KitComponent.
     */
    public void scanAndRegister(String basePackage) {
        String path = basePackage.replace('.', '/');
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            java.net.URL resource = classLoader.getResource(path);

            if (resource == null) {
                throw new IllegalArgumentException("Package not found: " + basePackage);
            }

            // Using toURI() to protect against the "space in folder name" bug!
            java.io.File directory = new java.io.File(resource.toURI());

            if (directory.exists()) {
                // Call the recursive helper method
                findAndRegisterClasses(directory, basePackage);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan package: " + basePackage, e);
        }
    }

    /**
     * Helper method to recursively scan directories for .class files.
     */
    private void findAndRegisterClasses(java.io.File directory, String packageName) throws Exception {
        java.io.File[] files = directory.listFiles();
        if (files == null) return;

        for (java.io.File file : files) {
            if (file.isDirectory()) {
                // If it's a sub-folder, dive into it recursively!
                findAndRegisterClasses(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                // If it's a class file, register it
                String className = packageName + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                if (clazz.isAnnotationPresent(KitComponent.class)) {
                    registerType(clazz);
                }
            }
        }
    }
}