package com.example.wishadish.DataBases;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompleteMenuTableDao {

    @Query("SELECT * FROM CompleteMenuTable")
    List<CompleteMenuTable> getAllItems();

    @Query("SELECT * FROM CompleteMenuTable WHERE CompleteMenuTable.name = :name")
    List<CompleteMenuTable> getMenuItemByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CompleteMenuTable menuItem);

    @Query("DELETE FROM CompleteMenuTable WHERE CompleteMenuTable.name = :name")
    void deleteByItemname(String name);

    @Query("DELETE FROM CompleteMenuTable")
    void deleteAllMenuItems();
}
