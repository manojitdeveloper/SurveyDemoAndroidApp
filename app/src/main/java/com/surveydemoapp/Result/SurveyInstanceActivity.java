package com.surveydemoapp.Result;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.surveydemoapp.Global.BaseActivity;
import com.surveydemoapp.R;
import com.surveydemoapp.database.DataBaseHelper;
import com.surveydemoapp.database.DatabaseConstant;
import com.surveydemoapp.database.DatabaseController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SurveyInstanceActivity extends BaseActivity {

    ListView survey_instance;
    SurveyAdapter surveyAdapter;
    ArrayList<JSONObject> survey_instance_object = new ArrayList<JSONObject>();
    Context context;
    public DatabaseController databaseController;
    TextView no_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_instance);
        context = this;

        setHeading("Survey Instances");
        no_content = (TextView) findViewById(R.id.no_content);

        databaseController = DatabaseController.getInstance(context);
        DataBaseHelper.getInstance(context);
        survey_instance_object.addAll(DatabaseController.getSurveyInstance(context));
        survey_instance = (ListView) findViewById(R.id.survey_instance);

        if(survey_instance_object.size() >0 ){
            survey_instance.setVisibility(View.VISIBLE);
            no_content.setVisibility(View.GONE);
            surveyAdapter = new SurveyAdapter(context);
            survey_instance.setAdapter(surveyAdapter);

        }else{
            survey_instance.setVisibility(View.GONE);
            no_content.setVisibility(View.VISIBLE);
        }


    }


    private class SurveyAdapter extends BaseAdapter {

        Context context;

        public SurveyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return survey_instance_object.size();
        }

        @Override
        public Object getItem(int position) {
            return survey_instance_object.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup viewGroup) {

            final Holder holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_survey_instance, null);
                holder = new Holder();
                holder.survey_count = (TextView) convertView.findViewById(R.id.survey_count);
                holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
                holder.action = (TextView) convertView.findViewById(R.id.action);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.survey_count.setText("Survey "+(pos+1));
            try {
                holder.date_time.setText(survey_instance_object.get(pos).getString(DatabaseConstant.SUBMITTED_TIME));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(SurveyInstanceActivity.this, ResponseActivity.class);
                    try {
                        intent.putExtra("survey_id", survey_instance_object.get(pos).getString(DatabaseConstant.ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);

                }
            });

            return convertView;
        }

        private class Holder {
            TextView survey_count, date_time, action;
        }


    }
}
