package com.example.qwerk.safety;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;



/**
 * Created by qWERK on 11/21/2015.
 */
public class database extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.qwerk.safety.databs";
    static final String URL = "content://" + PROVIDER_NAME + "/dbase";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final String NUMBER = "phone";

    private static HashMap<String, String> DBASE_PROJECTION_MAP;

    static final int DBASE = 1;
    static final int DBASE_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "dbase", DBASE);
        uriMatcher.addURI(PROVIDER_NAME, "dbase/#", DBASE_ID);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "womensafety";
    static final String DBASE_TABLE_NAME = "dbase";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =" CREATE TABLE " + DBASE_TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +" name TEXT NOT NULL, phone TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */

    private static class DatabaseHelper extends SQLiteOpenHelper {DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(CREATE_DB_TABLE);
            System.out.print("database created in oncreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  DBASE_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        if(db==null){
            System.out.println("not created database");
            return false;
        }else {
            System.out.println("created database");
            return true;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         *
         */

        long rowID = db.insert(	DBASE_TABLE_NAME, null, values);
        System.out.print("inserted");

        /**
         * If record is added successfully
         */


        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }


            throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBASE_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case DBASE:
                qb.setProjectionMap(DBASE_PROJECTION_MAP);
                break;

            case DBASE_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = NAME;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case DBASE:
                count = db.delete(DBASE_TABLE_NAME, selection, selectionArgs);
                break;

            case DBASE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( DBASE_TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case DBASE:
                count = db.update(DBASE_TABLE_NAME, values, selection, selectionArgs);
                break;

            case DBASE_ID:
                count = db.update(DBASE_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case DBASE:
                return "vnd.android.cursor.dir/vnd.example.dbase";

            /**
             * Get a particular student
             */
            case DBASE_ID:
                return "vnd.android.cursor.item/vnd.example.dbase";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}






