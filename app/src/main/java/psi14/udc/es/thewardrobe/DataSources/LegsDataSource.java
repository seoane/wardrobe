package psi14.udc.es.thewardrobe.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import psi14.udc.es.thewardrobe.ControlLayer.Legs;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;

import static psi14.udc.es.thewardrobe.Utils.Constants.COLOR;
import static psi14.udc.es.thewardrobe.Utils.Constants.DESCRIPTION;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.LEGS_TABLE;
import static psi14.udc.es.thewardrobe.Utils.Constants.LEGS_TYPE;
import static psi14.udc.es.thewardrobe.Utils.Constants.NAME;
import static psi14.udc.es.thewardrobe.Utils.Constants.SEASON;
import static psi14.udc.es.thewardrobe.Utils.Constants.URI;


public class LegsDataSource extends ClothDataSource {

    protected static LegsDataSource instance = null;

    public LegsDataSource(Context context) {
        super(context);
    }

    public static LegsDataSource getInstance(Context context) {
        if (instance == null) instance = new LegsDataSource(context);
        return (LegsDataSource) instance;
    }

    public void addLegs(Legs legs) {
        super.open();
        Log.d("addLegs", legs.toString());
        ContentValues values = new ContentValues();

/*      public static final String LEGS_ID = "_ID";
        public static final String NAME = "NAME";
        public static final String COLOR = "COLOR";
        public static final String SEASON = "SEASON";
        public static final String URI = "URI";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CLOTH_TYPE = "CLOTH_TYPE";*/

        values.put(NAME, legs.getName());
        values.put(COLOR, legs.getColor().toString());
        values.put(SEASON, legs.getSeason().toString());
        values.put(URI, legs.getPhotographyPath());
        values.put(DESCRIPTION, legs.getDescription());
        values.put(LEGS_TYPE, legs.getLegsType().toString());

        // 3. insert
        database.insert(LEGS_TABLE, null, values);
        // 4. close
        database.close();
    }

    public Legs getLegs(int id) {
        super.open();
        //SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {NAME, COLOR, URI, DESCRIPTION, LEGS_TYPE, SEASON};
        // 2. build query
        Cursor cursor =
                database.query(LEGS_TABLE, // a. table
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

        // 4. build legs object
        Legs legs = new Legs();
        legs.setName(cursor.getString(0));
        legs.setColor(Colors.valueOf(cursor.getString(1)));
        legs.setPhotographyPath(cursor.getString(2));
        legs.setDescription(cursor.getString(3));
        legs.setLegsType(LegsType.valueOf(cursor.getString(4)));
        legs.setSeason(Season.valueOf(cursor.getString(5)));

        //log
        Log.d("getLegs(" + id + ")", legs.toString());

        // 5. return legs
        return legs;
    }

    public boolean deleteFeet(int id) {
        super.open();
        return (database.delete(LEGS_TABLE, ID + " =? ", new String[]{String.valueOf(id)}) == 0);
    }
}
