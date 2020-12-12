package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "logreg.db";
    public static final int DB_VERSION = 1;

    public static final String LOGREG_TABLE = "felhasznalo";

    public static final String COL_ID       = "id";
    public static final String COL_EMAIL    = "email";
    public static final String COL_FELHNEV  = "felhnev";
    public static final String COL_JELSZO    = "jelszo";
    public static final String COL_TELJESNEV    = "teljesnev";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql  = "CREATE TABLE IF NOT EXISTS "+LOGREG_TABLE+" (" +
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL+" VARCHAR(255) NOT NULL UNIQUE, " +
                COL_FELHNEV+" VARCHAR(255) NOT NULL UNIQUE, " +
                COL_JELSZO+" VARCHAR(255) NOT NULL, " +
                COL_TELJESNEV+" VARCHAR(255) NOT NULL " +
                ")";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + LOGREG_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }


    public boolean dataInsert(String email, String felhnev, String jelszo, String teljesnev) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_FELHNEV, felhnev);
        values.put(COL_JELSZO, jelszo);
        values.put(COL_TELJESNEV, teljesnev);
        return db.insert(LOGREG_TABLE, null, values) != -1;
    }


    public Cursor dataQuery(String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String WHERE = "jelszo = ?";
        String[] whereArgs = {password};

        return db.query(LOGREG_TABLE, new String[]{COL_TELJESNEV},
                WHERE, whereArgs, null, null, null);
    }


    public Cursor dataQueryAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(LOGREG_TABLE, new String[]{COL_ID, COL_EMAIL, COL_FELHNEV, COL_JELSZO, COL_TELJESNEV},
                null, null, null, null, null);
    }


    public boolean loginQuery(String felhnev, String jelszo) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor login = db.rawQuery("SELECT * FROM " + LOGREG_TABLE +
                " WHERE " + COL_FELHNEV + " = ? OR " + COL_EMAIL + " = ? AND " + COL_JELSZO + " = ?" , new String[]{felhnev, felhnev, jelszo});
        login.moveToFirst();
        return login.getCount() == 1;
    }


    public boolean usernameExists(String felhnev) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM felhasznalo WHERE felhnev = ?", new String[]{felhnev});
        return result.getCount() == 1;
    }


    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM felhasznalo WHERE email = ?", new String[]{email});
        return result.getCount() == 1;
    }
}
