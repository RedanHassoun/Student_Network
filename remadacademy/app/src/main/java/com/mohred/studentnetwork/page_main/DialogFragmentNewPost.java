package com.mohred.studentnetwork.page_main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.common.ImageUtils;
import com.mohred.studentnetwork.connection.UploadPostMessage;
import com.mohred.studentnetwork.dialog.DialogFragmentInsertURL;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.interfaces.DialogFragmentListener;
import com.mohred.studentnetwork.interfaces.HTTPObserver;
import com.mohred.studentnetwork.managers.DataManager;
import com.mohred.studentnetwork.model.FeedItem;
import com.mohred.studentnetwork.model.Status;
import com.mohred.studentnetwork.model.User;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static com.mohred.studentnetwork.R.color.light_grey;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_NOT_OK;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.STATUS_OK;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.DIR_NAME_FIREBASE;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.IMAGE_TYPE_FIREBASE;
import static com.mohred.studentnetwork.common.AppConstants.GeneralConstants.NAME;

/**
 * Created by Redan on 12/24/2016.
 */
public class DialogFragmentNewPost extends DialogFragment implements View.OnClickListener,
                                                                     HTTPObserver,
                                                                     DialogFragmentListener
{
    private static final String TAG = "dialog_new_post";
    private FragmentNewsFeed containerFragment;
    private static final int PICK_IMAGE_ID = 234;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private View view;
    private ImageButton buttonExit;
    private EditText textStatus;
    private ImageButton buttonAttachPhoto;
    private ImageButton buttonAttachLink;
    private Button buttonCancel;
    private Button buttonSubmit;
    private TextView attachLinkName;
    private ProgressBar progressBar;
    private DataManager dataManager = DataManager.getInstance();
    private Bitmap imageToUpload = null;
    private String linkToAttach = null;


    public static DialogFragmentNewPost newInstance(FragmentNewsFeed containerFragment)
    {
        DialogFragmentNewPost f = new DialogFragmentNewPost();
        f.setContainerFragment(containerFragment);
        return f;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog  dialog = new Dialog(getActivity(), R.style.GrowFromPointTheme );
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_fragment_new_post, null);


        /*
            Exit button
         */
        Drawable exitDrawable = getActivity().getResources().getDrawable(R.drawable.ic_action_delete_sign_48px);
        exitDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.light_blue),
                PorterDuff.Mode.MULTIPLY));
        buttonExit = (ImageButton) view.findViewById(R.id.button_exit);
        buttonExit.setImageDrawable(exitDrawable);
        buttonExit.setOnClickListener(this);

        /*
            Status text
         */
        textStatus = (EditText) view.findViewById(R.id.text_status);

        /*
            Attach photo button
         */
        buttonAttachPhoto = (ImageButton) view.findViewById(R.id.button_attach_photo) ;
        buttonAttachPhoto.setOnClickListener(this);
        buttonAttachPhoto.setBackgroundColor(getResources().getColor(R.color.light_blue));

        /*
            Attach link button
         */
        buttonAttachLink = (ImageButton) view.findViewById(R.id.button_attach_link);
        buttonAttachLink.setOnClickListener(this);
        buttonAttachLink.setBackgroundColor(getResources().getColor(R.color.light_blue));

        /*
            Cancel button
         */
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);

        /*
            Submit button
         */
        buttonSubmit = (Button) view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

        /*
            Progress bar
         */
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        /*
            Image file name
         */
        attachLinkName = (TextView) view.findViewById(R.id.text_link_name);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return dialog;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_new_post_width);
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_new_post_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void	onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
    }

    @Override
    public void	onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
    }

    @Override
    public void	onStart()
    {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.60f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_submit:
                uploadPost();
                break;

            case R.id.button_exit:
                this.dismiss();
                break;

            case R.id.button_cancel:
                this.dismiss();
                break;

            case R.id.button_attach_photo:
                onPickImage();
                break;

            case R.id.button_attach_link:
                DialogFragmentInsertURL newFragment = DialogFragmentInsertURL.newInstance(this);
                newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                break;
        }
    }

    /**
     * This method is invoked when the user clicks on 'Add post' and it's responsible
     * for managing the adding post proccess. like follows :
     *      1. Upload the image to Firebase (if exists)
     *      2. Create a post message
     *      3. Add post to App DB
     * */
    private void uploadPost()
    {
        if(textStatus.getText().toString().equals(""))
        {
            AppUtils.showToastMessage(getActivity(),getString(R.string.you_have_to_write_a_status));
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        if(imageToUpload != null)
            uploadImageToFirebase();
        else
            uploadPostToAppServer(null);
    }

    private void uploadPostToAppServer(Uri imageURL)
    {
        FeedItem feedItem = new FeedItem();
        User u=DataManager.getInstance().getCurrentUser(getActivity());
        feedItem.setOgranizationId(u.getOrganizationId());
        feedItem.setStatus(textStatus.getText().toString());
        feedItem.setUserId(""+dataManager.getCurrentUser(getActivity()).getId());

        if(imageURL != null)
            feedItem.setPostImageURL(imageURL.toString());

        if(linkToAttach != null)
            feedItem.setExternamURL(linkToAttach);

        UploadPostMessage upldPost = new UploadPostMessage();
        dataManager.getHttpExecutionPool().sendPostMessage(upldPost,this,feedItem);
    }

    private void uploadImageToFirebase()
    {
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
                Log.d(TAG,"Download URL = "+url.toString());
                uploadPostToAppServer(url);
            }
        });

        uploadTask.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                showMessageAndStop(getString(R.string.error_uploading_image));
            }
        });
    }

    private void showMessageAndStop(String message)
    {
        progressBar.setVisibility(View.GONE);
        AppUtils.showToastMessage(getActivity(),message);
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
                buttonAttachPhoto.setBackgroundColor(getResources().getColor(light_grey)); // TODO - deprecated
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity()
                                .getContentResolver()
                                .query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void update(final ConnectionObject connectionObject)
    {
        if(getActivity() == null)
            return;
        getActivity().runOnUiThread(new Runnable()
        {
            public void run()
            {
                if(connectionObject == null)
                {
                    showMessageAndStop(getString(R.string.error_connecting_to_server));
                }

                if(connectionObject instanceof Status)
                {
                    Status status = (Status) connectionObject;
                    switch(status.getStatus())
                    {
                        case STATUS_OK:
                            showMessageAndStop(getString(R.string.post_uploaded_successfully));
                            getContainerFragment().initFeed();
                            DialogFragmentNewPost.this.dismiss();
                            break;

                        case STATUS_NOT_OK:
                            showMessageAndStop(getString(R.string.error_connecting_to_server));
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        EditText editText  = (EditText) dialog.getDialog().findViewById(R.id.link);
        if( URLUtil.isValidUrl(editText.getText().toString()))
        {
            linkToAttach = editText.getText().toString();
            attachLinkName.setText(linkToAttach);
            buttonAttachLink.setBackgroundColor(getResources().getColor(light_grey));
        }
        else
        {
            AppUtils.showToastMessage(getActivity(),getString(R.string.please_enter_valid_url));
        }
        Log.d(TAG,"Link = "+editText.getText().toString());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {

    }

    public FragmentNewsFeed getContainerFragment() {
        return containerFragment;
    }

    public void setContainerFragment(FragmentNewsFeed containerFragment) {
        this.containerFragment = containerFragment;
    }
}
