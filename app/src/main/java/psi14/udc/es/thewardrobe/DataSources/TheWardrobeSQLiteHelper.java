package psi14.udc.es.thewardrobe.DataSources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

import static psi14.udc.es.thewardrobe.Utils.Constants.*;

public class TheWardrobeSQLiteHelper extends SQLiteOpenHelper {
    private final static String LOG_TAG = "TheWardrobeSQLiteHelper";
    private static String DB_PATH = "";
    private static String DB_NAME = psi14.udc.es.thewardrobe.Utils.Constants.DATABASE_NAME;
    private static String DB_FILE = DB_NAME + ".db";
    Context context;

    public TheWardrobeSQLiteHelper(Context context) {
        super(context, DB_FILE, null, 1);
        DB_PATH = context.getFilesDir().getPath() + "/databases/";
        this.context = context;
    }

    public TheWardrobeSQLiteHelper(Context context, String name,
                                   CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("" +
                "CREATE TABLE " + CLOTH_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                BODYPART + "TEXT NOT NULL" +
                SEASON + " TEXT NOT NULL," +
                COLOR + " TEXT NOT NULL," +
                URI + " TEXT NOT NULL," +
                DESCRIPTION + " TEXT NOT NULL," +
                TYPE + " TEXT NOT NULL" +
                ");");

    }

    private boolean databaseExists() {
        File file = new File(DB_PATH + DB_FILE);
        return file.exists();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // Drop older cloths table if existed
        db.execSQL("DROP TABLE IF EXISTS "+  CLOTH_TABLE);

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
