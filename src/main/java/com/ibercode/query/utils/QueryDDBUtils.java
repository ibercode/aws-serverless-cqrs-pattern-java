package com.ibercode.query.utils;

import com.ibercode.query.model.QueryCustomer;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

public class QueryDDBUtils {

    private final DynamoDbEnhancedClient enhancedClient;

    public QueryDDBUtils(String region) {

        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.of(region))
                .build();
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }

    public List<QueryCustomer> getAllCustomers(String tableName){

        List<QueryCustomer> queryCustomers;

        DynamoDbTable<QueryCustomer> queryCustomerTable = enhancedClient.table(tableName, TableSchema.fromBean(QueryCustomer.class));
        queryCustomers = queryCustomerTable.scan().items().stream().collect(Collectors.toList());

        return queryCustomers;
    }
}
