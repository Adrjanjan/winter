package pl.design.patterns.winter.inheritance;

import pl.design.patterns.winter.inheritance.mappers.ClassTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.ConcreteTableInheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.InheritanceMapper;
import pl.design.patterns.winter.inheritance.mappers.SingleTableInheritanceMapper;

public enum InheritanceMappingType {
    SINGLE_TABLE(SingleTableInheritanceMapper.class), CONCRETE_TABLE(ConcreteTableInheritanceMapper.class), CLASS_TABLE(ClassTableInheritanceMapper.class);

    private final Class<? extends InheritanceMapper> mappingClass;

    InheritanceMappingType(Class<? extends InheritanceMapper> inheritanceClass) {
        this.mappingClass = inheritanceClass;
    }

    public Class<? extends InheritanceMapper> getMappingClass() {
        return mappingClass;
    }
}
