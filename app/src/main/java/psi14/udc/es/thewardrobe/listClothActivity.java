package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import static psi14.udc.es.thewardrobe.Utils.Constants.*;

public class listClothActivity extends Activity {

    ListView lv;
    ChestDataSource chestDataSource;
    LegsDataSource legsDataSource;
    FeetDataSource feetDataSource;
    ArrayList listCloth;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cloth);
        lv = (ListView) findViewById(R.id.lv_main);

        // Obtain dataSources
        chestDataSource = ChestDataSource.getInstance(this);
        legsDataSource = LegsDataSource.getInstance(this);
        feetDataSource = FeetDataSource.getInstance(this);

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
            listCloth = chestDataSource.getAllChests();
            listCloth.addAll(legsDataSource.getAllLegs());
            listCloth.addAll(feetDataSource.getAllFeet());
            updateList(listCloth);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateList(ArrayList<Cloth> list){
        Resources res =getResources();
        adapter=new CustomAdapter( this, list,res );
        lv.setAdapter( adapter );
    }


    public void onItemClick(int mPosition)
    {
        Cloth cloth = ( Cloth ) listCloth.get(mPosition);

        Toast.makeText(this,cloth.getName(),Toast.LENGTH_LONG).show();

    }
}

