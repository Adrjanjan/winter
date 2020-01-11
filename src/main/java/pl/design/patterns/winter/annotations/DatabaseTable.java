package pl.design.patterns.winter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.SingleTableInheritanceMapper;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DatabaseTable {

	String name() default "";

    Class<? extends InheritanceMapper> inheritanceMapper() default SingleTableInheritanceMapper.class;

}
