package com.example.todolistfragment;



import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import maes.tech.intentanim.CustomIntent;

public class PreviewActivity extends AppCompatActivity {

    public PriorityView priorityView;
    public ImageView img_preview;
    public Button btn_add_preview;
    public RatingBar rb_preview;
    public EditText et_name_preview;
    public EditText et_discription_preview;
    public Button btn_chose_photo_preview;
    public Button btn_delete;
    public View view_preview;

    public final int IMAGE_URI_REQUEST_CODE=23;
    private final int STORAGE_READ_REQUEST_CODE=654;

    private String currentImagePath=null;
    private Uri photoURI=Uri.EMPTY;
    private Uri uriForPermisiionRequest=Uri.EMPTY;
    private Utils utils;

    int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        utils=new Utils();
        viewInitialization();

        Intent stateIntent = getIntent();
        if (stateIntent.getBundleExtra("TaskToPreview") !=null) {

            Bundle taskBundle=stateIntent.getBundleExtra("TaskToPreview");
            Task taskToPreview=(Task) taskBundle.get("TaskToPreview");

            utils.onBackgroundColorSet(taskToPreview,view_preview,priorityView);

            btn_delete.setVisibility(Button.VISIBLE);
            btn_add_preview.setText(R.string.save_btn);

            rb_preview.setRating(taskToPreview.getType());
            et_name_preview.setText(taskToPreview.getName());
            et_discription_preview.setText(taskToPreview.getDescription());

            Uri uri=Uri.parse(taskToPreview.getImgUri());
            uriForPermisiionRequest=uri;

          if( ActivityCompat.checkSelfPermission(PreviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.
                    PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PreviewActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_READ_REQUEST_CODE);

            }else{ utils.setBitmap(PreviewActivity.this,img_preview,uri);}

        }
        btn_chose_photo_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_add_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                if (intent.getBundleExtra("TaskToPreview")==null) {
                    Task addTask = new Task(et_name_preview.getText().toString(), et_discription_preview.getText().toString(),rb_preview.
                            getRating(),photoURI.toString());
                    Bundle addBundle=new Bundle();
                    addBundle.putParcelable("addTask",addTask);
                    Intent addIntent=new Intent();
                    addIntent.putExtra("addTask",addBundle);
                    CustomIntent.customType(PreviewActivity.this,"fadein-to-fadeout");
                    type=1;
                    setResult(RESULT_OK,addIntent);
                   // finish()
                    // supportFinishAfterTransition();
                    ActivityCompat. finishAfterTransition(PreviewActivity.this);


                }
                else{
                    int index = intent.getIntExtra("index", -100);
                    Bundle bundle=intent.getBundleExtra("TaskToPreview");
                    Task saveTask=bundle.getParcelable("TaskToPreview");
                    Task task;
                    if(photoURI==Uri.EMPTY){
                        task = new Task(et_name_preview.getText().toString(), et_discription_preview.getText().toString(),
                                rb_preview.getRating(),saveTask.getImgUri());
                    }else{
                        task = new Task(et_name_preview.getText().toString(), et_discription_preview.getText().toString(),
                                rb_preview.getRating(),photoURI.toString());}
                    Intent saveIntent=new Intent();
                    Bundle saveBundle= new Bundle();
                    saveBundle.putParcelable("saveTask",task);
                    saveIntent.putExtra("saveTask",saveBundle);
                    saveIntent.putExtra("index",index);
                    setResult(RESULT_OK,saveIntent);

                    ActivityCompat. finishAfterTransition(PreviewActivity.this);
                    //supportFinishAfterTransition();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receivedDeleteIntent = getIntent();
                int index = receivedDeleteIntent.getIntExtra("index", -100);
                Intent deleteIntent=new Intent();
                deleteIntent.putExtra("index",index);
                setResult(RESULT_OK,deleteIntent);

                //finish();
               // supportFinishAfterTransition();
                ActivityCompat. finishAfterTransition(PreviewActivity.this);

            }
        });
        rb_preview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                utils.onBackgroundColorSet(rating,view_preview,priorityView);
            }
        });
    }

 @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"fadein-to-fadeout");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_READ_REQUEST_CODE){
            for (int i = 0; i <permissions.length ; i++) {
                if ( permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    utils.setBitmap(PreviewActivity.this,img_preview,uriForPermisiionRequest);
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_URI_REQUEST_CODE && resultCode == RESULT_OK) {

            if(photoURI == null){
                photoURI=data.getData();
            }
            utils.setBitmap(PreviewActivity.this,img_preview,photoURI);

        }
    }

    public void viewInitialization(){
        priorityView=findViewById(R.id.priority_view);
        img_preview=findViewById(R.id.imgView);
        btn_add_preview=findViewById(R.id.add);
        btn_delete=findViewById(R.id.btn_delete);
        btn_chose_photo_preview=findViewById(R.id.pic_chose_btn);
        et_name_preview=findViewById(R.id.et_name);
        et_discription_preview=findViewById(R.id.et_description);
        rb_preview=findViewById(R.id.rb_type);
        view_preview=findViewById(R.id.view);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentImagePath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,IMAGE_URI_REQUEST_CODE);
                CustomIntent.customType(this,"fadein-to-fadeout");

            }
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentImagePath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
public void selectImage(){
        final Dialog imageSelectDialog=new Dialog(this);
        final View view=getLayoutInflater().inflate(R.layout.dialog_layout,null);
              imageSelectDialog.setContentView(view);
              imageSelectDialog.setTitle("SELECT IMAGE");
    imageSelectDialog.create();

view.findViewById(R.id.camera_img).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imageSelectDialog.dismiss();
        dispatchTakePictureIntent();
    }
});
   view.findViewById(R.id.gallery_img).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent uriIntent=new Intent(Intent.ACTION_PICK);
            uriIntent.setType("image/*");
            photoURI=null;
            startActivityForResult(uriIntent,IMAGE_URI_REQUEST_CODE);
            CustomIntent.customType(PreviewActivity.this,"fadein-to-fadeout");
            imageSelectDialog.dismiss();

        }
    });
    view.findViewById(R.id.cancel_img).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageSelectDialog.dismiss();

        }
    });
   imageSelectDialog.show();
}
}
