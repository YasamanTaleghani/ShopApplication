package com.example.shopapplication.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.shopapplication.R;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.repository.ProductionRepository;
import com.example.shopapplication.retrofit.categories.CategoryResponse;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.retrofit.Products.ProductsItem;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;
import com.example.shopapplication.utilities.SettingPreferenses;

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
    private final LiveData<List<ReviewsResponse>> getProductionReveiws;

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
        getProductionReveiws = mRepository.getReviewsLiveData();
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

    public LiveData<List<ReviewsResponse>> getReviewsLiveData(){
        return getProductionReveiws;
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

    public void fetchSpecificCustomer(int customerId,Activity activity){
        mRepository.fetchSpecificCustomer(customerId, activity);
    }

    public void fetchReveiws(int productionId){
        mRepository.fetchReviews(productionId);
    }

    public void postReview(int productId, String review, String reviewer, String reviewerEmail, int rating){
        mRepository.postReview(productId, review, reviewer, reviewerEmail, rating);
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


    public class MyWorker extends Worker{

        public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @SuppressLint("WrongThread")
        @NonNull
        @Override
        public Result doWork() {

            int notificationOnOff =
                    SettingPreferenses.getSettingOnOffPreferences
                            ((Activity) getApplicationContext(), SettingPreferenses.SETTING_ON_OFF);

            if (notificationOnOff ==1){
                fetchHighestRankedAndNewestItemsAsync();
                getAllProducts.observe(getApplication(), new Observer<List<ProductionModel>>() {
                    @SuppressLint("WrongThread")
                    @Override
                    public void onChanged(List<ProductionModel> productionModels) {
                        int newestServerItem = productionModels.get(0).getId();
                        int newestAppItem = SettingPreferenses.getNewestItemPreferences
                                ((Activity) getApplicationContext(), SettingPreferenses.NEWEST_APP_ITEM);
                        if (newestAppItem==newestServerItem){
                            displayNotification
                                    ("اعلان",
                                            "یک محصول جدید به فروشگاه اضافه شده است");
                        }
                    }
                });
            } else
                return Result.retry();

            return Result.success();
        }

        public void displayNotification(String title, String description){
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService
                            (Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("simplifiedcoding",
                        "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder notification = new NotificationCompat.Builder
                    (getApplicationContext(), "simplifiedcoding")
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.mipmap.ic_launcher);

            notificationManager.notify(1, notification.build());
        }
    }
}
