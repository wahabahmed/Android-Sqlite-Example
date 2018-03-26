package sqlite.automaticamusements.com.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wahaba on 20/04/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "users.db";
    private final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "aid";
    public static final String COLUMN_NAME = "name";

    SQLiteDatabase myDB;


    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME +
                " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT NOT NULL " +
                ")";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB() {
        myDB = getReadableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insert(int id, String name) {
        ContentValues values = new ContentValues();
        if (id != -1) {
            values.put(COLUMN_ID, id);
        }
        values.put(COLUMN_NAME, name);

        return myDB.insert(TABLE_NAME, null, values);
    }


    public long update(int id, String name) {
        ContentValues values = new ContentValues();
        if (id != -1) {
            values.put(COLUMN_ID, id);
        }
        values.put(COLUMN_NAME, name);

        String where = COLUMN_ID + " = " + id;
        return myDB.update(TABLE_NAME, values, where, null);
    }

    public long delete(int id) {
        String where = COLUMN_ID + " = " + id;
        return myDB.delete(TABLE_NAME, where, null);
    }

    public Cursor getAllRecords() {
        return myDB.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
