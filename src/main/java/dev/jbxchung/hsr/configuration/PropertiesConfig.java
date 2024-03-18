package dev.jbxchung.hsr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration("propertiesConfig")
@PropertySources({
        @PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class),
        @PropertySource(value = "classpath:application-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class),
        @PropertySource(value = "classpath:application-local.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
})
public class PropertiesConfig {
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
}
