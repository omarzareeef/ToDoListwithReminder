package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TO_DO_REQUEST = 1;
    public static final int EDIT_TO_DO_REQUEST = 2;

    private RecyclerView toDoItemsRecyclerView;
    private ToDoItemsAdapter toDoItemsAdapter;

    ToDoItemsDatabase toDoItemsDatabase;

    FloatingActionButton fabToDo, fabReminder;
    FloatingActionMenu fabMenu;

    public Button deleteAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        showDate();

        toDoItemsRecyclerView = findViewById(R.id.todo_recyclerview);
        toDoItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoItemsRecyclerView.setHasFixedSize(true);
        toDoItemsAdapter = new ToDoItemsAdapter();
        toDoItemsRecyclerView.setAdapter(toDoItemsAdapter);
        toDoItemsAdapter.setOnItemClickListener(new ToDoItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ToDoItem item) {
                Intent updateItemIntent = new Intent(MainActivity.this, AddEditToDoItem.class);
                updateItemIntent.putExtra(AddEditToDoItem.EXTRA_TO_DO_ID,item.getId() );
                updateItemIntent.putExtra(AddEditToDoItem.EXTRA_TO_DO_ITEM, item.getBody());
                startActivityForResult(updateItemIntent, EDIT_TO_DO_REQUEST);
            }
        });


        toDoItemsDatabase = ToDoItemsDatabase.getInstance(this);
        getToDoItem();

        fabMenu = findViewById(R.id.fab_menu);

        fabToDo = findViewById(R.id.menu_todo);
        fabToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewItem = new Intent(MainActivity.this, AddEditToDoItem.class);
                startActivityForResult(addNewItem, ADD_TO_DO_REQUEST);
            }
        });

        fabReminder = findViewById(R.id.menu_reminder);
        fabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setNewAlarm = new Intent(MainActivity.this, Reminders.class);
                startActivity(setNewAlarm);
            }
        });

        deleteAllItems = findViewById(R.id.delete_all_items);
        deleteAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllToDoItems();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                delete(toDoItemsAdapter.getToDoItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(toDoItemsRecyclerView);

    }

    public void showDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView date = findViewById(R.id.date);
        date.setText(currentDate);
    }

    public void getToDoItem() {
        toDoItemsDatabase.toDoItemsDao().getToDoItems()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ToDoItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ToDoItem> toDoItems) {
                        toDoItemsAdapter.setmToDoItems(toDoItems);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void insertToDoItem(ToDoItem toDo) {
        toDoItemsDatabase.toDoItemsDao().insert(toDo)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getToDoItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateToDoItem(ToDoItem toDoItem) {
        toDoItemsDatabase.toDoItemsDao().update(toDoItem)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getToDoItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void delete(ToDoItem toDoItem) {
        toDoItemsDatabase.toDoItemsDao().delete(toDoItem)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getToDoItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void deleteAllToDoItems() {
        toDoItemsDatabase.toDoItemsDao().deleteAllToDoItems()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onComplete() {
                        getToDoItem();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToDoItem item;

        if (requestCode == ADD_TO_DO_REQUEST && resultCode == RESULT_OK) {
            String toDoItem = data.getStringExtra(AddEditToDoItem.EXTRA_TO_DO_ITEM);

            item = new ToDoItem(0, toDoItem);
            insertToDoItem(item);
            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            fabMenu.close(true);
        } else if (requestCode == EDIT_TO_DO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditToDoItem.EXTRA_TO_DO_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Can not be Updated...", Toast.LENGTH_SHORT).show();
            }

            String toDoItem = data.getStringExtra(AddEditToDoItem.EXTRA_TO_DO_ITEM);

            item = new ToDoItem(0, toDoItem);
            item.setId(id);
            updateToDoItem(item);
            Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            fabMenu.close(true);
        } else {
            fabMenu.close(true);
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}