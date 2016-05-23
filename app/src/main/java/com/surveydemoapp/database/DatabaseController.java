package com.surveydemoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import com.surveydemoapp.Global.Utils;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseController {


    private static Context context;
    private static SQLiteDatabase myDataBase;
    private static DataBaseHelper dbHelper;
    private static DatabaseController databaseController;

    /*
   * Method to insert data in table
   */
    public DatabaseController(Context context) {
        this.context = context;
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseController getInstance(Context context) {
        Utils.SOP("askjhalsk");
        if (databaseController == null) {
            databaseController = new DatabaseController(context);
        }
        return databaseController;
    }

    public DatabaseController open() throws SQLException {

        if (context != null)
            dbHelper = new DataBaseHelper(context);

        myDataBase = dbHelper.getWritableDatabase();
        return this;

    }

    public void close() {

        dbHelper.close();

    }


    /*
    * Method to fetch questions from table
    */
    public static ArrayList<JSONObject> getQuestions(Context context) {

        Utils.SOP("aertyuio");

        ArrayList<JSONObject> chatHistory = new ArrayList<JSONObject>();
        try {


            String sql = "select * from " + DatabaseConstant.QUESTION;


            Utils.SOP("DB==getQuestion==" + sql);

            Cursor cursor = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).rawQuery(sql, null);
            Utils.SOP("DB==getQuestion==" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put(DatabaseConstant.ID,cursor.getString(cursor.getColumnIndex(DatabaseConstant.ID)));
                    jsonObject.put(DatabaseConstant.QUESTION,cursor.getString(cursor.getColumnIndex(DatabaseConstant.QUESTION)));
                    jsonObject.put(DatabaseConstant.ANSWER_TYPE,cursor.getString(cursor.getColumnIndex(DatabaseConstant.ANSWER_TYPE)));

                chatHistory.add(jsonObject);

                } while (cursor.moveToNext());

            }
            Utils.SOP("after added==" + chatHistory);
            cursor.close();

            Utils.SOP("size ==" + chatHistory.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatHistory;
    }

    /*
    * Method to fetch answers from table
    */
    public static String getAnswer(Context context, String question_id) {

        String answers = "";
        try {

            String sql = "select * from " + DatabaseConstant.ANSWER + " where "+DatabaseConstant.QUESTION_ID+" ="+question_id;

            Cursor cursor = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                answers = cursor.getString(cursor.getColumnIndex(DatabaseConstant.ANSWER_VAl));
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    public static long insertResponseData(Context context, ContentValues values) {
        long id = -1;
        try {
            id = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).insert(DatabaseConstant.RESPONSES, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static long insertSurveyInstanceData(Context context, ContentValues values) {
        long id = -1;
        try {
            id = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).insert(DatabaseConstant.SURVEYINSTANCE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    /*
   * Method to fetch survey instance from table
   */
    public static ArrayList<JSONObject> getSurveyInstance(Context context) {

        ArrayList<JSONObject> surevyInstance = new ArrayList<JSONObject>();
        try {


            String sql = "select * from " + DatabaseConstant.SURVEYINSTANCE;


            Utils.SOP("DB==getQuestion==" + sql);

            Cursor cursor = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).rawQuery(sql, null);
            Utils.SOP("DB==getQuestion==" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put(DatabaseConstant.ID,cursor.getString(cursor.getColumnIndex(DatabaseConstant.ID)));
                    jsonObject.put(DatabaseConstant.SUBMITTED_TIME,cursor.getString(cursor.getColumnIndex(DatabaseConstant.SUBMITTED_TIME)));
                    surevyInstance.add(jsonObject);

                } while (cursor.moveToNext());

            }
            Utils.SOP("after added==" + surevyInstance);
            cursor.close();

            Utils.SOP("size ==" + surevyInstance.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return surevyInstance;
    }

    /*
   * Method to fetch responses from table
   */
    public static ArrayList<JSONObject> getResponsesInstance(Context context, String survey_id) {

        Utils.SOP("aaa"+survey_id);
        ArrayList<JSONObject> responses = new ArrayList<JSONObject>();
        try {


            String sql = "select * from " + DatabaseConstant.RESPONSES + " where "+ DatabaseConstant.SURVEYID +"="+survey_id;


            Utils.SOP("DB==getQuestion==" + sql);

            Cursor cursor = DataBaseHelper.getInstance(context).openDataBase(SQLiteDatabase.OPEN_READWRITE).rawQuery(sql, null);
            Utils.SOP("DB==getQuestion==" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put(DatabaseConstant.ID,cursor.getString(cursor.getColumnIndex(DatabaseConstant.ID)));
                    jsonObject.put(DatabaseConstant.QUESTION,cursor.getString(cursor.getColumnIndex(DatabaseConstant.QUESTION)));
                    jsonObject.put(DatabaseConstant.ANSWER_VAl,cursor.getString(cursor.getColumnIndex(DatabaseConstant.ANSWER_VAl)));
                    jsonObject.put(DatabaseConstant.SURVEYID,cursor.getString(cursor.getColumnIndex(DatabaseConstant.SURVEYID)));
                    jsonObject.put(DatabaseConstant.SUBMITTED_TIME,cursor.getString(cursor.getColumnIndex(DatabaseConstant.SUBMITTED_TIME)));
                    responses.add(jsonObject);

                } while (cursor.moveToNext());

            }
            Utils.SOP("after added==" + responses);
            cursor.close();

            Utils.SOP("size ==" + responses.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responses;
    }



}
