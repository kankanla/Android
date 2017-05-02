package com.kankanla.m0417b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by kankanla on 2017/04/21.
 */

public class FileSQL {
    private Context context;
    private Data_db data_db;
    private static final String db_name = "m0417b.db";
    private static final int db_version = 1;

    public FileSQL(Context context) {
        this.context = context;
        data_db = new Data_db(context);
        data_db.getReadableDatabase();
        data_db.close();
    }

    public void getUriinfo(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                System.out.println("-----------cursor-----getUriinfo---------1-----------");
                System.out.println("uri.toString");
                System.out.println(uri.toString());
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    System.out.println("getColumnName    " + cursor.getColumnName(i));
                    System.out.println("getString    " + cursor.getString(i));
                }
                System.out.println("----------cursor-------getUriinfo--------2----------");
            }
        }
    }

    public boolean add_info_sound(Uri uri) {
        System.out.println("--------------add_info_sound-------------------------------------1---------------");
        String table_name = "info_sound";
        ContentValues contentValues = new ContentValues();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        SQLiteDatabase sqLiteDatabase = data_db.getWritableDatabase();
        long cont = 0;
        if (cursor != null) {
            contentValues.put("document_uri", uri.toString());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String key = cursor.getColumnName(i);
                    String val = cursor.getString(i);
                    contentValues.put(key, val);
                }
                cont = sqLiteDatabase.insertWithOnConflict(table_name, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
        sqLiteDatabase.close();
        System.out.println("--------------add_info_sound-------------------------------------2---------------");

        if (cont > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor info_sound_all_quiry() {
        String table_name = "info_sound";
        SQLiteDatabase sqLiteDatabase = data_db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(table_name, new String[]{"*"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                System.out.println("--------------------info_sound_quiry---------------1----------");
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String key = cursor.getColumnName(i);
                    String val = cursor.getString(i);
                }
                System.out.println("--------------------info_sound_quiry---------------2----------");
            }
        }
        sqLiteDatabase.close();
        return cursor;
    }

    private class Data_db extends SQLiteOpenHelper {

        public Data_db(Context context) {
            super(context, db_name, null, db_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql_cmd1 = "create table info_sound (" +
                    "_id Integer primary key autoincrement," +
                    "document_uri text UNIQUE, " +
                    "document_id text," +
                    "mime_type text," +
                    "_display_name text," +
                    "last_modified integer," +
                    "flags text," +
                    "_size integer," +
                    "icon text," +
                    "play_count integer)";

            String sql_cmd2 = "create table info_list (" +
                    "_id integer primary key autoincrement," +
                    "list_name text," +
                    "last_modified integer," +
                    "flags text," +
                    "_size integer," +
                    "play_count integer)";

            String sql_cmd3 = "create table link_list (" +
                    "_id integer primary key autoincrement," +
                    "info_list_id integer," +
                    "info_sound_id integer," +
                    "flags text)";

            db.execSQL(sql_cmd1);
            db.execSQL(sql_cmd2);
            db.execSQL(sql_cmd3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
