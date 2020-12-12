package com.example.shopapplication.repository;

import com.example.shopapplication.model.ProductionItem;
import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;
import com.example.shopapplication.retrofitModel.ProductsItem;
import com.example.shopapplication.retrofitModel.ProductsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductionRepository {

    private List<ProductionItem> mItems= new ArrayList<>();
    private ShopService mShopService;

    //Getter & Setter
    public List<ProductionItem> getItems() {
        return mItems;
    }
    public void setItems(List<ProductionItem> items) {
        mItems = items;
    }

    //Constructor
    public ProductionRepository() {
        mShopService = RetrofitInstance.getInstance().create(ShopService.class);
    }

    //Methods
    public List<ProductionItem> fetchItems(){

        Call<ProductsResponse> call =
                mShopService.listItems(NetworkParams.PRODUCTS_LIST);
        try {
            Response<ProductsResponse> response = call.execute();
            ProductsResponse productsResponse = response.body();
            List<ProductionItem> productionItems = new ArrayList<>();
            for (ProductsItem item :productsResponse.getProducts()) {
                ProductionItem productionItem =
                        new ProductionItem(item.getId(), item.getTitle(), item.getPermalink());
                productionItems.add(productionItem);
            }
            return productionItems;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
