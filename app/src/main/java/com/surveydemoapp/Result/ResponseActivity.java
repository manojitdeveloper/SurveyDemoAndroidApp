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
import com.surveydemoapp.Global.Utils;
import com.surveydemoapp.R;
import com.surveydemoapp.database.DataBaseHelper;
import com.surveydemoapp.database.DatabaseConstant;
import com.surveydemoapp.database.DatabaseController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ResponseActivity extends BaseActivity{

    Context context;
    ListView survey_instance;
    SurveyAdapter surveyAdapter;
    ArrayList<JSONObject> response_instance_object = new ArrayList<JSONObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_instance);
        context = this;

        setHeading("Your Response");
        if(getIntent().hasExtra("survey_id")){
            response_instance_object.addAll(DatabaseController.getResponsesInstance(context, getIntent().getStringExtra("survey_id")));
        }

        Utils.SOP("aaaa"+response_instance_object);
        survey_instance = (ListView) findViewById(R.id.survey_instance);
        surveyAdapter = new SurveyAdapter(context);
        survey_instance.setAdapter(surveyAdapter);

    }

    private class SurveyAdapter extends BaseAdapter {

        Context context;

        public SurveyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return response_instance_object.size();
        }

        @Override
        public Object getItem(int position) {
            return response_instance_object.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {

            final Holder holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_response_instance, null);
                holder = new Holder();
                holder.question = (TextView) convertView.findViewById(R.id.question);
                holder.answer = (TextView) convertView.findViewById(R.id.answer);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            try {
                holder.question.setText(response_instance_object.get(pos).getString(DatabaseConstant.QUESTION));
                if(response_instance_object.get(pos).getString(DatabaseConstant.ANSWER_VAl).equalsIgnoreCase("")){
                    holder.answer.setText("No answer");
                    holder.answer.setTextColor(getResources().getColor(R.color.red));
                }else{
                    holder.answer.setText(response_instance_object.get(pos).getString(DatabaseConstant.ANSWER_VAl));
                    holder.answer.setTextColor(getResources().getColor(R.color.black));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }

        private class Holder {
            TextView question, answer;
        }


    }

}
