package com.surveydemoapp.HomeSection;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.surveydemoapp.Global.BaseFragmentActivity;
import com.surveydemoapp.Global.Utils;
import com.surveydemoapp.HomeSection.QuestionFragment;
import com.surveydemoapp.R;
import com.surveydemoapp.database.DataBaseHelper;
import com.surveydemoapp.database.DatabaseConstant;
import com.surveydemoapp.database.DatabaseController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends BaseFragmentActivity{


    public DatabaseController databaseController;
    Activity context;
    ArrayList<JSONObject> questions_list = new ArrayList<JSONObject>();
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;
        pager = (ViewPager) findViewById(R.id.pager);

        databaseController = DatabaseController.getInstance(context);
        DataBaseHelper.getInstance(context);
        questions_list.addAll(DatabaseController.getQuestions(context));
        Utils.ClearSharedPrefence(context);

        pager.setAdapter(new QuestionsScreensAdapter(getSupportFragmentManager(), questions_list));

        Utils.SOP("qqqq"+DatabaseController.getQuestions(context));


    }


    private class QuestionsScreensAdapter extends FragmentStatePagerAdapter {

        ArrayList<JSONObject> questionList;

        public QuestionsScreensAdapter(FragmentManager fragmentManager, ArrayList<JSONObject> qeList) {
            super(getSupportFragmentManager());
            this.questionList = qeList;
            Utils.SOP("imagew" + qeList.toString());
        }

        @Override
        public Fragment getItem(int position) {

            Utils.SOP("position"+position);
            QuestionFragment welcome_frag = new QuestionFragment();
            Bundle b = new Bundle();
            b.putString("questionObject", questionList.get(position).toString());
            b.putInt("position", position);
            if((questionList.size()-1) == position){
                b.putString("last_index", "yes");
            }else{
                b.putString("last_index", "no");
            }
            b.putInt("position", position);
            welcome_frag.setArguments(b);
            return welcome_frag;

        }

        @Override
        public int getCount() {
            return questionList.size();
        }
    }

    public void next_button(int pos){



       // int nextFragment = pager.getCurrentItem() +1;
        pager.setCurrentItem(pos+1);

    }

    public void previous_button(int pos){
       // int nextFragment = pager.getCurrentItem() -1;
        pager.setCurrentItem(pos-1);
    }

    public void submit(){

        ContentValues cv = new ContentValues();
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yy hh:mm a");
        String dateStr = simpleDateFormat.format(newDate);
        cv.put(DatabaseConstant.SUBMITTED_TIME, dateStr);
        long survey_instance_id = DatabaseController.insertSurveyInstanceData(context, cv);
        Utils.SOP("iii"+survey_instance_id);

            for (int i=0; i < questions_list.size(); i++){

                try {
                    ContentValues cv_res = new ContentValues();
                    Date newDate_res = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat_res = new SimpleDateFormat("dd MMM yy hh:mm a");
                    String dateStr_res = simpleDateFormat_res.format(newDate_res);
                    cv_res.put(DatabaseConstant.QUESTION, questions_list.get(i).getString("question"));
                    cv_res.put(DatabaseConstant.ANSWER_VAl, Utils.getPreferenceValues(context, questions_list.get(i).getString("id")));
                    cv_res.put(DatabaseConstant.SURVEYID, survey_instance_id);
                    cv_res.put(DatabaseConstant.SUBMITTED_TIME, dateStr_res);
                    long id = DatabaseController.insertResponseData(context, cv_res);
                    Utils.SOP("iii"+id);
                    //Utils.SOP("1111==>"+Utils.getPreferenceValues(context, questions_list.get(i).getString("id"))+"Quest=>"+questions_list.get(i).getString("question"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        Intent intent = new Intent(MainActivity.this, ThankYouActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        Utils.Alert_Dialog(context, "Are you sure you want to exit from survey?", "");

    }
}
