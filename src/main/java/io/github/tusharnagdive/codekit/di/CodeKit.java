package io.github.tusharnagdive.codekit.di;

import io.github.tusharnagdive.codekit.kitcollection.baseview.UniChainImpl;

public class CodeKit {

    private static DIContext globalContext = null;

    public static void onWire(Object target) {
        onWire(target, target.getClass().getPackageName());
    }

    public static void onWire(Object target, String... basePackages) {
        if (globalContext == null) {
            globalContext = new DIContext();

            // 1. Manually register built-in CodeKit tools!
            // (This completely bypasses the .jar scanning crash)
            globalContext.registerType(UniChainImpl.class);

            // 2. Dynamically scan the consumer's app packages
            // (This still works perfectly because their app is not a .jar yet)
            for (String pack : basePackages) {
                globalContext.scanAndRegister(pack);
            }
        }

        globalContext.wire(target);
    }
}