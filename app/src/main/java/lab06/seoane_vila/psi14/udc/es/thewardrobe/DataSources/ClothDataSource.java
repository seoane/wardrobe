package lab06.seoane_vila.psi14.udc.es.thewardrobe.DataSources;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Sokun on 29/10/14.
 */
public class ClothDataSource implements DataSourceInterface {
    protected static ClothDataSource instance = null;
    protected SQLiteDatabase database;
    TheWardrobeSQLiteHelper helper;

    public ClothDataSource(Context context) {
        helper = new TheWardrobeSQLiteHelper(context);
        helper.createDatabase();
    }

    public static ClothDataSource getInstance(Context context) {
        if (instance == null) instance = new ClothDataSource(context);
        return (ClothDataSource) instance;
    }

    public void open() throws SQLException {
        database = helper.openDataBase();
    }

    public void close() {
        helper.close();
    }
}
