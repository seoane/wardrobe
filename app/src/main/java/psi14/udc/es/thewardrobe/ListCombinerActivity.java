package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Combination;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;

import static psi14.udc.es.thewardrobe.Utils.Constants.CHEST_ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.FEET_ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.LEGS_ID;


public class ListCombinerActivity extends Activity implements ListView.OnItemClickListener {
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
        combListView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Combination combination = combList.get(position);
        Intent intent = new Intent(new Intent(this,CombinerActivity.class));
        intent.putExtra(CHEST_ID, combination.getChestId());
        intent.putExtra(LEGS_ID, combination.getLegsId());
        intent.putExtra(FEET_ID, combination.getFeetId());

        startActivity(intent);
    }
}
