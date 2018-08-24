package com.mohred.studentnetwork.temp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.interfaces.HTTPObserver;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Redan on 12/29/2016.
 */
public class TempUploadActivity extends AppCompatActivity implements View.OnClickListener,HTTPObserver
{
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int PICK_PHOTO_FOR_AVATAR = 0;
    private Button pickFile;
    private ImageView imageView;
    private Button uploadFile;
    private Bitmap photoToUpload;
    private static final String TAG = "temp";
    private String path;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity);

        pickFile = (Button) findViewById(R.id.btn_choose_file);
        uploadFile = (Button)findViewById(R.id.btn_upload);
        uploadFile.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.imageview_image);
        pickFile.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK)
        {
            if (data == null)
            {
                //Display an error
                return;
            }

            Bitmap datifoto = null;
            Uri picUri = data.getData();//<- get Uri here from data intent
            if(picUri !=null)
            {
                try
                {
                    datifoto = android.provider.MediaStore.Images.Media.getBitmap(
                                                    this.getContentResolver(),
                                                    picUri);

                    photoToUpload = datifoto;

                    path = getRealPathFromURI(picUri);
                    Log.d(TAG,"URI = "+picUri);
                }
                catch (FileNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }


            }

        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_choose_file:
                pickImage();
                break;

            case R.id.btn_upload:
                upload();
                break;
        }
    }

    private void upload()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photoToUpload.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] data = baos.toByteArray();
        String path= "temp/" + UUID.randomUUID() + ".png";
        StorageReference ref = storage.getReference(path);

        StorageMetadata metadata = new StorageMetadata.Builder()
                                            .setCustomMetadata("text","ssssss")
                                        .build();


        progressBar.setVisibility(View.VISIBLE);
        UploadTask uploadTask = ref.putBytes(data,metadata);
        uploadTask.addOnSuccessListener(TempUploadActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri url = taskSnapshot.getDownloadUrl();
                Log.d(TAG,"Download URL = "+url.toString());
                progressBar.setVisibility(View.GONE);
            }
        });

        uploadTask.addOnFailureListener(TempUploadActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Failure");
                e.printStackTrace();
                Log.d(TAG,e.getStackTrace().toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void update(ConnectionObject connectionObject) {

    }
}
