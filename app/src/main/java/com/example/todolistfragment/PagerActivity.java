package com.example.todolistfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class PagerActivity extends AppCompatActivity {

    private final int ADD_REQUEST_CODE = 12;
    private final int DELETE_SAVE_REQUEST_CODE = 23;
    ViewPager viewPager;
    TaskStatePagerAdapter taskStatePagerAdapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.view_pager);
        fragmentManager=getSupportFragmentManager();
        taskStatePagerAdapter=new TaskStatePagerAdapter(fragmentManager);
        viewPager.setAdapter(taskStatePagerAdapter);
        viewPager.setOffscreenPageLimit(2);


      //  ToDoTasksFragment previewFragment=(ToDoTasksFragment) fragmentManager.findFragmentByTag("Preview");
        //previewFragment.getView().findViewById(R.id.et_description).setBackgroundResource(R.drawable.view_3_background);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                Intent addIntent = new Intent(PagerActivity.this, PreviewActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST_CODE);
                CustomIntent.customType(PagerActivity.this,"fadein-to-fadeout");

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE && data != null) {
            Bundle addBundle = data.getBundleExtra("addTask");
            Task addTask = (Task) addBundle.get("addTask");
        newTaskAdding(addTask);
        }

        if (requestCode == DELETE_SAVE_REQUEST_CODE && data != null) {
            int index = data.getIntExtra("index", -10);
            if (data.getBundleExtra("saveTask")!=null) {
                Bundle saveBundle = data.getBundleExtra("saveTask");
                Task saveTask = (Task) saveBundle.get("saveTask");
                AllTasksFragment fragment = (AllTasksFragment) taskStatePagerAdapter.instantiateItem(viewPager, 0);
                fragment.taskAdapter.save(index,saveTask);
                viewPager.setCurrentItem(0);
            } else {

                AllTasksFragment fragment = (AllTasksFragment) taskStatePagerAdapter.instantiateItem(viewPager, 0);
                fragment.taskAdapter.delete(index);
                viewPager.setCurrentItem(0);            }
        }
    }
    public void newTaskAdding(Task task) {
        AllTasksFragment fragment = (AllTasksFragment) taskStatePagerAdapter.instantiateItem(viewPager, 0);
        fragment.taskAdapter.add(task);
        viewPager.setCurrentItem(0);
    }

    public void taskEdit(Task task, int index, TaskAdapter.TaskItemHolder taskItemHolder) {
        Intent intent = new Intent(PagerActivity.this, PreviewActivity.class);

        RatingBar rb=taskItemHolder.itemView.findViewById(R.id.tv_type_rv);
        PriorityView priorityView=taskItemHolder.itemView.findViewById(R.id.priority_view_rv);
        TextView itemName=taskItemHolder.itemView.findViewById(R.id.tv_name_rv);
        TextView itemDescription=taskItemHolder.itemView.findViewById(R.id.tv_description_rv);
        ImageView itemImg =taskItemHolder. itemView.findViewById(R.id.imgView_rv);
        Button deletBtn=taskItemHolder. itemView.findViewById(R.id.delet_btn);

        Pair<View,String> p1=Pair.create((View)itemImg,"img_trans");
        Pair<View,String> p2=Pair.create((View)deletBtn,"button_trans");
        Pair<View,String> p3=Pair.create((View)rb,"rb_trans");
        Pair<View,String> p4=Pair.create((View)priorityView,"pr_trans");
        Pair<View,String> p5=Pair.create((View)itemName,"title_trans");
        Pair<View,String> p6=Pair.create((View)itemDescription,"desc_trans");

        @SuppressWarnings("unchecked")
        ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation(this,p1,p2,p3,p4,p5,p6);

        Bundle taskBundle = new Bundle();
        taskBundle.putParcelable("TaskToPreview", task);
        intent.putExtra("TaskToPreview", taskBundle);
        intent.putExtra("index", index);
        startActivityForResult(intent, DELETE_SAVE_REQUEST_CODE,options.toBundle());
        CustomIntent.customType(this,"fadein-to-fadeout");

    }


    /*public void deleteTask(int index){
        taskStatePagerAdapter.deleteTask(index);
        viewPager.setAdapter(viewPager.getAdapter());
        viewPager.setCurrentItem(index);
    }*/


}
