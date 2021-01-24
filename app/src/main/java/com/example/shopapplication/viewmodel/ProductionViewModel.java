package com.example.shopapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.customer.Billing;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.example.shopapplication.retrofit.orders.LineItemsItem;

import java.util.List;

public class ProductionViewModel extends AndroidViewModel {

    private final ProductionRepository mRepository;
    private final LiveData<List<ProductsItem>> mHighestRankedItemsLiveData;
    private final LiveData<List<ProductsItem>> mNewestItemsLiveData;
    private final LiveData<ProductsItem> mItemLiveData;
    private final LiveData<List<CategoryResponse>> mCategoryItemsLiveData;
    private final LiveData<List<ProductsItem>> mItemsLiveData;
    private final LiveData<List<ProductsItem>> mSearchItemsLiveData;
    private final LiveData<List<CustomerResponse>> mCustomersLiveData;
    private LiveData<List<ProductionModel>> getAllProducts;
    private LiveData<List<CustomerModel>> getAllCustomers;

    //Constructor
    public ProductionViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductionRepository.getInstance(application);
        mHighestRankedItemsLiveData = mRepository.getHighestRankedItemsLiveData();
        mNewestItemsLiveData = mRepository.getNewestItemsLiveData();
        mItemLiveData = mRepository.getItemLiveData();
        mCategoryItemsLiveData = mRepository.getCategoryItemsLiveData();
        mItemsLiveData = mRepository.getItemsLiveData();
        mSearchItemsLiveData = mRepository.getSearchItemsLiveData();
        mCustomersLiveData = mRepository.getCustomersLiveData();
        getAllProducts = mRepository.returnAllProductionOrders();
        getAllCustomers = mRepository.returnAllCustomers();
    }

    //Getter
    public LiveData<List<ProductsItem>> getHighestRankedItemsLiveData() {
        return mHighestRankedItemsLiveData;
    }

    public LiveData<List<ProductsItem>> getNewestItemsLiveData() {
        return mNewestItemsLiveData;
    }

    public LiveData<ProductsItem> getItemLiveData() {
        return mItemLiveData;
    }

    public LiveData<List<ProductsItem>> getItemsLiveData() {
        return mItemsLiveData;
    }

    public LiveData<List<CategoryResponse>> getCategoryItemsLiveData() {
        return mCategoryItemsLiveData;
    }

    public LiveData<List<ProductsItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public LiveData<List<CustomerResponse>> getCustomersLiveData(){
        return mCustomersLiveData;
    }

    //Methods
    public void fetchHighestRankedAndNewestItemsAsync(){
        mRepository.fetchItemsAsyncHighestRateAndNewest();
    }

    public void fetchItem(int id){
        mRepository.fetchItem(id);
    }

    public void fetchCategoriesAsync(){
        mRepository.fetchCategoriesAsync();
    }

    public void fetchItems(){
        mRepository.fetchProductionItemsAsync();
    }

    public void fetchSearchItems(String query){
        mRepository.fetchSearchItemsAsync(query);
    }

    public void fetchCustomers(){
        mRepository.fetchCustomers();
    }

    public void postCustomer(String firstName, String lastName, String mail){
        mRepository.postCustomer(firstName,lastName,mail);
    }

    //Customer
    public LiveData<List<CustomerModel>> returnAllCustomers(){
       return getAllCustomers;
    }

    public void insertCustomer(CustomerModel customer){
        mRepository.insertCustomer(customer);
    }

    public CustomerModel getCustomer(String mail){
        return mRepository.returnCustomer(mail);
    }

    //Order
    public void postOrder(String total, int customerId, String discountTotal){
        mRepository.postOrder(total, customerId, discountTotal);
    }

    //Production Orders
    public LiveData<List<ProductionModel>> returnAllProductionModels(){
        return getAllProducts;
    }

    public ProductionModel returnProductionModel(int id){
        return mRepository.returnProductionOrder(id);
    }

    public void insertProductionOrder(ProductionModel productionModel){
        mRepository.insertProductionOrder(productionModel);
    }

    public void deleteAllProductionOrder(){
        mRepository.deleteAllProcusts();
    }
}
