package com.example.watercheck;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

    public class MyDBHandler extends SQLiteOpenHelper {
        //Σταθερές για τη ΒΔ (όνομα ΒΔ, έκδοση, πίνακες κλπ)
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "profileDB.db";
        public static final String TABLE_PROFILE = "profile";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_HEIGHT = "height";

        //Constructor
        public MyDBHandler(Context context, String name,
                           SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        //Δημιουργία του σχήματος της ΒΔ (πίνακας products)
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_PROFILE_TABLE = "CREATE TABLE " +
                    TABLE_PROFILE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_GENDER + " TEXT," +
                    COLUMN_AGE + " INTEGER," +
                    COLUMN_WEIGHT + " DOUBLE," +
                    COLUMN_HEIGHT + "DOUBLE" +")" ;
            db.execSQL(CREATE_PROFILE_TABLE);
        }

        //Αναβάθμιση ΒΔ: εδώ τη διαγραφώ και τη ξαναδημιουργώ ίδια
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            onCreate(db);
        }

        //Μέθοδος για προσθήκη ενός προϊόντος στη ΒΔ
        public void addProduct(Profile profile) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, profile.getName());
            values.put(COLUMN_GENDER, profile.getGender());
            values.put(COLUMN_AGE, profile.getAge());
            values.put(COLUMN_WEIGHT, profile.getWeight());
            values.put(COLUMN_HEIGHT, profile.getHeight());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_PROFILE, null, values);
            db.close();
        }

    }
