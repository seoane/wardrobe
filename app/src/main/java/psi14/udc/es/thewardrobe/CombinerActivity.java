package psi14.udc.es.thewardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.Iterator;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;
import psi14.udc.es.thewardrobe.Utils.Constants;

import static psi14.udc.es.thewardrobe.Utils.Constants.APP_TAG;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;


public class CombinerActivity extends FragmentActivity {
    static CombinerPageAdapter adapter;
    public String activityTag = "CombinerActivity";
    ClothDataSource clothDataSource;
    ViewPager pagerHead, pagerLegs, pagerFeet;
    CombinerOnPageListener pageChangeListener;
    List<Cloth> _allChests;
    List<Cloth> _allLegs;
    List<Cloth> _allFeets;
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
        _allChests = clothDataSource.getAllChests();
        Iterator _allChestIterator = _allChests.iterator();
        pagerHead = (ViewPager) findViewById(R.id.headPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allChestIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allChestIterator.next();
            CombinatorFragment combinatorFragment = CombinatorFragment.newInstance(argCloth);
            combinatorFragment.setHasOptionsMenu(true);
            adapter.addFragment(combinatorFragment);
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerHead.setOnPageChangeListener(pageChangeListener);
        pagerHead.setPageTransformer(true, new CombinerPageTransformer());
        pagerHead.setAdapter(adapter);
        pagerHead.setCurrentItem(0);
        ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.combiner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (DEBUG) Log.d(APP_TAG + activityTag,"onOptionsItemSelected");
        if (id == R.id.combiner_save) {
            int currentChestId = _allChests.get(pagerHead.getCurrentItem()).getId();
            int currentLegsId = _allLegs.get(pagerHead.getCurrentItem()).getId();
            int currentFeetsId = _allFeets.get(pagerHead.getCurrentItem()).getId();
            if (DEBUG) Log.d(APP_TAG + activityTag,
                    "\npager Head Current Item ID: " + currentChestId + "\n" +
                            " pager Legs Current Item ID: " + currentLegsId + "\n" +
                            " pager Feet Current Item ID: " + currentFeetsId
            );
            clothDataSource.addClothsToRT(currentChestId,currentLegsId,currentFeetsId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillLegsViewPager() {
        _allLegs = clothDataSource.getAllLegs();
        Iterator _allLegsIterator = _allLegs.iterator();
        pagerLegs = (ViewPager) findViewById(R.id.legsPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allLegsIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allLegsIterator.next();
            CombinatorFragment combinatorFragment = CombinatorFragment.newInstance(argCloth);
            combinatorFragment.setHasOptionsMenu(true);
            adapter.addFragment(combinatorFragment);
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
        _allFeets = clothDataSource.getAllFeets();
        Iterator _allFeetsIterator = _allFeets.iterator();
        pagerFeet = (ViewPager) findViewById(R.id.feetPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allFeetsIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allFeetsIterator.next();
            CombinatorFragment combinatorFragment = CombinatorFragment.newInstance(argCloth);
            combinatorFragment.setHasOptionsMenu(true);
            adapter.addFragment(combinatorFragment);
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerFeet.setOnPageChangeListener(pageChangeListener);
        pagerFeet.setPageTransformer(true, new CombinerPageTransformer());
        pagerFeet.setAdapter(adapter);
        pagerFeet.setCurrentItem(0);
        ;

    }

}
