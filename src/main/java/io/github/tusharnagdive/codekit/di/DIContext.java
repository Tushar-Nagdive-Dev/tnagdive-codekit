package io.github.tusharnagdive.codekit.di;

import io.github.tusharnagdive.codekit.annotate.KitComponent;
import io.github.tusharnagdive.codekit.annotate.Onwired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DIContext {

    private final Map<Class<?>, Object> registry = new HashMap<>();

    /**
     * Registers an object into the context so it can be injected elsewhere.
     */
    public void register(Object instance) {
        registry.put(instance.getClass(), instance);
    }

    /**
     * Scans the target object for @Onwired and injects the matching dependencies.
     *
     *
     * Scans the target object for @Onwired and injects the matching dependencies,
     * including matching interfaces to their implementations.
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
     * Helper method to find a dependency that either matches exactly OR implements the requested interface.
     */
    private Object findDependency(Class<?> requestedType) {
        // 1. Try an exact match first (it's faster)
        if (registry.containsKey(requestedType)) {
            return registry.get(requestedType);
        }

        // 2. If no exact match, loop through the registry to find an implementing class
        for (Map.Entry<Class<?>, Object> entry : registry.entrySet()) {
            // isAssignableFrom checks if entry.getKey() implements or extends requestedType
            if (requestedType.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null; // Nothing found
    }

    /**
     * Automatically creates an instance of a class and registers it.
     */
    public void registerType(Class<?> clazz) {
        try {
            // Automatically calls the default constructor: new MyClass()
            Object instance = clazz.getDeclaredConstructor().newInstance();
            register(instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), e);
        }
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

            java.io.File directory = new java.io.File(resource.getFile());

            if (directory.exists()) {
                for (java.io.File file : directory.listFiles()) {
                    if (file.getName().endsWith(".class")) {
                        // Rebuild the fully qualified class name
                        String className = basePackage + "." + file.getName().replace(".class", "");
                        Class<?> clazz = Class.forName(className);

                        // If it has our marker, instantiate and register it automatically!
                        if (clazz.isAnnotationPresent(KitComponent.class)) {
                            registerType(clazz);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan package: " + basePackage, e);
        }
    }
}
