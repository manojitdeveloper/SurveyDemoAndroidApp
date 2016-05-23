package com.surveydemoapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.surveydemoapp.Global.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    //@Singleton single instance reference of database instance
    private static DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    private static String DB_PATH;

    private static String DB_NAME = "survey_db.db";

    public SQLiteDatabase otcDB;

    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

        DB_PATH = this.context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        Utils.SOP("path=====" + DB_PATH);
        try {
            createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }
    /*
    * SingletonClass
    */
    public static DataBaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DataBaseHelper(context);
        }
        return dbHelper;
    }


    private void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public SQLiteDatabase openDataBase(int DB_Mode) throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        return SQLiteDatabase.openDatabase(myPath, null, DB_Mode);

    }

    @Override
    public synchronized void close() {

        if (otcDB != null)
            otcDB.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
