package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TheWardrobeMainActivity extends Activity {

    public final static String LOG_TAG = "TheWardrobeMainActivity";

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
            startActivity(new Intent(this, MacClothActivity.class));
            return true;
        } else if (id == R.id.menu_list) {
            startActivity(new Intent(this, ListClothActivity.class));
            return true;
        } else if (id == R.id.menu_combiner) {
            startActivity(new Intent(this, CombinerActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
