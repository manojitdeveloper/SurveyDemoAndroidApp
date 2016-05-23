package com.surveydemoapp.HomeSection;

import android.app.Activity;
import android.os.Bundle;

import com.surveydemoapp.Global.BaseActivity;
import com.surveydemoapp.R;

public class ThankYouActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thankyou);

        setHeading("Thank You");
    }
}
