package dev.premananda.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Dumy_db";
    private static final String TABLE_USERS = "Users";

    private static final String TABLE_USERS_ID = "userId";
    private static final String TABLE_USERS_USERNAME = "username";
    private static final String TABLE_USERS_PASSWORD = "password";
    private static final String TABLE_USERS_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
                + TABLE_USERS_ID + " INTEGER PRIMARY KEY,"
                + TABLE_USERS_USERNAME + " TEXT,"
                + TABLE_USERS_PASSWORD + " TEXT,"
                + TABLE_USERS_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void save(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USERNAME, user.getUsername());
        values.put(TABLE_USERS_PASSWORD, user.getPassword());
        values.put(TABLE_USERS_NAME, user.getName());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User findById(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_USERS, new String[]{TABLE_USERS_ID, TABLE_USERS_USERNAME, TABLE_USERS_PASSWORD, TABLE_USERS_NAME},
                    TABLE_USERS_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor == null || cursor.getCount() == 0) {
                db.close();
                return null;
            }

            if (cursor != null) {
                cursor.moveToFirst();
            }

            db.close();
            return new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        } catch (Error e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_USERS, new String[]{TABLE_USERS_ID, TABLE_USERS_USERNAME, TABLE_USERS_PASSWORD, TABLE_USERS_NAME},
                    TABLE_USERS_USERNAME + "=?", new String[]{username}, null, null, null);

            if (cursor == null || cursor.getCount() == 0) {
                db.close();
                return null;
            }

            if (cursor != null){
                cursor.moveToFirst();
            }

            db.close();
            return new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        } catch (Error e) {
            return null;
        }
    }

    public void update(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_NAME, user.getName());
        values.put(TABLE_USERS_USERNAME, user.getUsername());
        values.put(TABLE_USERS_PASSWORD, user.getPassword());

        db.update(TABLE_USERS, values, TABLE_USERS_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public List<User> findAllUsers() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_USERS, null, null,null, null, null, null);

            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }

            List<User> userList = new ArrayList<User>();
            if(cursor.moveToFirst()){
                do{
                    User user = new User(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3));
                    userList.add(user);
                }while(cursor.moveToNext());
            }

            db.close();
            return userList;
        } catch (Error e) {
            return null;
        }
    }

    public void deleteUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_USERS, TABLE_USERS_ID + " =?", new String[] {String.valueOf(id)});
        db.close();
    }
}
