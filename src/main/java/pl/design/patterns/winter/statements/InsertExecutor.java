package pl.design.patterns.winter.statements;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.design.patterns.winter.inheritance.mapping.InheritanceMapping;

import pl.design.patterns.winter.query.InsertQueryBuilder;
import pl.design.patterns.winter.query.QueryBuilder;
import pl.design.patterns.winter.schemas.DatabaseSchema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@CommonsLog
@Component
public class InsertExecutor {
    @Autowired
    private DataSource dataSource;

    private InheritanceMapping inheritanceMapping;

    public void setInheritanceMapping(InheritanceMapping inheritanceMapping) {
        this.inheritanceMapping = inheritanceMapping;
    }

    public <T> void execute(T object) {
        log.info("Insert klasy : " + object.getClass()
                .getName());
        QueryBuilder builder = new InsertQueryBuilder(inheritanceMapping);
        String query = builder.prepare(object);

        try (Connection conn = dataSource.getConnection()) {

            // TODO: mozna dodac najpierw sprawdzenie
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            log.info("Dodano obiekt klasy: " + object.getClass()
                    .toString());

        } catch (SQLException e) {

            log.error("Nie udalo sie dodać obiektu klasy " + object.getClass()
                    .toString());
            throw new RuntimeException(e);

        }
    }
}
