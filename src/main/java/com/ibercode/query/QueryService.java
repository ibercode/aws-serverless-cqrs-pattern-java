package com.ibercode.query;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.ibercode.query.model.QueryCustomer;
import com.ibercode.query.utils.QueryDDBUtils;

import java.util.List;

public class QueryService implements RequestHandler {

    @Override
    public List<QueryCustomer> handleRequest(Object o, Context context) {

        String queryTableName = System.getenv("QUERY_TABLE_NAME");
        String region = System.getenv("REGION");

        QueryDDBUtils ddbUtils = new QueryDDBUtils(region);

        List<QueryCustomer> customers = ddbUtils.getAllCustomers(queryTableName);

        customers.forEach(System.out::println);

        return customers;
    }
}