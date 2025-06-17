package org.example.app.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;
@Configuration
@ComponentScan("org.example.app")
@PropertySource("classpath:application.properties")
public class AppContext {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource =
                new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("dbUrl"));
        driverManagerDataSource.setUsername(environment.getProperty("dbUser"));
        driverManagerDataSource.setPassword(environment.getProperty("dbPass"));
        driverManagerDataSource.setDriverClassName(
                Objects.requireNonNull(environment.getProperty("jdbcDriver")));
        return driverManagerDataSource;
    }
    @Bean(initMethod = "migrate") // triggers flyway.migrate() on startup
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure().baselineOnMigrate(true).schemas("public")
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();
    }
}
