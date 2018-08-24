package com.mohred.studentnetwork.page_courses;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.AppUtils;
import com.mohred.studentnetwork.connection.MessageGetCourseMaterial;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import com.mohred.studentnetwork.model.CourseMaterialList;


/**
 * Created by Redan on 11/30/2016.
 */
public class FragmentCourseMaterial extends ListFragment implements View.OnClickListener
{
    private View view;
    private SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private String courseId=null;
    private ProgressBar progressBar;
    private static final String TAG = "course_material";

    public static final FragmentCourseMaterial newInstance(String courseId)
    {
        FragmentCourseMaterial fragment = new FragmentCourseMaterial();
        fragment.setCourseId(courseId);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_course_material,container,false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.button_floating);
        floatingActionButton.setOnClickListener(this);

        Log.d(TAG,"On create view");
        if(getCourseId() != null){
            Log.d(TAG,"instanitating loader ");
            //loaderManager = getLoaderManager();
            //loaderManager.restartLoader(MATERIAL_LOADER_ID, null, loaderCallbackForMaterial);
            initData();
        }

        return view;
    }

    public void initData()
    {
        new InitDataTask().execute();
    }

    private class InitDataTask extends AsyncTask<Void,Void,ConnectionObject>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConnectionObject doInBackground(Void... params) {
            try {
                MessageGetCourseMaterial message = new MessageGetCourseMaterial(getCourseId());
                return message.sendMessage();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ConnectionObject connectionObject) {
            super.onPostExecute(connectionObject);
            progressBar.setVisibility(View.GONE);
            try{
                ((ActivityCourses)getActivity()).hideProgressBar();
                // Display our data, for instance updating our adapter
                if(connectionObject == null){
                    AppUtils.showToastMessage(getContext(),getString(R.string.general_error_message));
                    Log.d(TAG,"NULL");
                    return;
                }
                CourseMaterialList materialList = (CourseMaterialList) connectionObject;
                AdapterMaterialList adapter = new AdapterMaterialList(getActivity(),materialList.getMaterialList());
                setListAdapter(adapter);
                Log.d(TAG,"size = "+materialList.getMaterialList().size());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /*
    private LoaderManager.LoaderCallbacks<ConnectionObject>
            loaderCallbackForMaterial =
            new LoaderManager.LoaderCallbacks<ConnectionObject>()
            {
                @Override
                public Loader<ConnectionObject> onCreateLoader(
                        int id, Bundle args)
                {
                    ((ActivityCourses)getActivity()).showProgressBar();
                    return new DataGetLoaderTask(getActivity(),new MessageGetCourseMaterial(getCourseId()));
                }
                @Override
                public void onLoadFinished(
                        Loader<ConnectionObject> loader, ConnectionObject data)
                {
                    ((ActivityCourses)getActivity()).hideProgressBar();
                    // Display our data, for instance updating our adapter
                    if(data == null){
                        // TODO - handle error
                        Log.d(TAG,"NULL");
                        return;
                    }
                    CourseMaterialList materialList = (CourseMaterialList) data;
                    AdapterMaterialList adapter = new AdapterMaterialList(getActivity(),materialList.getMaterialList());
                    setListAdapter(adapter);
                    Log.d(TAG,"size = "+materialList.getMaterialList().size());
                }
                @Override
                public void onLoaderReset(Loader<ConnectionObject> loader)
                {
                }
            };
    */

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_floating:
                showPostMaterialDialog();
                break;
        }
    }

    private void showPostMaterialDialog()
    {
        DialogFragmentNewCourseMaterial newFragment = DialogFragmentNewCourseMaterial.newInstance(this,courseId);
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
