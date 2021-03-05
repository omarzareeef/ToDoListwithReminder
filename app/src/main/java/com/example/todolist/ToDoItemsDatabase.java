package com.example.todolist;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ToDoItem.class}, version = 2, exportSchema = false)
public abstract class ToDoItemsDatabase extends RoomDatabase {
    private static ToDoItemsDatabase instance;
    public abstract ToDoItemsDao toDoItemsDao();

    public static synchronized ToDoItemsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ToDoItemsDatabase.class, "todo_items_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
