package pl.design.patterns.winter.annotations;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.SingleTableInheritance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DatabaseTable {

	String name() default "";
    Class<? extends InheritanceMapper> inheritanceMapper() default SingleTableInheritance.class;

}
