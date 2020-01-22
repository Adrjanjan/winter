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

    public <T> ResultSet findById(int id, Class<T> clazz, InheritanceMapping inheritanceMapping)
    {
        String query = SelectQuery.prepareFindById(id, clazz, inheritanceMapping);

        try (Connection conn = dataSource.getConnection()) {

            Statement stmt = conn.createStatement();
            // executeQuery zwraca ResultSet
            log.info("Wykonuje select findById("+id+")");
            return stmt.executeQuery(query);

        } catch (SQLException e) {

            log.error("Nie udalo sie wykonać selecta findById("+id+")");
            throw new RuntimeException(e);
        }
    }

    public <T> ResultSet findAll(Class<T> clazz, InheritanceMapping inheritanceMapping){
        String query = SelectQuery.prepareFindAll( clazz, inheritanceMapping);

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
