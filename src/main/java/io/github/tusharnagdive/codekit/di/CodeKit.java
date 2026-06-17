package io.github.tusharnagdive.codekit.di;

public class CodeKit {

    private static DIContext globalContext = null;

    public static void onWire(Object target) {
        onWire(target, target.getClass().getPackageName());
    }

    public static void onWire(Object target, String... basePackages) {
        if (globalContext == null) {
            globalContext = new DIContext();

            for (String pack : basePackages) {
                globalContext.scanAndRegister(pack);
            }
        }

        globalContext.wire(target);
    }
}