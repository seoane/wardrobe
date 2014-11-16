package psi14.udc.es.thewardrobe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

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
            adapter.addFragment(CombinatorFragment.newInstance(argCloth));
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
            adapter.addFragment(CombinatorFragment.newInstance(argCloth));
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
            adapter.addFragment(CombinatorFragment.newInstance(argCloth));
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
