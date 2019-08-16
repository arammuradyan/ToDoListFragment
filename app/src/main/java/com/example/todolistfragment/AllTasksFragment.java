package com.example.todolistfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class AllTasksFragment extends Fragment {
   private RecyclerView recyclerView;
   TaskAdapter taskAdapter;


    public AllTasksFragment() {
        super();
    }

    public static AllTasksFragment newInstance() {
        Bundle args = new Bundle();
        AllTasksFragment fragment = new AllTasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_all_tasks,container,false);
       recyclerView=view.findViewById(R.id.recycler_view_all);
       view.setTag("Recycler");
       taskAdapter=new TaskAdapter(new TaskAdapter.Listener() {
           @Override
           public void onTaskItemClick(int i, TaskAdapter.TaskItemHolder taskItemHolder) {
PagerActivity pa=(PagerActivity) getActivity();
pa.taskEdit(taskAdapter.getTaskList().get(i),i,taskItemHolder);
           }
       });
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(llm);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
              int position =viewHolder.getAdapterPosition();
              taskAdapter.delete(position);
            }
        }).attachToRecyclerView(recyclerView);


        return view;
    }

}
