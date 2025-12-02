package com.example.hikingmanagement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hikingmanagement.model.Hike;
import com.example.hikingmanagement.model.Observation;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hiking.db";
    private static final int DATABASE_VERSION = 4;

    // ------------------- Hike Table -------------------
    private static final String TABLE_HIKE = "hikes";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_LOCATION = "location";
    private static final String COL_DATE = "date";
    private static final String COL_LENGTH = "length";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_DIFFICULTY = "difficulty";
    private static final String COL_WEATHER = "weather";
    private static final String COL_PARKING = "parking";

    // ------------------- Observation Table -------------------
    private static final String TABLE_OBSERVATIONS = "observations";
    private static final String COL_OBS_ID = "id";
    private static final String COL_OBS_HIKE_ID = "hike_id";
    private static final String COL_OBS_OBSERVATION = "observation";
    private static final String COL_OBS_TIME = "time";
    private static final String COL_OBS_COMMENTS = "comments";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create hike table
        String createHikeTable = "CREATE TABLE " + TABLE_HIKE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_LOCATION + " TEXT," +
                COL_DATE + " TEXT," +
                COL_LENGTH + " REAL," +
                COL_DESCRIPTION + " TEXT," +
                COL_DIFFICULTY + " TEXT," +
                COL_WEATHER + " TEXT," +
                COL_PARKING + " TEXT)";
        db.execSQL(createHikeTable);

        // Create observations table
        String createObservationTable = "CREATE TABLE " + TABLE_OBSERVATIONS + " (" +
                COL_OBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_OBS_HIKE_ID + " INTEGER, " +
                COL_OBS_OBSERVATION + " TEXT NOT NULL, " +
                COL_OBS_TIME + " TEXT NOT NULL, " +
                COL_OBS_COMMENTS + " TEXT, " +
                "FOREIGN KEY(" + COL_OBS_HIKE_ID + ") REFERENCES " + TABLE_HIKE + "(" + COL_ID + ") ON DELETE CASCADE)";
        db.execSQL(createObservationTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKE);
        onCreate(db);
    }

    // ------------------- Hike CRUD -------------------
    public long addHike(Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, hike.getName());
        cv.put(COL_LOCATION, hike.getLocation());
        cv.put(COL_DATE, hike.getDate());
        cv.put(COL_LENGTH, hike.getLength());
        cv.put(COL_DESCRIPTION, hike.getDescription());
        cv.put(COL_DIFFICULTY, hike.getDifficulty());
        cv.put(COL_WEATHER, hike.getWeather());
        cv.put(COL_PARKING, hike.getParking());
        long id = db.insert(TABLE_HIKE, null, cv);
        db.close();
        return id;
    }

    public int updateHike(Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, hike.getName());
        cv.put(COL_LOCATION, hike.getLocation());
        cv.put(COL_DATE, hike.getDate());
        cv.put(COL_LENGTH, hike.getLength());
        cv.put(COL_DESCRIPTION, hike.getDescription());
        cv.put(COL_DIFFICULTY, hike.getDifficulty());
        cv.put(COL_WEATHER, hike.getWeather());
        cv.put(COL_PARKING, hike.getParking());
        int rows = db.update(TABLE_HIKE, cv, COL_ID + "=?", new String[]{String.valueOf(hike.getId())});
        db.close();
        return rows;
    }

    public boolean deleteHike(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_HIKE, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public ArrayList<Hike> getAllHikes() {
        ArrayList<Hike> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HIKE, null, null, null, null, null, COL_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Hike h = new Hike();
                h.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                h.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                h.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)));
                h.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                h.setLength(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LENGTH)));
                h.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
                h.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY)));
                h.setWeather(cursor.getString(cursor.getColumnIndexOrThrow(COL_WEATHER)));
                h.setParking(cursor.getString(cursor.getColumnIndexOrThrow(COL_PARKING)));
                list.add(h);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Hike> searchHikes(String keyword) {
        ArrayList<Hike> hikeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HIKE +
                " WHERE " + COL_NAME + " LIKE ? OR " + COL_LOCATION + " LIKE ? OR " + COL_DIFFICULTY + " LIKE ?";
        String likeKeyword = "%" + keyword + "%";

        Cursor cursor = db.rawQuery(query, new String[]{likeKeyword, likeKeyword, likeKeyword});
        if (cursor.moveToFirst()) {
            do {
                Hike hike = new Hike();
                hike.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                hike.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                hike.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)));
                hike.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                hike.setLength(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LENGTH)));
                hike.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
                hike.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY)));
                hike.setWeather(cursor.getString(cursor.getColumnIndexOrThrow(COL_WEATHER)));
                hike.setParking(cursor.getString(cursor.getColumnIndexOrThrow(COL_PARKING)));
                hikeList.add(hike);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hikeList;
    }

    // ------------------- Observation CRUD -------------------
    public long addObservation(Observation obs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_OBS_HIKE_ID, obs.getHikeId());
        values.put(COL_OBS_OBSERVATION, obs.getObservation());
        values.put(COL_OBS_TIME, obs.getTime());
        values.put(COL_OBS_COMMENTS, obs.getComments());
        long result = db.insert(TABLE_OBSERVATIONS, null, values);
        db.close();
        return result;
    }

    public ArrayList<Observation> getObservationsByHike(long hikeId) {
        ArrayList<Observation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATIONS +
                " WHERE " + COL_OBS_HIKE_ID + "=?", new String[]{String.valueOf(hikeId)});
        if (cursor.moveToFirst()) {
            do {
                Observation obs = new Observation();
                obs.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_OBS_ID)));
                obs.setHikeId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_OBS_HIKE_ID)));
                obs.setObservation(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_OBSERVATION)));
                obs.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_TIME)));
                obs.setComments(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_COMMENTS)));
                list.add(obs);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int updateObservation(Observation obs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_OBS_OBSERVATION, obs.getObservation());
        values.put(COL_OBS_TIME, obs.getTime());
        values.put(COL_OBS_COMMENTS, obs.getComments());
        int rows = db.update(TABLE_OBSERVATIONS, values, COL_OBS_ID + "=?",
                new String[]{String.valueOf(obs.getId())});
        db.close();
        return rows;
    }

    public Observation getObservation(long observationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Observation observation = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATIONS +
                " WHERE " + COL_OBS_ID + "=?", new String[]{String.valueOf(observationId)});

        if (cursor.moveToFirst()) {
            observation = new Observation();
            observation.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_OBS_ID)));
            observation.setHikeId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_OBS_HIKE_ID)));
            observation.setObservation(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_OBSERVATION)));
            observation.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_TIME)));
            observation.setComments(cursor.getString(cursor.getColumnIndexOrThrow(COL_OBS_COMMENTS)));
        }

        cursor.close();
        db.close();
        return observation;
    }

    public boolean deleteObservation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_OBSERVATIONS, COL_OBS_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public ArrayList<Observation> getObservationsByHikeId(long hikeId) {
        ArrayList<Observation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OBSERVATIONS + " WHERE " + COL_OBS_HIKE_ID + "=?", new String[]{String.valueOf(hikeId)});


        if (cursor.moveToFirst()) {
            do {
                Observation obs = new Observation();
                obs.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                obs.setHikeId(cursor.getLong(cursor.getColumnIndexOrThrow("hike_id")));
                obs.setObservation(cursor.getString(cursor.getColumnIndexOrThrow("observation")));
                obs.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                obs.setComments(cursor.getString(cursor.getColumnIndexOrThrow("comments")));
                list.add(obs);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


}
