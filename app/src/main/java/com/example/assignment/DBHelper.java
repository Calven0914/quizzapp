package com.example.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, marks INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("marks", 0); // Initialize marks to 0
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username =?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username =? and password =?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;


    }

    public Cursor getdata(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        // Construct the SQL query to retrieve username and password for the specified user
        Cursor cursor = MyDB.rawQuery("SELECT username, password FROM users WHERE username =?", new String[]{username});
        return cursor;
    }

    public boolean updateUserData(String oldUsername, String newUsername, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", newUsername);
        contentValues.put("password", newPassword);
        int rowsAffected = db.update("users", contentValues, "username =?", new String[]{oldUsername});
        return rowsAffected > 0;
    }

    public void updateUserScore(String username, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE users SET marks =? WHERE username =?";
        String[] args = new String[] { String.valueOf(score), username };
        db.execSQL(updateQuery, args);
    }

    public Cursor readAllData() {
        String query = "SELECT _id, username, marks FROM users ORDER BY marks DESC LIMIT 11";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }



}