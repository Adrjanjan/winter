package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;
import pl.design.patterns.winter.query.SelectQuery;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class SelectExecutor {

    @Autowired
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public ResultSet findById(int id, Class<?> clazz) {
        String query = SelectQuery.prepareFindById(id, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            log.info("Wykonano select findById(" + id + ")");
            return stmt.getResultSet();

        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać selecta findById("+id+")");
            throw new RuntimeException(e);
        }
    }

    public ResultSet findAll(Class<?> clazz) {
        String query = SelectQuery.prepareFindAll(clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            // executeQuery zwraca ResultSet
            log.info("Wykonuje select findAll");
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            log.error("Nie udalo sie wykonać selecta findAll");
            throw new RuntimeException(e);
        }
    }
}
