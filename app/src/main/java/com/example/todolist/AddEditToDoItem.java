package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditToDoItem extends AppCompatActivity {

    public static final String EXTRA_TO_DO_ID = "EXTRA_TO_DO_ID";
    public static final String EXTRA_TO_DO_ITEM = "EXTRA_TO_DO_ITEM";

    private EditText editTextToDoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_item);

        editTextToDoItem = findViewById(R.id.todo_edittext);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TO_DO_ID)) {
            setTitle("Edit Something To Do ");
            editTextToDoItem.setText(intent.getStringExtra(EXTRA_TO_DO_ITEM));
        } else {
            setTitle("Add Something To Do ");
        }
    }

    public void saveNote() {
        String toDoItem = editTextToDoItem.getText().toString().trim();

        if (toDoItem.isEmpty()) {
            Toast.makeText(this, "Please Insert Something to do...", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TO_DO_ITEM, toDoItem);

        int id = getIntent().getIntExtra(EXTRA_TO_DO_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_TO_DO_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_to_do_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_todo_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}