package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.DeleteQuery;
import pl.design.patterns.winter.query.UpdateQuery;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class UpdateExecutor {
    @Autowired
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> ResultSet update(T objectToUpdate, Class<T> clazz, InheritanceMapping inheritanceMapping) throws InvocationTargetException, IllegalAccessException {
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
