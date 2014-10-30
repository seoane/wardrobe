package psi14.udc.es.thewardrobe.DataSources;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Sokun on 29/10/14.
 */
public abstract class ClothDataSource implements DataSourceInterface {
    protected static ClothDataSource instance = null;
    protected SQLiteDatabase database;
    TheWardrobeSQLiteHelper helper;

    public ClothDataSource(Context context) {
        helper = new TheWardrobeSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
}
