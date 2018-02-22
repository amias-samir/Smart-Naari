package com.nepal.naxa.smartnaari.tapitstopit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadListener;
import com.nepal.naxa.smartnaari.aboutboardmembers.JSONAssetLoadTask;
import com.nepal.naxa.smartnaari.common.BaseActivity;
import com.nepal.naxa.smartnaari.homescreen.GridSpacingItemDecoration;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TapItStopItActivity extends BaseActivity implements TapItStopItRecyclerAdapter.OnItemClickListener, JSONAssetLoadListener {

    private static String TAG = "TapItStopItActivity";

    @BindView(R.id.rvTapItStopIt)
    RecyclerView recyclerViewTapItStopIt;
    private TapItStopItRecyclerAdapter adapter;

    private JSONAssetLoadTask jsonAssetLoadTask;
    List<TapItStopItPOJO> tapItStopItConactList = new ArrayList<>();
    TapItStopItDialogListAdapter tapItStopItDialogListAdapter;

    List<TapItStopItPOJO> tapItStopItfilteredList = new ArrayList<>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_it_stop_it);
        ButterKnife.bind(this);

        initToolbar();

        initGridRecyclerView();
        setRecyclerAdapter(recyclerViewTapItStopIt);

        jsonAssetLoadTask = new JSONAssetLoadTask(R.raw.emergency_numbers, this, this);
        jsonAssetLoadTask.execute();

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Tap It Stop It");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }


    private void initGridRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewTapItStopIt.setLayoutManager(gridLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_large);
        recyclerViewTapItStopIt.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));
        recyclerViewTapItStopIt.setNestedScrollingEnabled(false);


    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {


        List<ViewModel> items = ViewModel.getTapItStopItGridItem();
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new TapItStopItRecyclerAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        Log.e("TapItStopIt", "onItemClick: " + viewModel.getText());

        if (viewModel.getText().equals("Police")) {
            showEmergencyContactListDialog("Police");

        } else if (viewModel.getText().equals("Ambulance")) {
            showEmergencyContactListDialog("Ambulance");


        } else if (viewModel.getText().equals("Hospitals")) {
            showEmergencyContactListDialog("Hospitals");

        } else if (viewModel.getText().equals("Hotline")) {
            showEmergencyContactListDialog("Hotline");

        } else if (viewModel.getText().equals("Fire Brigade")) {
            showEmergencyContactListDialog("Fire_Brigade");

        } else if ((viewModel.getText().equals("Blood Bank"))) {
            showEmergencyContactListDialog("Blood_Bank");

        }
    }


    @Override
    public void onFileLoadComplete(String s) {

        Type listType = new TypeToken<List<TapItStopItPOJO>>() {
        }.getType();
        tapItStopItConactList = new Gson().fromJson(s, listType);

//        adapter.setMembers(members);

        Log.e("TapItStopIt", "This data is: " + s);
    }

    @Override
    public void onFileLoadError(String errorMsg) {
    }


    public void showEmergencyContactListDialog(String type) {
        tapItStopItfilteredList.clear();

        Log.d(TAG, "showServicesListDialog: " + tapItStopItConactList.size());

        for (int i = 0; i < tapItStopItConactList.size(); i++) {
            if(type.equals(tapItStopItConactList.get(i).getType())) {

                tapItStopItfilteredList.add(tapItStopItConactList.get(i));
                Log.d(TAG, "showEmergencyContactDialog: " + tapItStopItConactList.get(i).getName());
            }

        }


        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(this);
        showDialog.setContentView(R.layout.tap_it_stop_it_contact_dilog);

//         initialize
        RecyclerView rvContactDialogList;
//        TextView tvHeader = (TextView) showDialog.findViewById(R.id.emergency_dialog_title);
        Button btnClose = (Button) showDialog.findViewById(R.id.btn_close_dialog);
        rvContactDialogList = (RecyclerView) showDialog.findViewById(R.id.rv_contact_list);

//        set recycler adapter
        tapItStopItDialogListAdapter = new TapItStopItDialogListAdapter(tapItStopItfilteredList);
        rvContactDialogList.setAdapter(tapItStopItDialogListAdapter);
        rvContactDialogList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        showDialog.setTitle(type+" Contact List");
        showDialog.getActionBar();
        showDialog.show();
        showDialog.getWindow().setLayout((width), LinearLayout.LayoutParams.WRAP_CONTENT);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog.dismiss();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem tapItStopIt = menu.findItem(R.id.item_call);
        MenuItem notification = menu.findItem(R.id.item_notification);


        tapItStopIt.setVisible(false);
        notification.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
