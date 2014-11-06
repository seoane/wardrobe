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


public class TheWardrobeMainActivity extends Activity implements View.OnClickListener{
    ChestDataSource chestDataSource;
    Button but_mac;
    ListView lv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_wardrobe_main);
        chestDataSource = ChestDataSource.getInstance(this);
        //Chest chest = new Chest("Falda de putilla", Season.AUTUMN, Colors.BLUE, "/NULL", "es de ser putas", ChestType.BLOUSES);
        //chestDataSource.addChest(chest);
        but_mac = (Button) findViewById(R.id.but_mac);
        lv_main = (ListView) findViewById(R.id.lv_main);
        but_mac.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view == but_mac){
            startActivity(new Intent(this,macClothActivity.class));
        }
    }
}
