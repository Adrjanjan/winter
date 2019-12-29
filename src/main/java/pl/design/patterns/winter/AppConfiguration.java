package pl.design.patterns.winter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@ComponentScan("pl.design.patterns.winter")
public class AppConfiguration {
}
