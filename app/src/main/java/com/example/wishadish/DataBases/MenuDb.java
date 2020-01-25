package com.example.wishadish.DataBases;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CompleteMenuTable.class}, version = 1, exportSchema = false)

public abstract class MenuDb extends RoomDatabase {

    private static final String DB_NAME = "MenuDb.db";
    private static MenuDb instance;

    public static synchronized MenuDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MenuDb.class,
                    DB_NAME).build();
        }
        return instance;
    }

    public abstract CompleteMenuTableDao completeMenuTableDao();

//    public static MenuDb getInstance(Context context) {
//        if (instance == null) {
//            synchronized (MenuDb.class) {
//                if (instance == null) {
//                    instance = Room.databaseBuilder(context.getApplicationContext(),
//                            MenuDb.class, DB_NAME)
//                            .build();
//                }
//            }
//        }
//        return instance;
//    }

}