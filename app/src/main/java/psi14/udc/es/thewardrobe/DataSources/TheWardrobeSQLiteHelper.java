package psi14.udc.es.thewardrobe.DataSources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import static psi14.udc.es.thewardrobe.Utils.Constants.*;

public class TheWardrobeSQLiteHelper extends SQLiteOpenHelper {
    private final static String LOG_TAG = "TheWardrobeSQLiteHelper";
    private static String DB_PATH = "";
    private static String DB_NAME = psi14.udc.es.thewardrobe.Utils.Constants.DATABASE_NAME;
    private static String DB_FILE = DB_NAME + ".db";
    Context context;

    public TheWardrobeSQLiteHelper(Context context) {
        super(context, DB_FILE, null, 2);
        DB_PATH = context.getFilesDir().getPath() + "/databases/";
        this.context = context;
    }

    public TheWardrobeSQLiteHelper(Context context, String name,
                                   CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG,"Creating Database");
        db.execSQL("" +
                "CREATE TABLE " + CLOTH_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                BODYPART + " TEXT NOT NULL," +
                TYPE + " TEXT NOT NULL," +
                SEASON + " TEXT NOT NULL," +
                COLOR + " TEXT NOT NULL," +
                DESCRIPTION + " TEXT NOT NULL," +
                URI + " TEXT NOT NULL," +
                FREQUENCY + " INTEGER" +
                ");");
        db.execSQL(
                "CREATE TABLE " + CLOTH_RELATION_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COMB_NAME + " TEXT NOT NULL," +
                CHEST_ID +  " INTEGER NOT NULL," +
                LEGS_ID + " INTEGER NOT NULL," +
                FEET_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + CHEST_ID + ") REFERENCES " +  CLOTH_TABLE + "("+ ID + ")," +
                "FOREIGN KEY(" + FEET_ID + ") REFERENCES " +  CLOTH_TABLE + "("+ ID + ")," +
                "FOREIGN KEY(" + LEGS_ID + ") REFERENCES " +  CLOTH_TABLE + "("+ ID + ")" +
                ");");

    }

    private boolean databaseExists() {
        File file = new File(DB_PATH + DB_FILE);
        return file.exists();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        if (DEBUG) Log.d(LOG_TAG,"onUpgrade: Removing dbs if already exits");
        // Drop older  table if existed
        db.execSQL("DROP TABLE IF EXISTS "+  CLOTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +  CLOTH_RELATION_TABLE);

        // create fresh cloths table
        this.onCreate(db);
    }

    /**
     * Open the database defined with DB_PATH and DB_NAME
     *
     * @return an opened database
     * @throws android.database.SQLException
     */

}
