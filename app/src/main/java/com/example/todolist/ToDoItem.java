package com.example.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_items_table")
public class ToDoItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int checked;
    private String body;

    public ToDoItem(int checked, String body) {
        this.checked = checked;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
    }

    public String getBody() {
        return body;
    }
}
