package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import psi14.udc.es.thewardrobe.DataSources.ChestDataSource;


public class TheWardrobeMainActivity extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_wardrobe_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.the_wardrobe_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this,macClothActivity.class));
            return true;
        }else if (id == R.id.menu_list){
            startActivity(new Intent(this,listClothActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
