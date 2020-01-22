package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.DeleteQuery;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class DeleteExecutor {

    @Autowired
    private DataSource dataSource;

    public <T> ResultSet delete(int id, Class<T> clazz, InheritanceMapping inheritanceMapping)
    {
        String query = DeleteQuery.prepareDelete(id, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            log.info("Wykonuje delete gdzie ("+id+")");
            stmt.executeUpdate(query);
            return stmt.getResultSet();

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać  delete gdzie id =("+id+")");
            throw new RuntimeException(e);
        }
    }
}
