package com.example.shopapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class ProductionModel {

    @PrimaryKey
    public int customerId;

    public int productionId;

    public String productionName;

    public String ProductionPrice;

    //
    public int getCustomerId() {
        return customerId;
    }

    public int getProductionId() {
        return productionId;
    }

    public String getProductionName() {
        return productionName;
    }

    public String getProductionPrice() {
        return ProductionPrice;
    }

    //Constructor
    public ProductionModel(int customerId, int productionId, String productionName, String productionPrice) {
        this.customerId = customerId;
        this.productionId = productionId;
        this.productionName = productionName;
        ProductionPrice = productionPrice;
    }
}
