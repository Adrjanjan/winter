package pl.design.patterns.winter.inheritance.mapping;

public interface InheritanceMapper {

    <T> InheritanceMapping<T> map(Class<T> clazz);
}
