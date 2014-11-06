package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import psi14.udc.es.thewardrobe.DataSources.ChestDataSource;


public class TheWardrobeMainActivity extends Activity {
    ChestDataSource chestDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_wardrobe_main);
        chestDataSource = ChestDataSource.getInstance(this);
        //Chest chest = new Chest("Falda de putilla", Season.AUTUMN, Colors.BLUE, "/NULL", "es de ser putas", ChestType.BLOUSES);
        //chestDataSource.addChest(chest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.the_wardrobe_main, menu);
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
