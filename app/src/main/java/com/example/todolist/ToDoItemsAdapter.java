package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoItemsAdapter extends RecyclerView.Adapter<ToDoItemsAdapter.toDoViewHolder> {

    private List<ToDoItem> mToDoItemsArrayList = new ArrayList<>();

    private OnItemClickListener listener;

    public class toDoViewHolder extends RecyclerView.ViewHolder {

        public CheckBox toDoItem;

        public toDoViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoItem = itemView.findViewById(R.id.todo_checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mToDoItemsArrayList.get(position));
                    }
                }
            });
        }
    }

    public ToDoItem getToDoItemAt(int position) {
        return mToDoItemsArrayList.get(position);
    }

    @NonNull
    @Override
    public toDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        toDoViewHolder jvh = new toDoViewHolder(v);
        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull toDoViewHolder holder, int position) {
        ToDoItem currentItem = mToDoItemsArrayList.get(position);
        if (currentItem.getChecked() == 0) {
            holder.toDoItem.setChecked(false);
        } else {
            holder.toDoItem.setChecked(true);
        }
        holder.toDoItem.setText(currentItem.getBody());
    }

    @Override
    public int getItemCount() {
        return mToDoItemsArrayList.size();
    }

    public void setmToDoItems(List<ToDoItem> toDoItems) {
        this.mToDoItemsArrayList = toDoItems;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ToDoItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
