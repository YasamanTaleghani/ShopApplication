package com.example.shopapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductionModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String customerEmail;

    public int productionId;

    public String productionName;

    public String ProductionPrice;

    public String productionSrc;

    //
    public int getId() {
        return id;
    }

    public String getCustomerId() {
        return customerEmail;
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
    public ProductionModel(int customerId, String customerEmail, String productionName,
                           String productionPrice, String productionSrc) {
        this.customerEmail = customerEmail;
        this.productionId = productionId;
        this.productionName = productionName;
        ProductionPrice = productionPrice;
        this.productionSrc = productionSrc;
    }

    public ProductionModel() {
    }
}
