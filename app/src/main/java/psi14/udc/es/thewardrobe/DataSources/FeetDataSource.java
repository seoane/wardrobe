package psi14.udc.es.thewardrobe.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import psi14.udc.es.thewardrobe.ControlLayer.Feet;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.FeetType;
import psi14.udc.es.thewardrobe.Utils.Season;

import static psi14.udc.es.thewardrobe.Utils.Constants.COLOR;
import static psi14.udc.es.thewardrobe.Utils.Constants.DESCRIPTION;
import static psi14.udc.es.thewardrobe.Utils.Constants.FEET_TABLE;
import static psi14.udc.es.thewardrobe.Utils.Constants.FEET_TYPE;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.NAME;
import static psi14.udc.es.thewardrobe.Utils.Constants.SEASON;
import static psi14.udc.es.thewardrobe.Utils.Constants.URI;


public class FeetDataSource extends ClothDataSource{

    protected static FeetDataSource instance = null;

    public FeetDataSource(Context context) {
        super(context);
    }

    public static FeetDataSource getInstance(Context context) {
        if (instance == null) instance = new FeetDataSource(context);
        return (FeetDataSource) instance;
    }

    public int addFeet(Feet feet) {
        super.open();
        Log.d("addFeet", feet.toString());
        ContentValues values = new ContentValues();

/*      public static final String FEET_ID = "_ID";
        public static final String NAME = "NAME";
        public static final String SEASON = "SEASON";
        public static final String COLOR = "COLOR";
        public static final String URI = "URI";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CLOTH_TYPE = "CLOTH_TYPE";*/

        values.put(NAME, feet.getName());
        values.put(SEASON, feet.getSeason().toString());
        values.put(COLOR, feet.getColor().toString());
        values.put(URI, feet.getPhotographyPath());
        values.put(DESCRIPTION, feet.getDescription());
        values.put(FEET_TYPE, feet.getFeetType().toString());

        // 3. insert
        Long id = database.insert(FEET_TABLE, null, values);
        // 4. close
        database.close();
        return id.intValue();
    }

    public Feet getFeet(int id) {
        super.open();
        //SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {NAME, SEASON,COLOR, URI, DESCRIPTION, FEET_TYPE};
        // 2. build query
        Cursor cursor =
                database.query(FEET_TABLE, // a. table
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

        // 4. build feet object
        Feet feet = new Feet();
        feet.setName(cursor.getString(0));
        feet.setSeason(Season.valueOf(cursor.getString(1)));
        feet.setColor(Colors.valueOf(cursor.getString(2)));
        feet.setPhotographyPath(cursor.getString(3));
        feet.setDescription(cursor.getString(4));
        feet.setFeetType(FeetType.valueOf(cursor.getString(5)));

        feet.setId(id);
        //log
        Log.d("getFeet(" + id + ")", feet.toString());

        // 5. return feet
        return feet;
    }

    public ArrayList<Feet> getAllFeet() {
        super.open();
        ArrayList<Feet> feets = new ArrayList<Feet>();

        String query = "SELECT  * FROM " + FEET_TABLE;


        Cursor cursor = database.rawQuery(query, null);

        Feet feet = null;
        if (cursor.moveToFirst()) {
            do {
                feet = new Feet(cursor.getInt(0),cursor.getString(1),Season.valueOf(cursor.getString(2)),
                        Colors.valueOf(cursor.getString(3)),cursor.getString(4),
                        cursor.getString(5),FeetType.valueOf(cursor.getString(6)));
                feets.add(feet);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFeet()", feets.toString());

        return feets;
    }

    public boolean deleteFeet(int id) {
        super.open();
        return (database.delete(FEET_TABLE, ID + " =? ", new String[]{String.valueOf(id)}) == 1);
    }

    public boolean updateFeet(Feet feet) {
        super.open();
        long idNot = feet.getId();
        ContentValues cv = new ContentValues();
        cv.put(NAME, feet.getName());
        cv.put(SEASON, feet.getSeason().toString());
        cv.put(COLOR, feet.getColor().toString());
        cv.put(URI, feet.getPhotographyPath());
        cv.put(DESCRIPTION, feet.getDescription());
        cv.put(FEET_TYPE, feet.getFeetType().toString());
        return (database.update(FEET_TABLE, cv, ID + " = " + idNot, null) >= 1);
    }
}
