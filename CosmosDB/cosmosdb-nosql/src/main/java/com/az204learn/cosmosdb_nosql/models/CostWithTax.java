package com.az204learn.cosmosdb_nosql.models;

import lombok.Data;

@Data
public class CostWithTax {

    private double cost;
    private double origionalCost;
    private double costWithTax;
}
