package lab06.seoane_vila.psi14.udc.es.thewardrobe.DataSources;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TheWardrobeSQLiteHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = lab06.seoane_vila.psi14.udc.es.thewardrobe.Utils.Constants.DATABASE_NAME;
    Context context;

    public TheWardrobeSQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getFilesDir().getPath() + "/databases/";
        this.context = context;
    }

    public TheWardrobeSQLiteHelper(Context context, String name,
                                   CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * If the database is not already created - create a new database on the device.
     */
    public void createDatabase() {
        if (!databaseExists()) {
            File f = new File(DB_PATH);
            if (!f.exists()) {
                f.mkdir();
            }

            this.getReadableDatabase().close();

            try {
                copyDatabase();
                Log.v("DATABASE", "Database Copied");
            } catch (IOException exception) {
                throw new Error("ErrorCopyingDatabase");
            }
        } else {
            Log.v("DATABASE", "Database Already Exists");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    private boolean databaseExists() {
        File file = new File(DB_PATH + DB_NAME);
        return file.exists();
    }

    /**
     * Copy database to DB_PATH+DB_NAME
     *
     * @throws java.io.IOException
     */
    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DB_NAME);
        OutputStream output = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }

    /**
     * Open the database defined with DB_PATH and DB_NAME
     *
     * @return an opened database
     * @throws android.database.SQLException
     */
    public SQLiteDatabase openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
