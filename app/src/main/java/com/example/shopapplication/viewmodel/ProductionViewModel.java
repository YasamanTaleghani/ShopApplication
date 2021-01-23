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

    //Constructor
    public ProductionViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductionRepository.getInstance(getApplication());
        mHighestRankedItemsLiveData = mRepository.getHighestRankedItemsLiveData();
        mNewestItemsLiveData = mRepository.getNewestItemsLiveData();
        mItemLiveData = mRepository.getItemLiveData();
        mCategoryItemsLiveData = mRepository.getCategoryItemsLiveData();
        mItemsLiveData = mRepository.getItemsLiveData();
        mSearchItemsLiveData = mRepository.getSearchItemsLiveData();
        mCustomersLiveData = mRepository.getCustomersLiveData();
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

    public void postCustomer(String firstName, String lastName, String mail, Billing billing){
        mRepository.postCustomer(firstName,lastName,mail, billing);
    }

    //Customer
    public List<CustomerModel> returnAllCustomers(){
       return mRepository.returnAllCustomers();
    }

    public void insertCustomer(CustomerModel customer){
        mRepository.insertCustomer(customer);
    }

    public CustomerModel getCustomer(int id){
        return mRepository.returnCustomer(id);
    }

    //Order
    public void postOrder(String total, int customerId, String discountTotal, Billing billing, List<LineItemsItem> items){
        mRepository.postOrder(total, customerId, discountTotal, billing, items);
    }

    //Production Orders
    public List<ProductionModel> returnAllProductionModels(){
        return mRepository.returnAllProductionOrders();
    }

    public ProductionModel returnProductionModel(int id){
        return mRepository.returnProductionOrder(id);
    }

    public void insertProductionOrder(ProductionModel productionModel){
        mRepository.insertProductionOrder(productionModel);
    }

    public void deleteProductionOrder(ProductionModel productionModel){
        mRepository.deleteProductionOrder(productionModel);
    }
}
