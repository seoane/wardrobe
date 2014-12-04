package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;

import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Utilities.cancelPotentialWork;
import static psi14.udc.es.thewardrobe.Utils.Utilities.randInt;


public class DetailsClothActivity extends Activity implements View.OnClickListener {

    public final static String LOG_TAG = "DetailsClothActivity";

    ImageView imageView,imSuggColor,imSuggSeason,imSuggType;
    TextView tv_name, tv_bodyPart, tv_clothType, tv_season, tv_color, tv_description;
    Bitmap phBitmap;
    String[] bodyParts, seasons, colors;
    int id;
    ClothDataSource clothDataSource;
    Cloth cloth,suggColor,suggSeason,suggType;
    List<Cloth> suggColorList,suggSeasonList,suggTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cloth);

        //ImageView
        imageView = (ImageView) findViewById(R.id.imageView_details);
        imSuggColor = (ImageView) findViewById(R.id.im_suggestionColor);
        imSuggSeason = (ImageView) findViewById(R.id.im_suggestionSeason);
        imSuggType = (ImageView) findViewById(R.id.im_suggestionType);

        //Listeners
        imSuggColor.setOnClickListener(this);
        imSuggSeason.setOnClickListener(this);
        imSuggType.setOnClickListener(this);

        // TextViews
        tv_name = (TextView) findViewById(R.id.tv_details_name);
        tv_bodyPart = (TextView) findViewById(R.id.tv_details_bodyPart);
        tv_clothType = (TextView) findViewById(R.id.tv_details_type);
        tv_season = (TextView) findViewById(R.id.tv_details_season);
        tv_color = (TextView) findViewById(R.id.tv_details_color);
        tv_description = (TextView) findViewById(R.id.tv_details_description);

        /******** Arrays locale Strings *************/
        bodyParts = getResources().getStringArray(R.array.bodyParts);
        seasons = getResources().getStringArray(R.array.seasons);
        colors = getResources().getStringArray(R.array.colors);

        // Placeholder Bitmap
        phBitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_gallery);

        // Retrieve intent extras
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            id = extra.getInt(ID, 0);
            clothDataSource = ClothDataSource.getInstance(this);
            cloth = clothDataSource.getCloth(id);
            if (cloth != null) {
                // Loading bitmap in background
                loadBitmap(cloth.getUri(), imageView);
                tv_name.setText(cloth.getName());
                tv_bodyPart.setText(bodyParts[cloth.getBodyPart().ordinal()]);
                tv_clothType.setText(cloth.getType());
                tv_season.setText(seasons[cloth.getSeason().ordinal()]);
                tv_color.setText(colors[cloth.getColor().ordinal()]);
                tv_description.setText(cloth.getDescription());

            } else {
                if (DEBUG) Log.d(LOG_TAG, "Cloth with ID: " + id + " not found");
                finish();
            }

        }

        // Retrieve similar clothes
        retrieveSimilarClothes();

    }

    private void retrieveSimilarClothes(){
        // At the moment it retrieves randomly

        // Retrieve similar clothes
        suggColorList = clothDataSource.getByColor(cloth.getColor());
        //Remove actual cloth
        suggColorList.remove(cloth);
        if (suggColorList.size()>0) {
            int rand = randInt(0, suggColorList.size()-1);
            suggColor = suggColorList.get(rand);
            loadBitmap(suggColor.getUri(),imSuggColor);
        }

        suggSeasonList = clothDataSource.getBySeason(cloth.getSeason());
        //Remove actual cloth
        suggSeasonList.remove(cloth);
        if (suggSeasonList.size()>0) {
            int rand = randInt(0, suggSeasonList.size()-1);
            suggSeason = suggSeasonList.get(rand);
            loadBitmap(suggSeason.getUri(),imSuggSeason);
        }

        suggTypeList = clothDataSource.getByBodyPart(cloth.getBodyPart());
        //Remove actual cloth
        suggTypeList.remove(cloth);
        if (suggTypeList.size()>0) {
            int rand = randInt(0, suggTypeList.size()-1);
            suggType = suggTypeList.get(rand);
            loadBitmap(suggType.getUri(),imSuggType);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_cloth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d(LOG_TAG, "Edit:Selected cloth with ID: " + id);
                Intent intent = new Intent(this, MacClothActivity.class);
                intent.putExtra(ID, id);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_delete:
                if (DEBUG) Log.d(LOG_TAG, "Delete:Selected cloth with ID: " + id);
                clothDataSource.deleteCloth(id);
                //removeFile(cloth.getUri());
                finish();
                return true;
            case R.id.action_details_ok:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to load images in background
    public void loadBitmap(String path, ImageView imageView) {
        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), phBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(path, "256");
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DetailsClothActivity.class);
        if (view==imSuggColor){
            if (suggColor!=null){
                intent.putExtra(ID, suggColor.getId());
                startActivity(intent);
                finish();
            }

        }else if (view == imSuggSeason){
            if (suggSeason!=null) {
                intent.putExtra(ID, suggSeason.getId());
                startActivity(intent);
                finish();
            }

        }else if (view == imSuggType){
            if (suggType!=null) {
                intent.putExtra(ID, suggType.getId());
                startActivity(intent);
                finish();
            }

        }
    }
}
