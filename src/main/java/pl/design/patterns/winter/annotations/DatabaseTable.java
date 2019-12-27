package pl.design.patterns.winter.annotations;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mapping.SingleTableInheritance;

public @interface DatabaseTable  {
    Class<? extends InheritanceMapper> inheritanceMapper() default SingleTableInheritance.class;
}
