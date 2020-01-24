package pl.design.patterns.winter.statements;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.object.assembler.ObjectAssembler;
import pl.design.patterns.winter.query.SelectQuery;

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
        String query = SelectQuery.prepareFindById(id, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            log.info("Wykonano select findById(" + id + ")");
            ResultSet resultSet = stmt.getResultSet();
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
        SelectQuery selectQuery = new SelectQuery();
        String query = selectQuery.prepareFindAll(clazz, inheritanceMapping);

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
