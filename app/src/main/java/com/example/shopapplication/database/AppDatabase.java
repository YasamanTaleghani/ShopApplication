package com.example.shopapplication.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {CustomerModel.class, ProductionModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDAO getCustomerDAO();
    public abstract ProductionDAO getProductionOrderDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private ProductionDAO mProductionDAO;
        private CustomerDAO mCustomerDAO;

        public populateDbAsyncTask(AppDatabase db) {
            mProductionDAO = db.getProductionOrderDAO();
            mCustomerDAO = db.getCustomerDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*mProductionDAO.insertProductionOrder(new ProductionModel(1, 1,
                    "production1", "1000", null));
            mProductionDAO.insertProductionOrder(new ProductionModel(1, 2,
                    "production2", "1000" , null ));
            mCustomerDAO.insertCustomer(new CustomerModel(1, "saghi",
                    "saadat", "1234", "s@gmail.com","tehran",
                    "tehran", "iran", "vanak", null));
            mCustomerDAO.insertCustomer(new CustomerModel(2, "ali",
                    "saadat", "12345", "a@gmail.com","tehran",
                    "tehran", "iran", "vanak", null));*/
            return null;
        }
    }
}
