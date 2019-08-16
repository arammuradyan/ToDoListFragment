package com.example.todolistfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DoneTasksFragment extends Fragment {

    public DoneTasksFragment() {
    }

    public static DoneTasksFragment newInstance(Bundle bundle) {
        DoneTasksFragment fragment = new DoneTasksFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_done_tasks, container, false);
viewInit(view);

return view;
    }

    private void viewInit(View view){

    }


}
