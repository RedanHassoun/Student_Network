package com.mohred.studentnetwork.page_profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityBase;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.common.ImageUtils;
import com.mohred.studentnetwork.connection.DataPostLoaderTask;
import com.mohred.studentnetwork.connection.MessageUpdateUserImage;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.User;
import com.mohred.studentnetwork.model.UserMessage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.DIR_NAME_FIREBASE;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.IMAGE_TYPE_FIREBASE;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.NAME;

/**
 * Created by Redan on 3/11/2017.
 */

public class DialogFragmentUploadProfilePhoto extends DialogFragment implements View.OnClickListener
{
    private ImageView imageProfile;
    private Button buttonCancel;
    private Button buttonUpload;
    private String imageURL;
    private FloatingActionButton buttonEditPhoto;
    private static final int PICK_IMAGE_ID = 2345;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Bitmap imageToUpload = null;
    private static final int UPLOAD_PHOTO_LOADER_ID = 150;
    private LoaderManager loaderManager;
    private ProgressBar progressBar;
    private static final String TAG = "dialog_upload_photo";
    private ImageButton buttonExit;
    private View view;

    public static DialogFragmentUploadProfilePhoto newInstance() {
        DialogFragmentUploadProfilePhoto fragment = new DialogFragmentUploadProfilePhoto();
        return fragment;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog  dialog = new Dialog(getActivity(), R.style.GrowFromPointTheme );
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_fragment_upload_profile_photo, null);

        initView();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private void initView()
    {
        imageProfile = (ImageView) view.findViewById(R.id.image_profile);
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);
        buttonUpload = (Button) view.findViewById(R.id.button_upload);
        buttonUpload.setOnClickListener(this);
        imageURL= DataManager.getInstance().getCurrentUser(getContext()).getImageURL();
        buttonEditPhoto = (FloatingActionButton) view.findViewById(R.id.button_edit_photo);
        buttonEditPhoto.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        buttonExit = (ImageButton)view.findViewById(R.id.button_exit);
        Drawable drawable = AppUtils.customizeDrawable(getContext(),
                                                       R.drawable.ic_action_delete_sign_48px);
        buttonExit.setImageDrawable(drawable);
        buttonExit.setOnClickListener(this);

        try {
            if( imageURL != null && !imageURL.equals(""))
                Picasso.with(getContext())
                        .load(imageURL)
                        .placeholder(R.drawable.loading_icon)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .into(imageProfile);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.button_cancel:
                DialogFragmentUploadProfilePhoto.this.dismiss();
                break;

            case R.id.button_upload:
                uploadNewPhoto();
                break;

            case R.id.button_edit_photo:
                onPickImage();
                break;

            case R.id.button_exit:
                dismiss();
                break;
        }
    }

    private void uploadNewPhoto()
    {
        if(imageToUpload == null){
            AppUtils.showToastMessage(getContext(),getString(R.string.you_have_to_choose_photo));
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageToUpload.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] data = baos.toByteArray();
        String path= DIR_NAME_FIREBASE + "/" + UUID.randomUUID() + IMAGE_TYPE_FIREBASE;

        StorageReference ref = storage.getReference(path);

        User user = DataManager.getInstance().getCurrentUser(getActivity());

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata(NAME,user.getUsername())
                .build();

        UploadTask uploadTask = ref.putBytes(data,metadata);
        uploadTask.addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri url = taskSnapshot.getDownloadUrl();
                imageURL = url.toString();

                /* Update image URL on the application Database */
                User user = DataManager.getInstance().getCurrentUser(getContext());
                UserMessage userMessage = user.getUserMessage();
                userMessage.setImageURL(imageURL);
                loaderManager = getLoaderManager();
                loaderManager.restartLoader(UPLOAD_PHOTO_LOADER_ID,
                                            null,
                                            new UploadUserPhotoLoader(userMessage));

                Log.d(TAG,"the URL = "+imageURL);
            }
        });

        uploadTask.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                progressBar.setVisibility(View.GONE);
                AppUtils.showToastMessage(getContext(),getString(R.string.error_uploading_image));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    private class UploadUserPhotoLoader implements LoaderManager.LoaderCallbacks<ConnectionObject>
    {
        private UserMessage user;
        public UploadUserPhotoLoader(UserMessage user)
        {
            this.user = user;
        }
        @Override
        public Loader<ConnectionObject> onCreateLoader(
                int id, Bundle args)
        {
            return new DataPostLoaderTask(getActivity(),new MessageUpdateUserImage(),user);
        }
        @Override
        public void onLoadFinished(
                Loader<ConnectionObject> loader, ConnectionObject data)
        {
            progressBar.setVisibility(View.GONE);
            // Display our data, for instance updating our adapter
            if(data == null){
                AppUtils.showToastMessage(getContext(),getString(R.string.error_connecting_to_server));
                Log.d(TAG,"NULL");
                return;
            }

            try
            {
                Status status = (Status) data;
                switch (status.getStatus()){
                    case STATUS_OK:
                        AppUtils.showToastMessage(getContext(),getString(R.string.image_updated_successfully));
                        User currUser = DataManager.getInstance().getCurrentUser(getContext());
                        currUser.setImageURL(imageURL);
                        DataManager.getInstance().setCurrentUser(currUser,getContext());
                        ((ActivityBase)getActivity()).openActivity(ActivityProfile.class);
                        getActivity().finish();
                        UIDismissHandler handler = new UIDismissHandler();
                        handler.sendMessage(new Message());
                        break;
                    default:
                        AppUtils.showToastMessage(getContext(),
                                                  getString(R.string.error_occurred_while_uploading_image));
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
                AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
            }


        }
        @Override
        public void onLoaderReset(Loader<ConnectionObject> loader)
        {
        }
    }

    private void onPickImage()
    {
        Intent chooseImageIntent = ImageUtils.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode)
        {
            case PICK_IMAGE_ID:
                imageToUpload = ImageUtils.getImageFromResult(getActivity(),
                        resultCode,
                        data);
                if(imageToUpload != null){
                    Drawable drawable = new BitmapDrawable(getResources(), imageToUpload);
                    imageProfile.setImageDrawable(drawable);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    private class UIDismissHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogFragmentUploadProfilePhoto.this.dismiss();
        }
    }
}
