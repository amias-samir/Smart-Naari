package com.nepal.naxa.smartnaari.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.MainActivity;

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        startActivity(new Intent(this, MainActivity.class));

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sex_male:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_sex_female:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
