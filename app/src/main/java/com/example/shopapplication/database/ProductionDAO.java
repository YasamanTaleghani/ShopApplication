package com.example.shopapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductionDAO {

    @Query("SELECT * FROM products")
    LiveData<List<ProductionModel>> returnAllProductionOrders();

    @Query("SELECT * FROM products WHERE productionId IN (:productionOrderId)")
    ProductionModel returnProductionOrder (int productionOrderId);

    @Insert
    void insertProductionOrder(ProductionModel productionModel);

    @Query("DELETE FROM products")
    void deleteAllProcusts();
}
