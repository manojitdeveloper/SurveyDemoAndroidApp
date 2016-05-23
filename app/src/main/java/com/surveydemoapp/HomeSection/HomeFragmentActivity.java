package com.surveydemoapp.HomeSection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.navdrawer.SimpleSideDrawer;
import com.surveydemoapp.Global.BaseActivity;
import com.surveydemoapp.Global.BaseFragmentActivity;
import com.surveydemoapp.Global.Utils;
import com.surveydemoapp.R;
import com.surveydemoapp.Result.SurveyInstanceActivity;


public class HomeFragmentActivity extends BaseActivity implements View.OnClickListener {

    Activity context;
    private TextView submit_survery, responses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        context = this;

        submit_survery = (TextView) findViewById(R.id.submit_survery);
        responses = (TextView) findViewById(R.id.responses);
        setHeading("Survey App");

        submit_survery.setOnClickListener(this);
        responses.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submit_survery:

                Intent intent = new Intent(HomeFragmentActivity.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.responses:


                Intent intent_survey = new Intent(HomeFragmentActivity.this, SurveyInstanceActivity.class);
                startActivity(intent_survey);

                break;

            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        Utils.Alert_Dialog(context, getResources().getString(R.string.exit_message), "");

    }

}
