package com.example.shopapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CustomerModel.class, ProductionModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDAO getCustomerDAO();
    public abstract ProductionDAO getProductionOrderDAO();
}
