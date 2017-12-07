package com.github.wonwoo.dynamodb;

import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.socialsignin.spring.data.dynamodb.mapping.DynamoDBMappingContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@Configuration
@ConditionalOnClass(DynamoDBTemplate.class)
@AutoConfigureAfter({DynamoAutoConfiguration.class, EmbeddedDynamoAutoConfiguration.class})
public class DynamoDataAutoConfiguration {

  private final ApplicationContext applicationContext;

  public DynamoDataAutoConfiguration(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamoDBMappingContext dynamoDBMappingContext() throws ClassNotFoundException {
    DynamoDBMappingContext context = new DynamoDBMappingContext();
    context.setInitialEntitySet(new EntityScanner(this.applicationContext)
        .scan(DynamoDBTable.class));
    return context;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamoDbMapping dynamoDbMapping(AmazonDynamoDB amazonDynamoDB, DynamoDBMappingContext context) {
    return new DynamoDbMapping(amazonDynamoDB, context);
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamoDBTemplate dynamoDBTemplate(AmazonDynamoDB amazonDynamoDB,
                                           ObjectProvider<DynamoDBMapperConfig> dynamoDBMapperConfig) {
    return new DynamoDBTemplate(amazonDynamoDB,
        dynamoDBMapperConfig.getIfAvailable());
  }
}