package pl.design.patterns.winter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//Nie jestem pewien czy nie powinno być ElementType.TYPE ...?
@Target(ElementType.FIELD)
public @interface JoinColumn {
    String name() default "";
    String referencedColumnName();
}
