package com.example.watercheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    //costants for the DB (DB name, version, tables etc)
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

    //Constructor
    public MyDBHandler(Context context) //MyDBHandler constructor
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDBHandler getInstance(Context context) {
        if (instance == null) { //no instance of MyDBHandler has been created yet
            instance = new MyDBHandler(context.getApplicationContext());
        }
        return instance;
    }

    //here it creates the DB (table profile)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES +   //creating the table
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_HEIGHT + " REAL," +
                COLUMN_WEIGHT + " REAL," +
                COLUMN_AGE + " INTEGER," +
                COLUMN_GENDER + " TEXT" +
                ")";
        db.execSQL(CREATE_PROFILES_TABLE); //creates the table
    }
    //Upgrade DB: here it deletes and recreates the same
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db); //create new "profiles" table with updated schema
    }

    //creates profile in the DB
    public int addProfile(Profile profile) { //insert new profile in "profiles" table
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues(); //store column-value pairs
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
                Profile profile = new Profile(id, name, height, weight, age, gender);//creates a new Profile object using the retrieved values from the cursor
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profileList;
    }

    //finds profile by ID
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



    //deletes profile by ID
    public void deleteProduct(int ID) {
        Profile profile = getProfileById(ID);
        if (profile != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_PROFILES, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(profile.getId()) });
            db.close();
        }
    }
}
