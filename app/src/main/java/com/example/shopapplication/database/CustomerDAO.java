package com.example.shopapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM customer WHERE id IN (:customerId)")
    CustomerModel returnCustomer(int customerId);

    @Insert
    void insertCustomer(CustomerModel customer);

    @Update
    void updateCustomer(CustomerModel customer);

    @Delete
    void deleteCustomer(CustomerModel customer);

}
