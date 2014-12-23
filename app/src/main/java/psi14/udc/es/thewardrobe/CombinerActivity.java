package psi14.udc.es.thewardrobe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;
import psi14.udc.es.thewardrobe.Utils.Constants;

import static psi14.udc.es.thewardrobe.Utils.Constants.APP_TAG;
import static psi14.udc.es.thewardrobe.Utils.Constants.CHEST_ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;
import static psi14.udc.es.thewardrobe.Utils.Constants.FEET_ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.LEGS_ID;


public class CombinerActivity extends FragmentActivity {
    static CombinerPageAdapter adapter;
    public String activityTag = "CombinerActivity";
    ClothDataSource clothDataSource;
    ViewPager pagerChest, pagerLegs, pagerFeet;
    CombinerOnPageListener pageChangeListener;
    List<Cloth> _allChests;
    List<Cloth> _allLegs;
    List<Cloth> _allFeets;
    EditText etAlertDialogCombiner;
    String combName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combiner);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        clothDataSource = ClothDataSource.getInstance(this);
        Bundle extra = getIntent().getExtras();
        fillChestViewPager();
        fillLegsViewPager();
        fillFeetsViewPager();

        if (extra != null) {
            int chestId = extra.getInt(CHEST_ID, 0);
            int feetId = extra.getInt(LEGS_ID, 0);
            int legsId = extra.getInt(FEET_ID, 0);
            clothDataSource = ClothDataSource.getInstance(this);
            Cloth chest = clothDataSource.getCloth(chestId);
            Cloth legs = clothDataSource.getCloth(legsId);
            Cloth feet = clothDataSource.getCloth(feetId);

            if (chest != null && legs != null && feet != null) {
                    pagerChest.setCurrentItem(_allChests.indexOf(chest));
                    pagerLegs.setCurrentItem(_allLegs.indexOf(legs));
                    pagerFeet.setCurrentItem(_allFeets.indexOf(feet));


            } else {
                if (DEBUG) Log.d(activityTag, "Cloth not found");
                finish();
            }
        }

        if (DEBUG) Log.d(APP_TAG + activityTag, "ViewPager Loaded");
    }

    private void fillChestViewPager() {
        _allChests = clothDataSource.getAllChests();
        Iterator _allChestIterator = _allChests.iterator();
        pagerChest = (ViewPager) findViewById(R.id.headPager);
        adapter = new CombinerPageAdapter(getSupportFragmentManager());

        while (_allChestIterator.hasNext()) {
            Cloth argCloth = (Cloth) _allChestIterator.next();
            CombinatorFragment combinatorFragment = CombinatorFragment.newInstance(argCloth);
            combinatorFragment.setHasOptionsMenu(true);
            adapter.addFragment(combinatorFragment);
            if (DEBUG) Log.d(APP_TAG + activityTag, "Adapter Size :" + adapter.getCount());
        }
        pageChangeListener = new CombinerOnPageListener();
        pagerChest.setOnPageChangeListener(pageChangeListener);
        pagerChest.setPageTransformer(true, new CombinerPageTransformer());
        pagerChest.setAdapter(adapter);
        pagerChest.setCurrentItem(0);

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
            if (_allFeets.size() != 0 && _allChests.size() != 0 && _allLegs.size() !=0) {
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.alert_dialog_edit_text, null);
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_dialog_comb_name))
                        .setView(view)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int currentChestId = _allChests.get(pagerChest.getCurrentItem()).getId();
                                int currentLegsId = _allLegs.get(pagerLegs.getCurrentItem()).getId();
                                int currentFeetsId = _allFeets.get(pagerFeet.getCurrentItem()).getId();
                                if (DEBUG) Log.d(APP_TAG + activityTag,
                                        "\npager Head Current Item ID: " + currentChestId + "\n" +
                                                " pager Legs Current Item ID: " + currentLegsId + "\n" +
                                                " pager Feet Current Item ID: " + currentFeetsId
                                );
                                etAlertDialogCombiner = (EditText) view.findViewById(R.id.et_comb_alert_dialog);
                                combName = etAlertDialogCombiner.getText().toString();
                                clothDataSource.addClothsToRT(currentChestId, currentLegsId, currentFeetsId, combName);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
                return true;
            }
            else {
                Toast.makeText(this, getString(R.string.incomplete_combination), Toast.LENGTH_SHORT).show();

            }
        }
        if (id == R.id.combiner_use) {
            int currentChestId = _allChests.get(pagerChest.getCurrentItem()).getId();
            int currentLegsId = _allLegs.get(pagerLegs.getCurrentItem()).getId();
            int currentFeetsId = _allFeets.get(pagerFeet.getCurrentItem()).getId();
            Cloth currentChest = clothDataSource.getCloth(currentChestId);
            Cloth currentLegs = clothDataSource.getCloth(currentLegsId);
            Cloth currentFeets = clothDataSource.getCloth(currentFeetsId);
            if (DEBUG) Log.d(APP_TAG + activityTag,
                    "\nFrequency Head Current: " + currentChest.getFrequency() + "\n" +
                            " Frequency Legs Current: " + currentLegs.getFrequency() + "\n" +
                            " Frequency Feet Current : " + currentFeets.getFrequency()
            );
            currentChest.use();
            currentLegs.use();
            currentFeets.use();

            if (DEBUG) Log.d(APP_TAG + activityTag,
                    "\nFrequency Head Middle: " + currentChest.getFrequency() + "\n" +
                            " Frequency Legs Middle: " + currentLegs.getFrequency() + "\n" +
                            " Frequency Feet Middle : " + currentFeets.getFrequency()
            );

            clothDataSource.updateCloth(currentChest);
            clothDataSource.updateCloth(currentLegs);
            clothDataSource.updateCloth(currentFeets);

            if (DEBUG) {
                currentChest = clothDataSource.getCloth(currentChestId);
                currentLegs = clothDataSource.getCloth(currentLegsId);
                currentFeets = clothDataSource.getCloth(currentFeetsId);
                Log.d(APP_TAG + activityTag,
                        "\nFrequency Head After: " + currentChest.getFrequency() + "\n" +
                                " Frequency Legs After: " + currentLegs.getFrequency() + "\n" +
                                " Frequency Feet After : " + currentFeets.getFrequency()
                );
            }
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
