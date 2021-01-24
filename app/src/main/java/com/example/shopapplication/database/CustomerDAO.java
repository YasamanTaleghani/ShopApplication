package com.example.shopapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM customer")
    LiveData<List<CustomerModel>> returnAllCustomers();

    @Query("SELECT * FROM customer WHERE mail IN (:mail)")
    CustomerModel returnCustomer(String mail);

    @Insert
    void insertCustomer(CustomerModel customer);

    @Update
    void updateCustomer(CustomerModel customer);

    @Delete
    void deleteCustomer(CustomerModel customer);

}
