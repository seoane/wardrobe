package psi14.udc.es.thewardrobe.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import psi14.udc.es.thewardrobe.ControlLayer.Chest;
import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

import static psi14.udc.es.thewardrobe.Utils.Constants.CHEST_TABLE;
import static psi14.udc.es.thewardrobe.Utils.Constants.CHEST_TYPE;
import static psi14.udc.es.thewardrobe.Utils.Constants.COLOR;
import static psi14.udc.es.thewardrobe.Utils.Constants.DESCRIPTION;
import static psi14.udc.es.thewardrobe.Utils.Constants.NAME;
import static psi14.udc.es.thewardrobe.Utils.Constants.SEASON;
import static psi14.udc.es.thewardrobe.Utils.Constants.URI;


public class ChestDataSource extends ClothDataSource {
    protected static ChestDataSource instance = null;

    public ChestDataSource(Context context) {
        super(context);
    }

    public static ChestDataSource getInstance(Context context) {
        if (instance == null) instance = new ChestDataSource(context);
        return (ChestDataSource) instance;
    }

    public void addChest(Chest chest) {
        super.open();
        Log.d("addChest", chest.toString());
        ContentValues values = new ContentValues();

/*      public static final String CHEST_ID = "_ID";
        public static final String NAME = "NAME";
        public static final String COLOR = "COLOR";
        public static final String SEASON = "SEASON";
        public static final String URI = "URI";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CLOTH_TYPE = "CLOTH_TYPE";*/

        values.put(NAME, chest.getName());
        values.put(COLOR, chest.getColor().toString());
        values.put(SEASON, chest.getSeason().toString());
        values.put(URI, chest.getPhotographyPath());
        values.put(DESCRIPTION, chest.getDescription());
        values.put(CHEST_TYPE, chest.getChestType().toString());

        // 3. insert
        database.insert(CHEST_TABLE, null, values);
        // 4. close
        database.close();
    }

    public Chest getChest(int id) {
        super.open();
        //SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {NAME, COLOR, URI, DESCRIPTION, CHEST_TYPE, SEASON};
        // 2. build query
        Cursor cursor =
                database.query(CHEST_TABLE, // a. table
                        columns, // b. column names
                        " _ID = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Chest chest = new Chest();
        chest.setName(cursor.getString(0));
        chest.setColor(Colors.valueOf(cursor.getString(1)));
        chest.setPhotographyPath(cursor.getString(2));
        chest.setDescription(cursor.getString(3));
        chest.setChestType(ChestType.valueOf(cursor.getString(4)));
        chest.setSeason(Season.valueOf(cursor.getString(5)));

        //log
        Log.d("getChest(" + id + ")", chest.toString());

        // 5. return book
        return chest;
    }

}
