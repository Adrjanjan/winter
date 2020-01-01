package pl.design.patterns.winter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
ADNOTACJE:
    KnuckleBones: https://github.com/Iubalus/Knucklebones/tree/master/src/main/java/com/jubalrife/knucklebones/v1/annotation
    ORMLite: https://github.com/j256/ormlite-core/blob/master/src/main/java/com/j256/ormlite/field/DatabaseField.java
    Hibernate: https://github.com/hibernate/hibernate-orm/tree/master/hibernate-core/src/main/java/org/hibernate/annotations
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseField {
    //generalnie name powinno być defaultowo nazwą pola w klasie, ale w adnotacji tego nie ustawię bo default musi być constant
    //ORMLite ma tak samo z tego co widzę tu (https://github.com/j256/ormlite-core/blob/master/src/main/java/com/j256/ormlite/field/DatabaseField.java)
    String name() default "";
    boolean nullable() default true;
    //Czy to jest potrzebne skoro w JoinColumn mamy adnotacje o foreignKey ...?
    boolean foreignKey() default false;
}
