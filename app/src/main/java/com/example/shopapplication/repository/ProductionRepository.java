package com.example.shopapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shopapplication.database.AppDatabase;
import com.example.shopapplication.database.CustomerDAO;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionDAO;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.RetrofitInstance;
import com.example.shopapplication.retrofit.ShopService;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.retrofit.orders.OrdersResponse;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductionRepository implements CustomerDAO, ProductionDAO {

    private CustomerDAO mCustomerDAO;
    private ProductionDAO mProductionDAO;

    public static final String TAG = "ProductionRepository";
    private ShopService mShopService;
    private static ProductionRepository mRepository = null;
    private int pageHighestRankedOrder = 1;
    private int pageCustomers = 1;
    private int pageReviews = 1;

    private List<ProductsItem> mItemsRanked = new ArrayList<>();
    private List<ProductsItem> mItemsNewest = new ArrayList<>();
    private List<CustomerResponse> mCustomers = new ArrayList<>();
    private List<ReviewsResponse> mReviews = new ArrayList<>();
    private boolean request = true;

    private MutableLiveData<List<ProductsItem>> mHighestRankedItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mNewestItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductsItem> mItemLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CategoryResponse>> mCategoryItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductsItem>> mSearchItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CustomerResponse>> mCustomersLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ReviewsResponse>> mReviewsLiveData = new MutableLiveData<>();
    private LiveData<List<ProductionModel>> allProducts;
    private LiveData<List<CustomerModel>> allCustomers;

    //Constructor
    private ProductionRepository(Application application) {
        mShopService = RetrofitInstance.getInstance().create(ShopService.class);
        AppDatabase db = AppDatabase.getDataBase(application);
        mCustomerDAO = db.getCustomerDAO();
        mProductionDAO = db.getProductionOrderDAO();
        allProducts = mProductionDAO.returnAllProductionOrders();
        allCustomers = mCustomerDAO.returnAllCustomers();
    }

    //Getter
    public MutableLiveData<List<ProductsItem>> getHighestRankedItemsLiveData() {
        return mHighestRankedItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getNewestItemsLiveData() {
        return mNewestItemsLiveData;
    }

    public MutableLiveData<ProductsItem> getItemLiveData() {
        return mItemLiveData;
    }

    public MutableLiveData<List<CategoryResponse>> getCategoryItemsLiveData() {
        return mCategoryItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getItemsLiveData() {
        return mItemsLiveData;
    }

    public MutableLiveData<List<ProductsItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public MutableLiveData<List<CustomerResponse>> getCustomersLiveData() {
        return mCustomersLiveData;
    }

    public LiveData<List<CustomerModel>> getAllCustomers() {
        return allCustomers;
    }

    public MutableLiveData<List<ReviewsResponse>> getReviewsLiveData() {
        return mReviewsLiveData;
    }

    //Methods
    public static ProductionRepository getInstance(Application application) {
        if (mRepository == null) {
            mRepository = new ProductionRepository(application);
            return mRepository;
        } else {
            return mRepository;
        }
    }

    public void fetchItemsAsyncHighestRateAndNewest() {


            Call<List<ProductsItem>> call =
                    mShopService.listItems(
                            NetworkParams.getHighestRateOptions(), pageHighestRankedOrder);

            call.enqueue(new Callback<List<ProductsItem>>() {

                @Override
                public void onResponse
                        (Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {

                    if (response.isSuccessful()){
                        List<ProductsItem> productsItems = response.body();

                        for (int i = 0; i <productsItems.size() ; i++) {
                            //Log.d(TAG, "product:" + (i+1) + productsItems.get(i).getName());
                            mItemsRanked.add(productsItems.get(i));
                            mItemsNewest.add(productsItems.get(i));
                        }
                        if (productsItems.size()==10){
                            pageHighestRankedOrder++;
                            fetchItemsAsyncHighestRateAndNewest();
                        } else {
                            Collections.sort(mItemsRanked, new Comparator<ProductsItem>() {
                                @Override
                                public int compare(ProductsItem p1, ProductsItem p2) {
                                    return p1.getAverageRating().compareTo(p2.getAverageRating());
                                }
                            });
                            Collections.reverse(mItemsRanked);
                            mHighestRankedItemsLiveData.setValue(mItemsRanked);

                            Collections.sort(mItemsNewest, new Comparator<ProductsItem>() {
                                @Override
                                public int compare(ProductsItem p1, ProductsItem p2) {
                                    return p1.getDateCreated().compareTo(p2.getDateCreated());
                                }
                            });
                            mNewestItemsLiveData.setValue(mItemsNewest);
                            pageHighestRankedOrder =1;
                        }

                    }

                }

                @Override
                public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                }

            });

    }


    public void fetchItem(int id) {
        Call<ProductsItem> call = mShopService.item(id, NetworkParams.getBaseOptions());

        call.enqueue(new Callback<ProductsItem>() {
            @Override
            public void onResponse(Call<ProductsItem> call, Response<ProductsItem> response) {
                ProductsItem item = response.body();

                //Log.d(TAG, "Get item: " + response.body().getName());

                mItemLiveData.setValue(item);
            }

            @Override
            public void onFailure(Call<ProductsItem> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoriesAsync() {
        Call<List<CategoryResponse>> call =
                mShopService.listCategories(NetworkParams.getBaseOptions());

        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call,
                                   Response<List<CategoryResponse>> response) {
                List<CategoryResponse> categoryResponses = response.body();

                /*for (int i = 0; i < categoryResponses.size() ; i++) {
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                }*/

                mCategoryItemsLiveData.setValue(categoryResponses);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductionItemsAsync() {
        Call<List<ProductsItem>> call =
                mShopService.listItems(NetworkParams.getBaseOptions(),1);

        call.enqueue(new Callback<List<ProductsItem>>() {
            @Override
            public void onResponse(Call<List<ProductsItem>> call,
                                   Response<List<ProductsItem>> response) {
                List<ProductsItem> items = response.body();

                /*for (int i = 0; i < items.size() ; i++) {
                    List<CategoriesItem> categories = response.body().get(i).getCategories();
                    Log.d(TAG, "Get Response: " + response.body().get(i).getName());
                    if(categories.size()>0){
                        for (int j = 0; j < categories.size() ; j++) {
                            Log.d(TAG, "Get Response: " + categories.get(j).getName());
                        }
                    }
                }*/

                mItemsLiveData.setValue(items);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSearchItemsAsync(String query) {
        Call<List<ProductsItem>> call =
                mShopService.listSearchItems(NetworkParams.getSearchOptions(query), 1);

        call.enqueue(new Callback<List<ProductsItem>>() {
            @Override
            public void onResponse(Call<List<ProductsItem>> call, Response<List<ProductsItem>> response) {
                List<ProductsItem> searchItems = response.body();

                mSearchItemsLiveData.setValue(searchItems);
            }

            @Override
            public void onFailure(Call<List<ProductsItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void postCustomer(String firstName, String lastName, String mail){

        Call<CustomerResponse> call = mShopService.customerResponse(
                NetworkParams.getBaseOptions(),
                firstName,
                lastName,
                mail);

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                CustomerResponse customer = response.body();
                if (response.isSuccessful()){
                    Log.d("postCustomer", "customer id: " + customer.getId());
                    CustomerModel customerModel = new CustomerModel(
                            customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail());

                    insertCustomer(customerModel);
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCustomers(){
        Call<List<CustomerResponse>> call =
                mShopService.listCustomers(NetworkParams.getBaseOptions(), pageCustomers);

        //Log.d("customer", "page is: " + pageCustomers);

        call.enqueue(new Callback<List<CustomerResponse>>() {
            @Override
            public void onResponse(Call<List<CustomerResponse>> call, Response<List<CustomerResponse>> response) {
                List<CustomerResponse> customers = response.body();
                if (response.isSuccessful()){
                    if (customers.size()==10){
                        for (int i = 0; i < customers.size(); i++) {
                            mCustomers.add(customers.get(i));
                        }
                        pageCustomers++;
                        fetchCustomers();
                    } else{
                        mCustomersLiveData.setValue(mCustomers);
                        pageCustomers = 1;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CustomerResponse>> call, Throwable t) {

            }
        });
    }

    public void postOrder(String total, int customerId, String discountTotal){
        Call<OrdersResponse> call = mShopService.orderResponse(
                NetworkParams.getBaseOptions(),
                total,
                customerId,
                discountTotal);

        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "Order: " + response.body().getId() + "is successful");
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {

            }
        });
    }

    public void fetchReviews(int productId){
        Call<List<ReviewsResponse>> call =
                mShopService.listReviews(NetworkParams.getBaseOptions(), pageReviews, productId);

        call.enqueue(new Callback<List<ReviewsResponse>>() {
            @Override
            public void onResponse(Call<List<ReviewsResponse>> call, Response<List<ReviewsResponse>> response) {
                if (response.isSuccessful()){
                    List<ReviewsResponse> reviews = response.body();
                    if (reviews.size()>0){
                        for (int i = 0; i < reviews.size() ; i++) {
                            mReviews.add(reviews.get(i));
                        }
                    }
                    if (reviews.size()==10){
                        pageReviews++;
                        fetchReviews(productId);
                    } else {
                        mReviewsLiveData.setValue(mReviews);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReviewsResponse>> call, Throwable t) {

            }
        });
    }

    //Data base
    @Override
    public LiveData<List<CustomerModel>> returnAllCustomers() {
        return allCustomers;
    }

    @Override
    public CustomerModel returnCustomer(String customerEmail) {
        return mCustomerDAO.returnCustomer(customerEmail);
    }

    @Override
    public void insertCustomer(CustomerModel customer) {
        new InsertCustomerAsyncTask(mCustomerDAO).execute(customer);
    }

    @Override
    public void updateCustomer(CustomerModel customer) {
        mCustomerDAO.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(CustomerModel customer) {
        mCustomerDAO.deleteCustomer(customer);
    }

    //
    @Override
    public LiveData<List<ProductionModel>> returnAllProductionOrders() {
        return allProducts;
    }

    @Override
    public ProductionModel returnProductionOrder(int productionOrderId) {
        return mProductionDAO.returnProductionOrder(productionOrderId);
    }

    @Override
    public void insertProductionOrder(ProductionModel productionModel) {
        new InsertProductionToShopListAsyncTask(mProductionDAO).execute(productionModel);
    }

    @Override
    public void deleteAllProcusts() {
        new DeleteAllProductsAsyncTask(mProductionDAO).execute();
    }

    public static class InsertProductionToShopListAsyncTask extends AsyncTask<ProductionModel, Void, Void>{

        private ProductionDAO mProductionDAO;

        public InsertProductionToShopListAsyncTask(ProductionDAO productionDAO) {
            mProductionDAO = productionDAO;
        }
        @Override
        protected Void doInBackground(ProductionModel... productionModels) {
            mProductionDAO.insertProductionOrder(productionModels[0]);
            return null;
        }
    }

    public static class InsertCustomerAsyncTask extends AsyncTask<CustomerModel, Void, Void>{

        private CustomerDAO mCustomerDAO;

        public InsertCustomerAsyncTask(CustomerDAO customerDAO) {
            mCustomerDAO = customerDAO;
        }

        @Override
        protected Void doInBackground(CustomerModel... customerModels) {
            mCustomerDAO.insertCustomer(customerModels[0]);
            return null;
        }
    }

    public static class DeleteAllProductsAsyncTask extends AsyncTask<ProductionModel, Void, Void>{

        private ProductionDAO mProductionDAO;
        public DeleteAllProductsAsyncTask(ProductionDAO productionDAO) {
            mProductionDAO = productionDAO;
        }

        @Override
        protected Void doInBackground(ProductionModel... productionModels) {
            mProductionDAO.deleteAllProcusts();
            return null;
        }
    }
}
