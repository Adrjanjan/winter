package pl.design.patterns.winter.inheritance.mappers;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public interface InheritanceMapper {

    <T> InheritanceMapping<T> map(Class<T> clazz);
}
