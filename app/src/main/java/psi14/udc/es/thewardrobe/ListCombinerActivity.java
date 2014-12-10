package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Combination;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;


public class ListCombinerActivity extends Activity {
    ListView combListView;
    List<Combination> combList;
    ClothDataSource clothDataSource;
    CombinerCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_combiner);
        combListView = (ListView) findViewById(R.id.lv_combiner);
        clothDataSource = ClothDataSource.getInstance(this);
        updateList();
    }

    private void updateList() {

        combList = clothDataSource.getAllCombinations();

        if (combList.size() > 0) {
            Resources res = getResources();
            adapter = new CombinerCustomAdapter(this, combList, res);
            combListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_combiner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
