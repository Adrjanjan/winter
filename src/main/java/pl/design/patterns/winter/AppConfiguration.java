package pl.design.patterns.winter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import pl.design.patterns.winter.schemas.DatabaseSchema;

@Configuration
@PropertySource("application.properties")
@ComponentScan("pl.design.patterns.winter")
public class AppConfiguration {

    @Bean
    public DatabaseSchema databaseScheme() {
        return new DatabaseSchema();
    }
}
