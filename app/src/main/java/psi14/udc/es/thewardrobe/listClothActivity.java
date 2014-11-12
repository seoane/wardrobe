package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Chest;
import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ChestDataSource;
import psi14.udc.es.thewardrobe.DataSources.FeetDataSource;
import psi14.udc.es.thewardrobe.DataSources.LegsDataSource;
import psi14.udc.es.thewardrobe.Utils.Constants;

import static psi14.udc.es.thewardrobe.Utils.Constants.*;

public class listClothActivity extends Activity {

    public static final String TAG = "listClothActivity";

    ListView lv;
    ChestDataSource chestDataSource;
    LegsDataSource legsDataSource;
    FeetDataSource feetDataSource;
    ArrayList listCloth;
    CustomAdapter adapter;
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cloth);
        lv = (ListView) findViewById(R.id.lv_main);

        // Obtain dataSources
        chestDataSource = ChestDataSource.getInstance(this);
        legsDataSource = LegsDataSource.getInstance(this);
        feetDataSource = FeetDataSource.getInstance(this);

        registerForContextMenu(lv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_cloth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this,macClothActivity.class));
            return true;
        }else if (id == R.id.menu_update){
            updateList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.context_edit:
                Log.d(TAG, "Context Edit");
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Log.d(TAG,"info: " + info.id);
                /*Intent intent = new Intent(this, listClothActivity.class);
                intent.putExtra(Constants.ID, (int) info.id);
                startActivity(intent);*/
                return true;
            case R.id.context_delete:
                Log.d(TAG, "Context Delete");
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                //Delete de la BD correspondiente...
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateList(){

        listCloth = chestDataSource.getAllChests();
        listCloth.addAll(legsDataSource.getAllLegs());
        listCloth.addAll(feetDataSource.getAllFeet());

        Resources res =getResources();
        adapter=new CustomAdapter( this, listCloth,res );
        lv.setAdapter( adapter );
    }

}

