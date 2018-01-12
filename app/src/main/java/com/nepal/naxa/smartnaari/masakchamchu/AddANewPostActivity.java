package com.nepal.naxa.smartnaari.masakchamchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.nepal.naxa.smartnaari.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nepal.naxa.smartnaari.R.id.parent;

/**
 * Created by Majestic on 9/17/2017.
 */

public class AddANewPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.spinnerAddANewPostCatagories)
    Spinner spinnerAddANewPostCatagories;
    @BindView(R.id.etAddANewPostTitle)
    EditText etAddANewPostTitle;
    @BindView(R.id.btnAddANewAddPostAddAnImage)
    Button btnAddANewAddPostAddAnImage;
    @BindView(R.id.spinnerAddANewPostMonth)
    Spinner spinnerAddANewPostMonth;
    @BindView(R.id.spinnerAddANewPostDay)
    Spinner spinnerAddANewPostDay;
    @BindView(R.id.spinnerAddANewPostYear)
    Spinner spinnerAddANewPostYear;
    @BindView(R.id.etAddANewPostDescription)
    EditText etAddANewPostDescription;
    @BindView(R.id.btnAddANewPostSubmit)
    Button btnAddANewPostSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_new_post);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_preference_entries, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerAddANewPostCatagories.setAdapter(adapter);
        spinnerAddANewPostCatagories.setOnItemSelectedListener(this);

}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



















