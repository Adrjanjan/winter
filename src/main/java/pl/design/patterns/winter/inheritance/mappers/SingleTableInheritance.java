package pl.design.patterns.winter.inheritance.mappers;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public class SingleTableInheritance implements InheritanceMapper {

    @Override
    public <T> InheritanceMapping<T> map(Class<T> clazz) {
        return null;
    }
}
