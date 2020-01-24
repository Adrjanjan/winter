package pl.design.patterns.winter.statements;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import pl.design.patterns.winter.exceptions.CouldNotSelectFromTableException;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.object.assembler.ObjectAssembler;
import pl.design.patterns.winter.query.QueryBuildDirector;
import pl.design.patterns.winter.query.QueryBuilder;
import pl.design.patterns.winter.query.SelectQueryBuilder;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class SelectExecutor {

    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    private ObjectAssembler objectAssembler;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public SelectExecutor(DataSource dataSource, InheritanceMapping inheritanceMapping, ObjectAssembler objectAssembler) {
        this.dataSource = dataSource;
        this.inheritanceMapping = inheritanceMapping;
        this.objectAssembler = objectAssembler;
    }

    @SuppressWarnings("unchecked")
    public <T> T findById(int id, Class<T> clazz) {

        QueryBuilder builder = new SelectQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;

        try {
            query = queryBuildDirector.withObject((T)clazz).withCondition(id)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotSelectFromTableException(String.format("Could not select object with id %d class %s", id, clazz.getName()), e);
        }

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();

            log.info("Wykonano select findById(" + id + ")");
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();
            return (T) objectAssembler.assemble(clazz, resultSet);

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać selecta findById(" + id + ")");
            throw new RuntimeException(e);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> clazz) {
        QueryBuilder builder = new SelectQueryBuilder(inheritanceMapping);
        QueryBuildDirector<T> queryBuildDirector = new QueryBuildDirector<>(builder);
        String query;

        try {
            query = queryBuildDirector.withObject((T)clazz)
                    .build();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CouldNotSelectFromTableException(String.format("Could not select all objects class %s", clazz.getName()), e);
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            log.info("Executing select findAll with query: " + query);
            ResultSet resultSet = stmt.executeQuery(query);
            return objectAssembler.assembleMultiple(clazz, resultSet);
        } catch (SQLException e) {
            log.error("Failed to execute query findAll");
            throw new RuntimeException(e);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
