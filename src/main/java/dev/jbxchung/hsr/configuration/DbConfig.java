package dev.jbxchung.hsr.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@DependsOn("propertiesConfig")
public class DbConfig {
    Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @Value("${hsr.db.url}")
    private String dbUrl;

    @Value("${hsr.db.username}")
    private String dbUser;

    @Value("${hsr.db.pw.file:}")
    private String dbPwFile;

    @Bean()
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUser);

        String dbPass = null;
        try {
            // need to trim because in docker jvm this ends in a newline
            dbPass = Files.readString(Paths.get(dbPwFile)).trim();
        } catch (IOException e) {
            logger.error("Failed to get db pw from configured file path: {}", dbPwFile, e);
        }
        dataSourceBuilder.password(dbPass);

        return dataSourceBuilder.build();
    }
}
