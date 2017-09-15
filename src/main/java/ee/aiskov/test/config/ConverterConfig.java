package ee.aiskov.test.config;

import ee.aiskov.test.client.model.Client;
import ee.aiskov.test.client.request.AddOrUpdateClient;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;

@Configuration
public class ConverterConfig {
    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean() {
        return new DozerBeanMapperFactoryBean();
    }

    @Bean
    public BeanMappingBuilder addOrUpdateClientMapper() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(AddOrUpdateClient.class, Client.class, oneWay());
            }
        };
    }

}
