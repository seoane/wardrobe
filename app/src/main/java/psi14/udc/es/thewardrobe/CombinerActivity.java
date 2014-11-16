package psi14.udc.es.thewardrobe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Iterator;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;

import static psi14.udc.es.thewardrobe.Utils.Constants.APP_TAG;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;


public class CombinerActivity extends FragmentActivity {
    static CombinerPageAdapter adapter;
    public String activityTag = "CombinerActivity";
    ClothDataSource clothDataSource;
    ViewPager pagerHead, pagerLegs, pagerFeet;
    CombinerOnPageListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combiner);
        clothDataSource = ClothDataSource.getInstance(this);
        fillChestViewPager();
        fillLegsViewPager();
        fillFeetsViewPager();
        if (DEBUG) Log.d(APP_TAG + activityTag, "ViewPager Loaded");
    }

    private void fillChestViewPager() {
        List<Cloth> _allChests = clothDataSource.getAllChests();
        Iterator _allChestIterator = _allChests.iterator();
        pagerHead = (ViewPager) findViewById(R.id.headPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allChestIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allChestIterator.next();
            adapter.addFragment(CombinerFragment.newInstance(argCloth));
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerHead.setOnPageChangeListener(pageChangeListener);
        pagerHead.setPageTransformer(true, new CombinerPageTransformer());
        pagerHead.setAdapter(adapter);
        pagerHead.setCurrentItem(0);
        ;

    }

    private void fillLegsViewPager() {
        List<Cloth> _allLegs = clothDataSource.getAllLegs();
        Iterator _allLegsIterator = _allLegs.iterator();
        pagerLegs = (ViewPager) findViewById(R.id.legsPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allLegsIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allLegsIterator.next();
            adapter.addFragment(CombinerFragment.newInstance(argCloth));
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerLegs.setOnPageChangeListener(pageChangeListener);
        pagerLegs.setPageTransformer(true, new CombinerPageTransformer());
        pagerLegs.setAdapter(adapter);
        pagerLegs.setCurrentItem(0);
        ;

    }

    private void fillFeetsViewPager() {
        List<Cloth> _allFeets = clothDataSource.getAllFeets();
        Iterator _allFeetsIterator = _allFeets.iterator();
        pagerFeet = (ViewPager) findViewById(R.id.feetPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allFeetsIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allFeetsIterator.next();
            adapter.addFragment(CombinerFragment.newInstance(argCloth));
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerFeet.setOnPageChangeListener(pageChangeListener);
        pagerFeet.setPageTransformer(true, new CombinerPageTransformer());
        pagerFeet.setAdapter(adapter);
        pagerFeet.setCurrentItem(0);
        ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.combiner, menu);
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
