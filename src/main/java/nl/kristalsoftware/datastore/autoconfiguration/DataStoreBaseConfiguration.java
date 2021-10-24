package nl.kristalsoftware.datastore.autoconfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "nl.kristalsoftware.datastore.base")
@EnableJpaRepositories(basePackages = "nl.kristalsoftware.datastore.base")
@EntityScan(basePackages = "nl.kristalsoftware.datastore.base")
@Configuration
public class DataStoreBaseConfiguration {
}
