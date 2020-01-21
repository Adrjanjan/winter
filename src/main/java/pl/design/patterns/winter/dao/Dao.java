package pl.design.patterns.winter.dao;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

public class Dao<T> {
    private InheritanceMapping inheritanceMapping;

    public boolean add(T value) {
        return false;
    }

    public T findById(String s) {
        return null;
    }

}
