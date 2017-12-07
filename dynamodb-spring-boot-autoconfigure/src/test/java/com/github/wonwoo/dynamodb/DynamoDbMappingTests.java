package com.github.wonwoo.dynamodb;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.socialsignin.spring.data.dynamodb.mapping.DynamoDBMappingContext;
import org.socialsignin.spring.data.dynamodb.mapping.DynamoDBPersistentEntityImpl;
import org.socialsignin.spring.data.dynamodb.mapping.DynamoDBPersistentProperty;
import org.springframework.data.util.TypeInformation;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DynamoDbMappingTests {

    @Mock
    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMappingContext context;


    private DynamoDbMapping dynamoDbMapping;

    @Before
    public void setup() {
        context = new DynamoDBMappingContext();
        HashSet<Class<?>> initialEntitySet = new HashSet<>();
        initialEntitySet.add(Person.class);
        context.setInitialEntitySet(initialEntitySet);
        context.afterPropertiesSet();
        this.dynamoDbMapping = new DynamoDbMapping(amazonDynamoDB, context);
    }

    @Test
    public void getPersistentEntities() {
        Collection<DynamoDBPersistentEntityImpl<?>> entities = this.dynamoDbMapping.getPersistentEntities();
        assertThat(entities).hasSize(1);
    }
    
    @Test
    public void getPersistentEntity() {
        DynamoDBPersistentEntityImpl<?> entity = this.dynamoDbMapping.getPersistentEntity(Person.class);
        DynamoDBPersistentProperty idProperty = entity.getIdProperty();
        assertThat(idProperty.getActualType()).isEqualTo(String.class);
    }

    @Test
    public void getIdProperty() {
        DynamoDBPersistentProperty idProperty = this.dynamoDbMapping.getIdProperty(Person.class);
        assertThat(idProperty.getActualType()).isEqualTo(String.class);
    }

    @Test
    public void getTypeInformation() {
        TypeInformation<?> typeInformation = this.dynamoDbMapping.getTypeInformation(Person.class);
        assertThat(typeInformation.getType()).isEqualTo(Person.class);
    }

    @Test
    public void createTable() {
        CreateTableResult value = new CreateTableResult();
        TableDescription tableDescription = new TableDescription();
        tableDescription.setItemCount(10L);
        value.setTableDescription(tableDescription);
        given(amazonDynamoDB.createTable(any())).willReturn(value);
        List<CreateTableResult> table = this.dynamoDbMapping.createTable();
        assertThat(table).hasSize(1);
        assertThat(table.iterator().next().getTableDescription()).isEqualTo(tableDescription);
    }

    @DynamoDBTable(tableName = "persons")
    private static class Person {
        @DynamoDBHashKey
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}