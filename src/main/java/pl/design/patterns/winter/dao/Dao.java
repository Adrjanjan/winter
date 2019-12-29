package pl.design.patterns.winter.dao;

import lombok.Data;
import pl.design.patterns.winter.inheritance.mapping.TableType;

import java.util.List;

@Data
public class Dao<T> {

    TableType tableInfo;

    public Dao(TableType tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getClassName() {
        return tableInfo.getClassName().toString();
    }

    public int insert(T data) {
        return 1;
    }

    public int update(T data) {
        return 1;
    }

    public int delete(T data) {
        return 1;
    }

    public int deleteById(int id) {
        return 1;
    }

    public List<T> findAll() {
        return null;
    }

    public T findById(int id) {
        return null;
    }

    public void createTable() {

    }
}
