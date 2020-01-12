package pl.design.patterns.winter.dao;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.CreateExecutor;

public class Dao<T> {
    private InheritanceMapping inheritanceMapping;

    public boolean add(T value) {
        return false;
    }

    public T findById(String s) {
        return null;
    }

	public void createTable() {
        CreateExecutor createExecutor = new CreateExecutor(inheritanceMapping);
        if ( !createExecutor.tableExists() ) {
            createExecutor.create();
        }
	}
}
