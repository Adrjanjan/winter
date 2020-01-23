package pl.design.patterns.winter.dao;

import lombok.Data;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.object.assembler.ObjectAssembler;
import pl.design.patterns.winter.statements.DeleteExecutor;
import pl.design.patterns.winter.statements.InsertExecutor;
import pl.design.patterns.winter.statements.SelectExecutor;
import pl.design.patterns.winter.statements.UpdateExecutor;

import java.util.List;

@Data
public class Dao<T> {

    private Class<T> clazz;
    private ObjectAssembler<T> objectAssembler;
    private InheritanceMapping inheritanceMapping;
    private InsertExecutor insertExecutor;
    private SelectExecutor selectExecutor;
    private UpdateExecutor updateExecutor;
    private DeleteExecutor deleteExecutor;

    public Dao(Class<T> clazz, InheritanceMapping inheritanceMapping) {
        this.clazz = clazz;
        this.inheritanceMapping = inheritanceMapping;
        this.insertExecutor = new InsertExecutor();
        this.insertExecutor.setInheritanceMapping(inheritanceMapping);
        this.selectExecutor = new SelectExecutor();
        this.selectExecutor.setInheritanceMapping(inheritanceMapping);
        this.updateExecutor = new UpdateExecutor();
        this.updateExecutor.setInheritanceMapping(inheritanceMapping);
        this.deleteExecutor = new DeleteExecutor();
        this.deleteExecutor.setInheritanceMapping(inheritanceMapping);
    }

    public void insert(T obj) {
        insertExecutor.execute(obj);
    }

    public List<T> findAll() {
        return objectAssembler.assembleMultiple(clazz, selectExecutor.findAll(clazz));
    }

    public T findById(int id) {
        return objectAssembler.assemble(clazz, selectExecutor.findById(id, clazz));
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
