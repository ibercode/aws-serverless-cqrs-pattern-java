package com.ibercode.command.utils;

import com.ibercode.command.model.Customer;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.UUID;

public class DDBUtils {

    private final DynamoDbEnhancedClient enhancedClient;

    public DDBUtils(String region) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.of(region))
                .build();
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }


    public String save(Customer customer, String commandTableName) {

        DynamoDbTable<Customer> mappedTable = enhancedClient
                .table(commandTableName, TableSchema.fromBean(Customer.class));

        String customerId = UUID.randomUUID().toString();

        customer.setId(customerId);

        mappedTable.putItem(customer);

        return customerId;
    }

    public void saveIntoQueryDDB(List<Customer> customers, String queryTableName) {

        DynamoDbTable<Customer> mappedTable = enhancedClient
                .table(queryTableName, TableSchema.fromBean(Customer.class));

        customers.forEach(mappedTable::putItem);
    }
}
