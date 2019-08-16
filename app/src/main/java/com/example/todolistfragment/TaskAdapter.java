package com.example.todolistfragment;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter <TaskAdapter.TaskItemHolder> {
    private List<Task> taskList=new ArrayList<>();
    private Listener onTaskClickListener;
    private final String Tag="tag";
    private static int height;
    private static int width;
   // private ImageView img;

    public TaskAdapter(Listener onTaskClickListener) {
        this.onTaskClickListener = onTaskClickListener;
    }

    public  List<Task> getTaskList() {
        return taskList;
    }

    public void setList(List<Task> list){
        taskList.addAll(list);
    }

    @NonNull
    @Override
    public TaskAdapter.TaskItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
       final TaskItemHolder taskItemHolder=new TaskItemHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button= view.findViewById(R.id.delet_btn);
                ImageView img=view.findViewById(R.id.imgView_rv);
                onTaskClickListener.onTaskItemClick((int)v.getTag(),taskItemHolder);
            }
        });
        return  taskItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  TaskItemHolder taskItemHolder, final int position) {
        Button delete= taskItemHolder.itemView.findViewById(R.id.delet_btn);
        /*height=taskItemHolder.itemView.findViewById(R.id.imgView_rv).getHeight();
        width=taskItemHolder.itemView.findViewById(R.id.imgView_rv).getWidth();*/
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete(position);
            }
        });
        Task task = taskList.get(position);
        taskItemHolder.bind(task);
        taskItemHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public interface Listener {
        void onTaskItemClick(int i,TaskItemHolder taskItemHolder);
    }

    public  void delete(int index) {
        taskList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index,getItemCount());
    }

    public void add(Task task) {
        taskList.add(task);
        notifyItemInserted(getItemCount() -1);
   }

    public void save(int index, Task task) {
       Task changedTask = taskList.get(index);
       changedTask.setType(task.getType());
       changedTask.setName(task.getName());
       changedTask.setDescription(task.getDescription());
       changedTask.setImgUri(task.getImgUri());
       notifyItemChanged(index);
    }


    static class TaskItemHolder extends RecyclerView.ViewHolder {

        private PriorityView priorityView;
        private TextView itemName;
        private TextView itemDescription;
        private RatingBar itemType;
        private ImageView itemImg;
        private View view_background;
        private Utils utils=new Utils();

        private TaskItemHolder(@NonNull View itemView) {
            super(itemView);
            priorityView=itemView.findViewById(R.id.priority_view_rv);
            view_background = itemView.findViewById(R.id.view_rv);
            itemName = itemView.findViewById(R.id.tv_name_rv);
            itemDescription = itemView.findViewById(R.id.tv_description_rv);
            itemType = itemView.findViewById(R.id.tv_type_rv);
            itemImg = itemView.findViewById(R.id.imgView_rv);

        }

        private void bind(Task task) {
            itemName.setText(task.getName());
            itemDescription.setText(task.getDescription());
            itemType.setRating(task.getType());
            itemType.setEnabled(false);
            Uri uri = Uri.parse(task.getImgUri());
            itemImg.setImageURI(uri);
            utils.setBitmap(itemView.getContext(),itemImg,uri);
            utils.onBackgroundColorSet(task,view_background,priorityView);

        }
    }
}