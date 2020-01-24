package pl.design.patterns.winter.statements.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.statements.query.UpdateQuery;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class UpdateExecutor {
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public UpdateExecutor(DataSource dataSource, InheritanceMapping inheritanceMapping) {
        this.dataSource = dataSource;
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> ResultSet update(T objectToUpdate, Class<T> clazz) {
        String query = UpdateQuery.prepareUpdate(objectToUpdate, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            log.info("Wykonuje update na : <tu moze dac ten obiekt>");
            stmt.executeUpdate(query);
            return stmt.getResultSet();

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać  update ");
            throw new RuntimeException(e);
        }
    }
}
