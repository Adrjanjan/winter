package pl.design.patterns.winter.dao;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.object.assembler.ObjectAssembler;
import pl.design.patterns.winter.statements.DeleteExecutor;
import pl.design.patterns.winter.statements.InsertExecutor;
import pl.design.patterns.winter.statements.SelectExecutor;
import pl.design.patterns.winter.statements.UpdateExecutor;

import javax.sql.DataSource;
import java.util.List;

public class Dao<T> {

    private Class<T> clazz;
    private ObjectAssembler<T> objectAssembler;
    private InsertExecutor insertExecutor;
    private SelectExecutor selectExecutor;
    private UpdateExecutor updateExecutor;
    private DeleteExecutor deleteExecutor;

    public Dao(DataSource dataSource, Class<T> clazz, InheritanceMapping inheritanceMapping) {
        this.clazz = clazz;
        this.objectAssembler = new ObjectAssembler<>();
        this.insertExecutor = new InsertExecutor(dataSource, inheritanceMapping);
        this.selectExecutor = new SelectExecutor(dataSource, inheritanceMapping, objectAssembler);
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
