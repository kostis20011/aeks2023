package com.example.watercheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "watercheck.db";
    private static final String TABLE_PROFILES = "profiles";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";

    private static MyDBHandler instance;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new MyDBHandler(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_HEIGHT + " REAL," +
                COLUMN_WEIGHT + " REAL," +
                COLUMN_AGE + " INTEGER," +
                COLUMN_GENDER + " TEXT" +
                ")";
        db.execSQL(CREATE_PROFILES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db);
    }

    public int addProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, profile.getName());
        values.put(COLUMN_HEIGHT, profile.getHeight());
        values.put(COLUMN_WEIGHT, profile.getWeight());
        values.put(COLUMN_AGE, profile.getAge());
        values.put(COLUMN_GENDER, profile.getGender());
        long id = db.insert(TABLE_PROFILES, null, values);
        db.close();
        return (int) id;
    }

    public List<Profile> getProfiles() {
        List<Profile> profileList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_AGE, COLUMN_GENDER};
        Cursor cursor = db.query(TABLE_PROFILES, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int heightIndex = cursor.getColumnIndex(COLUMN_HEIGHT);
            int weightIndex = cursor.getColumnIndex(COLUMN_WEIGHT);
            int ageIndex = cursor.getColumnIndex(COLUMN_AGE);
            int genderIndex = cursor.getColumnIndex(COLUMN_GENDER);
            do {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                double height = cursor.getDouble(heightIndex);
                double weight = cursor.getDouble(weightIndex);
                int age = cursor.getInt(ageIndex);
                String gender = cursor.getString(genderIndex);
                Profile profile = new Profile(id, name, height, weight, age, gender);
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profileList;
    }


    public Profile getProfileById(int profileId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_AGE, COLUMN_GENDER};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(profileId)};
        Cursor cursor = db.query(TABLE_PROFILES, columns, selection, selectionArgs, null, null, null);

        Profile profile = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int heightIndex = cursor.getColumnIndex(COLUMN_HEIGHT);
            int weightIndex = cursor.getColumnIndex(COLUMN_WEIGHT);
            int ageIndex = cursor.getColumnIndex(COLUMN_AGE);
            int genderIndex = cursor.getColumnIndex(COLUMN_GENDER);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            double height = cursor.getDouble(heightIndex);
            double weight = cursor.getDouble(weightIndex);
            int age = cursor.getInt(ageIndex);
            String gender = cursor.getString(genderIndex);

            profile = new Profile(id, name, height, weight, age, gender);
        }

        cursor.close();
        db.close();

        return profile;
    }



    public void deleteProfile(int profileId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PROFILES, COLUMN_ID + "=?", new String[]{String.valueOf(profileId)});
        db.close();
    }
}
