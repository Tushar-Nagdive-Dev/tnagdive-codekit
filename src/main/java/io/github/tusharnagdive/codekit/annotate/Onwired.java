package io.github.tusharnagdive.codekit.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.CONSTRUCTOR,
        ElementType.METHOD
})
public @interface Onwired {

    // Allows developers to specify if it's okay for the dependency to be missing.
    boolean required() default true;
}
