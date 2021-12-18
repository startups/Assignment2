package com.shop.onlineapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class SqliteDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "onlineshop.com";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String PRIMARY_ID = "id";
    public static final String KEY_CUSTOMER_EMAIL = "email";
    public static final String KEY_CUSTOMER_NAME = "username";
    public static final String KEY_CUSTOMER_PASSWORD = "password";

    public static final String SQL_TABLE_CUSTOMERS = " CREATE TABLE " + TABLE_CUSTOMER
            + " ( "
            + PRIMARY_ID + " INTEGER PRIMARY KEY, "
            + KEY_CUSTOMER_NAME + " TEXT, "
            + KEY_CUSTOMER_EMAIL + " TEXT, "
            + KEY_CUSTOMER_PASSWORD + " TEXT"
            + " ) ";

   public SqliteDBHelper(Context activityContext) {
        super(activityContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int a, int b) {
        sqlDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        sqlDB.execSQL(SQL_TABLE_CUSTOMERS);
    }

    public UserObject authenticateUser(UserObject userObject) {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor sqlCursor = sqlDB.query(TABLE_CUSTOMER,
                new String[]{PRIMARY_ID, KEY_CUSTOMER_NAME, KEY_CUSTOMER_EMAIL, KEY_CUSTOMER_PASSWORD},
                KEY_CUSTOMER_EMAIL + "=?",
                new String[]{userObject.emailId},
                null, null, null);
        if (sqlCursor != null && sqlCursor.moveToFirst()) {
            UserObject retUser = new UserObject(sqlCursor.getString(0), sqlCursor.getString(1), sqlCursor.getString(2), sqlCursor.getString(3));
            if (userObject.password.equalsIgnoreCase(retUser.password)) {
                return retUser;
            }
        }
        return null;
    }

    public void createNewUser(UserObject userObject) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CUSTOMER_NAME, userObject.name);
        contentValues.put(KEY_CUSTOMER_EMAIL, userObject.emailId);
        contentValues.put(KEY_CUSTOMER_PASSWORD, userObject.password);
        sqlDB.insert(TABLE_CUSTOMER, null, contentValues);
    }

    public boolean isAccountExists(String emailId) {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor sqlCursor = sqlDB.query(TABLE_CUSTOMER,
                new String[]{PRIMARY_ID, KEY_CUSTOMER_NAME, KEY_CUSTOMER_EMAIL, KEY_CUSTOMER_PASSWORD},
                KEY_CUSTOMER_EMAIL + "=?",
                new String[]{emailId},
                null, null, null);
        if (sqlCursor != null && sqlCursor.moveToFirst()) {
            return true;
        }
        return false;
    }
}
