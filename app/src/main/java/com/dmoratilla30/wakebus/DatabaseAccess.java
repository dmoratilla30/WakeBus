package com.dmoratilla30.wakebus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    public SQLiteDatabase database;
    private static DatabaseAccess instance;

    /** Private constructor to avoid object creation from outside classes.
     * @param context */
    private DatabaseAccess(Context context) {
        this.openHelper = new com.dmoratilla30.wakebus.DatabaseOpenHelper(context);
    }

    /** Return a singleton instance of DatabaseAccess.
     * @param context the Context
     * @return the instance of DabaseAccess */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) instance = new DatabaseAccess(context);
        return instance;
    }

    /** Open the database connection. */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /** Close the database connection. */
    public void close() {
        if (database != null) this.database.close();
    }
}