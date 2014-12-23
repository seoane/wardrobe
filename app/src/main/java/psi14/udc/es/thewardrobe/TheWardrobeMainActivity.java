package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;

import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Utilities.cancelPotentialWork;
import static psi14.udc.es.thewardrobe.Utils.Utilities.randInt;


public class TheWardrobeMainActivity extends Activity implements ImageView.OnClickListener {

    public final static String LOG_TAG = "TheWardrobeMainActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private ClothDataSource clothDataSource;
    private List<Cloth> _allClothsOrderByFrequency;
    private ImageView ivMostUsed1, ivMostUsed2, ivMostUsed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_wardrobe_main);
        mDrawerMenu = getResources().getStringArray(R.array.drawer_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.action_open_drawer,  /* "open drawer" description */
                R.string.action_close_drawer  /* "close drawer" description */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(getResources().getString(R.string.app_name));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(getResources().getString(R.string.app_name));
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        Log.d(LOG_TAG, mDrawerMenu.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDrawerMenu);
        mDrawerList.setAdapter(adapter);
        // Set the list's click listener*/
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        clothDataSource = ClothDataSource.getInstance(this);

        ivMostUsed1 = (ImageView) findViewById(R.id.iv_most_used_1);
        ivMostUsed1.setOnClickListener(this);
        ivMostUsed2 = (ImageView) findViewById(R.id.iv_most_used_2);
        ivMostUsed2.setOnClickListener(this);
        ivMostUsed3 = (ImageView) findViewById(R.id.iv_most_used_3);
        ivMostUsed3.setOnClickListener(this);

        retrievePopularClothes();
    }

    private void retrievePopularClothes(){
        _allClothsOrderByFrequency = clothDataSource.getAllClothsOrderByFrequency();

        if (_allClothsOrderByFrequency.size()>0) {
            Cloth cloth = _allClothsOrderByFrequency.get(0);
            loadBitmap(cloth.getUri(), ivMostUsed1);
        }
        if (_allClothsOrderByFrequency.size()>1) {
            Cloth cloth = _allClothsOrderByFrequency.get(1);
            loadBitmap(cloth.getUri(), ivMostUsed2);
        }
        if (_allClothsOrderByFrequency.size()>2) {
            Cloth cloth = _allClothsOrderByFrequency.get(2);
            loadBitmap(cloth.getUri(), ivMostUsed3);
        }

    }

    public void loadBitmap(String path, ImageView imageView) {
        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            Bitmap phBitmap = null;
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), phBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(path, "256");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_most_used_1:
                intent = new Intent(this, DetailsClothActivity.class);
                intent.putExtra(ID, _allClothsOrderByFrequency.get(0).getId());
                startActivity(intent);
                break;
            case R.id.iv_most_used_2:
                intent = new Intent(this, DetailsClothActivity.class);
                intent.putExtra(ID, _allClothsOrderByFrequency.get(1).getId());
                startActivity(intent);
                break;
            case R.id.iv_most_used_3:
                intent = new Intent(this, DetailsClothActivity.class);
                intent.putExtra(ID, _allClothsOrderByFrequency.get(2).getId());
                startActivity(intent);
                break;

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(view.getContext(), ListClothActivity.class));
                break;
                case 1:
                    startActivity(new Intent(view.getContext(), CombinerActivity.class));
                break;
                case 2:
                    startActivity(new Intent(view.getContext(), ListCombinerActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(view.getContext(),TheWardrobeHelpActivity.class));
            }
        }
    }

}
