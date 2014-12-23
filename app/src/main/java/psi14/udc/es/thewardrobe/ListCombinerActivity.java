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

        getActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Combination combination = combList.get(position);
        Intent intent = new Intent(new Intent(this,CombinerActivity.class));
        intent.putExtra(CHEST_ID, combination.getChestId());
        intent.putExtra(LEGS_ID, combination.getLegsId());
        intent.putExtra(FEET_ID, combination.getFeetId());

        startActivity(intent);
    }
}
