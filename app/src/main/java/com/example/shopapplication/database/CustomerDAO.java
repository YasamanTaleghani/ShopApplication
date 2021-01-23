package com.example.shopapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM customer")
    List<CustomerModel> returnAllCustomers();

    @Query("SELECT * FROM customer WHERE customerId IN (:customerId)")
    CustomerModel returnCustomer(int customerId);

    @Insert
    void insertCustomer(CustomerModel customer);

    @Update
    void updateCustomer(CustomerModel customer);

    @Delete
    void deleteCustomer(CustomerModel customer);

}
