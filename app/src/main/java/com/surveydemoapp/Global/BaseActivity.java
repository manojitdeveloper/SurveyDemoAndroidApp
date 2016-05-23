package com.surveydemoapp.Global;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.surveydemoapp.R;


public class BaseActivity extends Activity {

	protected TextView mid_header_text, back;
	protected Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		con = this;

	}

	/**
	 * set heading in the header
	 */
	protected void setHeading(String heading) {
		mid_header_text = (TextView) findViewById(R.id.mid_header_text);
		mid_header_text.setText(heading);
	}



	protected void backclick() {
		TextView back = (TextView) this.findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
