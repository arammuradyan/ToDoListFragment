package com.example.todolistfragment;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskStatePagerAdapter extends FragmentStatePagerAdapter {
  private List<Task> taskList=new ArrayList<>();


    public TaskStatePagerAdapter(FragmentManager fm)
     {
        super(fm);
    }

public void addTask(Task task){
        taskList.add(task);
        notifyDataSetChanged();
}
public void deleteTask(int index){
        taskList.remove(index);
        notifyDataSetChanged();
}

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new AllTasksFragment();
            case 1:
                return new ToDoTasksFragment();
            case 2:
                return new DoneTasksFragment();
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
        case 0:
            return "ALL TASKS";
            case 1:
                return "TASKS TO DO";
            case 2:
                return "DONE TASKS";
     default:
         return null;
    }
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
