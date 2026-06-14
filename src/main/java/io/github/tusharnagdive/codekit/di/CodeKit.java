package io.github.tusharnagdive.codekit.di;

public class CodeKit {

    private static DIContext globalContext = null;

    /**
     * The standard magic method. Scans the target's exact package.
     */
    public static void onWire(Object target) {
        // Defers to the overloaded method below
        onWire(target, target.getClass().getPackageName());
    }

    /**
     * The advanced magic method. Allows the developer to specify custom packages to scan.
     */
    public static void onWire(Object target, String... basePackages) {
        if (globalContext == null) {
            globalContext = new DIContext();

            // Scan all provided packages
            for (String pack : basePackages) {
                globalContext.scanAndRegister(pack);
            }
        }

        // Inject the dependencies
        globalContext.wire(target);
    }
}