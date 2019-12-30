package pl.design.patterns.winter.inheritance.mapping;

import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritance;
import pl.design.patterns.winter.inheritance.mappers.ConcreteTableInheritance;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.SingleTableInheritance;

public enum InheritanceMappingType {
    SINGLE_TABLE(SingleTableInheritance.class), CONCRETE_TABLE(ConcreteTableInheritance.class), CLASS_TABLE(ClassTableInheritance.class);

    private final Class<? extends InheritanceMapper> mappingClass;

    InheritanceMappingType(Class<? extends InheritanceMapper> inheritanceClass) {
        this.mappingClass = inheritanceClass;
    }

    public Class<? extends InheritanceMapper> getMappingClass() {
        return mappingClass;
    }
}
