package com.example.shopapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductionDAO {

    @Query("SELECT * FROM products")
    List<ProductionModel> returnAllProductionOrders();

    @Query("SELECT * FROM products WHERE productionId IN (:productionOrderId)")
    ProductionModel returnProductionOrder (int productionOrderId);

    @Insert
    void insertProductionOrder(ProductionModel productionModel);

    @Delete
    void deleteProductionOrder(ProductionModel productionModel);
}
