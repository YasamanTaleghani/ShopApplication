package com.example.shopapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.model.ProductsItem;

import java.util.List;

public class ProductionViewModel extends ViewModel {

    private final ProductionRepository mRepository;
    private final LiveData<List<ProductsItem>> mHighestRankedItemsLiveData;
    private final LiveData<List<ProductsItem>> mNewestItemsLiveData;
    private final LiveData<ProductsItem> mItemLiveData;
    private final LiveData<List<CategoryResponse>> mCategoryItemsLiveData;

    //Constructor
    public ProductionViewModel() {
        mRepository = ProductionRepository.getInstance();
        mHighestRankedItemsLiveData = mRepository.getHighestRankedItemsLiveData();
        mNewestItemsLiveData = mRepository.getNewestItemsLiveData();
        mItemLiveData = mRepository.getItemLiveData();
        mCategoryItemsLiveData = mRepository.getCategoryItemsLiveData();
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

    public LiveData<List<CategoryResponse>> getCategoryItemsLiveData() {
        return mCategoryItemsLiveData;
    }

    //Methods
    public void fetchHighestRankedItemsAsync(){
        mRepository.fetchItemsAsyncHighestRate();
    }

    public void fetchNewestItemsAsync(){
        mRepository.fetchItemsAsyncNewestProducts();
    }

    public void fetchItem(int id){
        mRepository.fetchItem(id);
    }
}
