package com.example.wishadish.DataBases;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DbRepository {

    private CompleteMenuTableDao MenuItemsDao;
    private static List<CompleteMenuTable> allMenuItemsList;
    private static List<CompleteMenuTable> fewMenuItemsList;

    public DbRepository(Application application) {
        MenuDb database = MenuDb.getInstance(application);
        MenuItemsDao = database.completeMenuTableDao();
        // allTrips = TripDao.getAllTrips();
    }

    public void insert(CompleteMenuTable menuItem) {
        new InsertMenuItemAsyncTask(MenuItemsDao).execute(menuItem);
    }

    public void deleteByItemname(String itemName) {
        new DeleteMenuItemByNameAsyncTask(MenuItemsDao).execute(itemName);
    }

    public void deleteAllMenuItems() {
        new DeleteAllMenuItemsAsyncTask(MenuItemsDao).execute();
    }

    public List<CompleteMenuTable> getAllItems() {
        new SelectAllMenuItemsAsyncTask(MenuItemsDao).execute();
        return allMenuItemsList;
    }

    public List<CompleteMenuTable> getMenuItemByName(String itemname) {
        new SelectMenuItemsByNameAsyncTask(MenuItemsDao).execute(itemname);
        return fewMenuItemsList;
    }



    private static class InsertMenuItemAsyncTask extends AsyncTask<CompleteMenuTable, Void, Void> {
        private CompleteMenuTableDao itemsDao;

        private InsertMenuItemAsyncTask(CompleteMenuTableDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(CompleteMenuTable... items) {
            itemsDao.insert(items[0]);
            return null;
        }
    }

    private static class DeleteMenuItemByNameAsyncTask extends AsyncTask<String, Void, Void> {
        private CompleteMenuTableDao itemsDao;

        private DeleteMenuItemByNameAsyncTask(CompleteMenuTableDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(String... items) {
            itemsDao.deleteByItemname(items[0]);
            return null;
        }
    }

    private static class DeleteAllMenuItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CompleteMenuTableDao itemsDao;

        private DeleteAllMenuItemsAsyncTask(CompleteMenuTableDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemsDao.deleteAllMenuItems();
            return null;
        }
    }

    private static class SelectAllMenuItemsAsyncTask extends AsyncTask<Void, Void, Void> {

        private CompleteMenuTableDao itemsDao;

        private SelectAllMenuItemsAsyncTask(CompleteMenuTableDao itemsDao) {
            this.itemsDao = itemsDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            allMenuItemsList = new ArrayList<>();
            allMenuItemsList = itemsDao.getAllItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private static class SelectMenuItemsByNameAsyncTask extends AsyncTask<String, Void, Void> {

        private CompleteMenuTableDao itemsDao;

        private SelectMenuItemsByNameAsyncTask(CompleteMenuTableDao itemsDao) {
            this.itemsDao = itemsDao;

        }

        @Override
        protected Void doInBackground(String... items) {
            fewMenuItemsList = new ArrayList<>();
            fewMenuItemsList = itemsDao.getMenuItemByName(items[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
