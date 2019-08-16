package com.example.todolistfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;


public class Utils {


     void setBitmap(Context context, ImageView view, Uri uri){
        Bitmap bitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bitmap!=null){
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/5, bitmap.getHeight()/5,true);
            view.setImageBitmap(scaledBitmap);}

    }

     void onBackgroundColorSet(Task task,View view,PriorityView priorityView){
        if (task.getType() == 5.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_HIGH);
            view.setBackgroundResource(R.drawable.view_5_background);
        } else if (task.getType() == 4.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_HIGH);
            view.setBackgroundResource(R.drawable.view_4_background);
        } else if (task.getType() == 3.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_MEDIUM);
            view.setBackgroundResource(R.drawable.view_3_background);
        } else if (task.getType() == 2.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_LOW);
            view.setBackgroundResource(R.drawable.view_2_background);
        } else if (task.getType() == 1.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_LOW);
            view.setBackgroundResource(R.drawable.view_1_background);
        } else{ priorityView.setPriority(PriorityView.PRIORITY_DEFAULT);
                view.setBackgroundResource(R.drawable.view_backgroound);}
         view.setAlpha(0.6f);


     }
    void onBackgroundColorSet(float raiting,View view,PriorityView priorityView){
        if (raiting == 5.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_HIGH);
            view.setBackgroundResource(R.drawable.view_5_background);
        } else if (raiting == 4.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_HIGH);
            view.setBackgroundResource(R.drawable.view_4_background);
        } else if (raiting == 3.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_MEDIUM);
            view.setBackgroundResource(R.drawable.view_3_background);
        } else if (raiting == 2.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_LOW);
            view.setBackgroundResource(R.drawable.view_2_background);
        } else if (raiting == 1.0f) {
            priorityView.setPriority(PriorityView.PRIORITY_LOW);
            view.setBackgroundResource(R.drawable.view_1_background);
        } else{priorityView.setPriority(PriorityView.PRIORITY_DEFAULT);
               view.setBackgroundResource(R.drawable.view_backgroound);}
        view.setAlpha(0.6f);


    }

}
