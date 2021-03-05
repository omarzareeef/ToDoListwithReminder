package com.example.todolist;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ToDoItemsDao {

    @Insert
    Completable insert(ToDoItem toDoItem);

    @Update
    Completable update(ToDoItem toDoItem);

    @Delete
    Completable delete(ToDoItem toDoItem);

    @Query("DELETE FROM todo_items_table")
    Completable deleteAllToDoItems();

    @Query("SELECT * FROM todo_items_table")
    Single<List<ToDoItem>> getToDoItems();


}
