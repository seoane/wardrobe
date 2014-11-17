package psi14.udc.es.thewardrobe.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

import static psi14.udc.es.thewardrobe.Utils.Constants.BODYPART;
import static psi14.udc.es.thewardrobe.Utils.Constants.CLOTH_TABLE;
import static psi14.udc.es.thewardrobe.Utils.Constants.COLOR;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;
import static psi14.udc.es.thewardrobe.Utils.Constants.DESCRIPTION;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.NAME;
import static psi14.udc.es.thewardrobe.Utils.Constants.SEASON;
import static psi14.udc.es.thewardrobe.Utils.Constants.TYPE;
import static psi14.udc.es.thewardrobe.Utils.Constants.URI;


public class ClothDataSource implements DataSourceInterface {
    protected static ClothDataSource instance = null;
    protected SQLiteDatabase database;
    TheWardrobeSQLiteHelper helper;

    public ClothDataSource(Context context) {
        helper = new TheWardrobeSQLiteHelper(context);
    }

    public static ClothDataSource getInstance(Context context) {
        if (instance == null)
            instance = new ClothDataSource(context);
        return instance;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public int addCloth(Cloth cloth) {
        open();
        if (DEBUG) Log.d("addChest", cloth.toString());
        ContentValues values = new ContentValues();

        values.put(NAME, cloth.getName());
        values.put(BODYPART,cloth.getBodyPart().toString());
        values.put(TYPE,cloth.getType());
        values.put(SEASON, cloth.getSeason().toString());
        values.put(COLOR, cloth.getColor().toString());
        values.put(DESCRIPTION, cloth.getDescription());
        values.put(URI, cloth.getUri());


        Long id = database.insert(CLOTH_TABLE, null, values);
        database.close();
        return id.intValue();
    }

    public Cloth getCloth(Integer id) {
        open();

        String[] columns = {NAME,BODYPART,TYPE,SEASON,COLOR, DESCRIPTION, URI};

        Cursor cursor =
                database.query(CLOTH_TABLE,
                        columns,
                        " _ID = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);
        if (cursor != null)
            cursor.moveToFirst();

        Cloth cloth = new Cloth(cursor.getString(0),
                BodyParts.valueOf(cursor.getString(1)),
                cursor.getString(2),
                Season.valueOf(cursor.getString(3)),
                Colors.valueOf(cursor.getString(4)),
                cursor.getString(5),
                cursor.getString(6));
        cloth.setId(id);

        return cloth;
    }

    public List<Cloth> getAllCloths() {
        open();
        List<Cloth> cloths = new ArrayList<Cloth>();

        String query = "SELECT  * FROM " + CLOTH_TABLE;


        Cursor cursor = database.rawQuery(query, null);

        Cloth cloth = null;
        if (cursor.moveToFirst()) {
            do {
                cloth = new Cloth(cursor.getInt(0),
                        cursor.getString(1),
                        BodyParts.valueOf(cursor.getString(2)),
                        cursor.getString(3),
                        Season.valueOf(cursor.getString(4)),
                        Colors.valueOf(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7));
                cloths.add(cloth);
            } while (cursor.moveToNext());
        }

        if (DEBUG) Log.d("getAllCloths()", cloths.toString());

        return cloths;
    }

    public List<Cloth> getAllChests() {
        open();
        List<Cloth> cloths = new ArrayList<Cloth>();

        String query = "SELECT * FROM " + CLOTH_TABLE + " WHERE " + BODYPART + " like " + "'" + BodyParts.CHEST + "'";


        Cursor cursor = database.rawQuery(query, null);

        Cloth cloth = null;
        if (cursor.moveToFirst()) {
            do {
                cloth = new Cloth(cursor.getInt(0),
                        cursor.getString(1),
                        BodyParts.valueOf(cursor.getString(2)),
                        cursor.getString(3),
                        Season.valueOf(cursor.getString(4)),
                        Colors.valueOf(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7));
                cloths.add(cloth);
            } while (cursor.moveToNext());
        }

        if (DEBUG) Log.d("getAllChests()", cloths.toString());

        return cloths;
    }

    public List<Cloth> getAllLegs() {
        open();
        List<Cloth> cloths = new ArrayList<Cloth>();

        String query = "SELECT * FROM " + CLOTH_TABLE + " WHERE " + BODYPART + " like " + "'" + BodyParts.LEGS + "'";


        Cursor cursor = database.rawQuery(query, null);

        Cloth cloth = null;
        if (cursor.moveToFirst()) {
            do {
                cloth = new Cloth(cursor.getInt(0),
                        cursor.getString(1),
                        BodyParts.valueOf(cursor.getString(2)),
                        cursor.getString(3),
                        Season.valueOf(cursor.getString(4)),
                        Colors.valueOf(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7));
                cloths.add(cloth);
            } while (cursor.moveToNext());
        }

        if (DEBUG) Log.d("getAllLegs()", cloths.toString());

        return cloths;
    }

    public List<Cloth> getAllFeets() {
        open();
        List<Cloth> cloths = new ArrayList<Cloth>();

        String query = "SELECT * FROM " + CLOTH_TABLE + " WHERE " + BODYPART + " like " + "'" + BodyParts.FEET + "'";


        Cursor cursor = database.rawQuery(query, null);

        Cloth cloth = null;
        if (cursor.moveToFirst()) {
            do {
                cloth = new Cloth(cursor.getInt(0),
                        cursor.getString(1),
                        BodyParts.valueOf(cursor.getString(2)),
                        cursor.getString(3),
                        Season.valueOf(cursor.getString(4)),
                        Colors.valueOf(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7));
                cloths.add(cloth);
            } while (cursor.moveToNext());
        }

        if (DEBUG) Log.d("getAllFeets()", cloths.toString());

        return cloths;
    }

    public boolean deleteCloth(Integer id) {
        open();
        return (database.delete(CLOTH_TABLE, ID + " =? ", new String[]{String.valueOf(id)}) == 1);
    }

    public boolean updateCloth(Cloth cloth) {
        open();
        Integer idNot = cloth.getId();
        ContentValues values = new ContentValues();
        values.put(NAME, cloth.getName());
        values.put(BODYPART,cloth.getBodyPart().toString());
        values.put(TYPE,cloth.getType());
        values.put(SEASON, cloth.getSeason().toString());
        values.put(COLOR, cloth.getColor().toString());
        values.put(DESCRIPTION, cloth.getDescription());
        values.put(URI, cloth.getUri());
        return (database.update(CLOTH_TABLE, values, ID + " = " + idNot, null) >= 1);
    }
}
