package pl.design.patterns.winter.dao;

import java.util.List;

import javax.sql.DataSource;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.object.assembler.ObjectAssembler;
import pl.design.patterns.winter.statements.executors.DeleteExecutor;
import pl.design.patterns.winter.statements.executors.InsertExecutor;
import pl.design.patterns.winter.statements.executors.SelectExecutor;
import pl.design.patterns.winter.statements.executors.UpdateExecutor;

public class Dao<T> {

    private Class<T> clazz;
    private InsertExecutor insertExecutor;
    private SelectExecutor selectExecutor;
    private UpdateExecutor updateExecutor;
    private DeleteExecutor deleteExecutor;

    public Dao(DataSource dataSource, Class<T> clazz, InheritanceMapping inheritanceMapping) {
        this.clazz = clazz;
        this.insertExecutor = new InsertExecutor(dataSource, inheritanceMapping);
        this.selectExecutor = new SelectExecutor(dataSource, inheritanceMapping, new ObjectAssembler<>());
        this.updateExecutor = new UpdateExecutor(dataSource, inheritanceMapping);
        this.deleteExecutor = new DeleteExecutor(dataSource, inheritanceMapping);
    }

    public void insert(T obj) {
        insertExecutor.execute(obj);
    }

    public List<T> findAll() {
        return selectExecutor.findAll(clazz);
    }

    public T findById(int id) {
        return selectExecutor.findById(id, clazz);
    }

    public void update(T obj) {
        updateExecutor.update(obj, clazz);
    }

    public void deleteById(int id) {
        deleteExecutor.delete(id, clazz);
    }

    //    public void delete(T obj) {
    //    }

}
