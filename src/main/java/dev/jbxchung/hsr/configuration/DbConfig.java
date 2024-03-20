package dev.jbxchung.hsr.configuration;

import dev.jbxchung.hsr.util.HostUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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

    @Value("${hsr.db.pw.override:}")
    private String dbPass;

    @Bean()
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUser);

        if (!HostUtils.isLocalhost()) {
            try {
                dbPass = Files.readString(Paths.get(dbPwFile));
            } catch (IOException e) {
                logger.error("Failed to get db pw from configured file path: {}", dbPwFile, e);
            }
        }
        dataSourceBuilder.password(dbPass);

        return dataSourceBuilder.build();
    }
}
