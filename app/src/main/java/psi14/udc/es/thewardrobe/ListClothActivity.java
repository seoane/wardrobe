package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;
import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Constants;
import psi14.udc.es.thewardrobe.Utils.Season;

import static psi14.udc.es.thewardrobe.Utils.Constants.BODYPART;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;


public class ListClothActivity extends Activity implements ListView.OnItemClickListener {

    public static final String LOG_TAG = "ListClothActivity";

    ListView lv;
    ClothDataSource clothDataSource;

    List<Cloth> listCloth;
    CustomAdapter adapter;
    AdapterView.AdapterContextMenuInfo info;
    int posSelected=0;
    String[] filters,subfilters,bodyParts,seasons,colors;

    // Enum values
    BodyParts[] bodyPartValues = BodyParts.values();
    Colors[] colorValues = Colors.values();
    Season[] seasonValues = Season.values();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_list_cloth);
        lv = (ListView) findViewById(R.id.lv_main);
        lv.setOnItemClickListener(this);
        // Obtain dataSources
        clothDataSource = ClothDataSource.getInstance(this);
        filters = getResources().getStringArray(R.array.filters);
        bodyParts = getResources().getStringArray(R.array.bodyParts);
        seasons = getResources().getStringArray(R.array.seasons);
        colors = getResources().getStringArray(R.array.colors);

        // Context Menu
        registerForContextMenu(lv);

        //Load data: (Should be done on backGround)
        updateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_cloth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this, MacClothActivity.class));
            return true;
        } else if (id == R.id.menu_update) {
            updateList();
            return true;
        }else if (id == R.id.menu_filter){
            createDialogFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (DEBUG) Log.d(LOG_TAG, "ContextMenu: Position on listView: " + info.position);
        Cloth cloth = (Cloth) lv.getAdapter().getItem(info.position);
        int id = cloth.getId();

        switch (item.getItemId()) {
            case R.id.context_edit:
                Log.d(LOG_TAG, "Edit:Selected cloth with ID: " + id);
                Intent intent = new Intent(this, MacClothActivity.class);
                intent.putExtra(Constants.ID, id);
                startActivity(intent);
                return true;
            case R.id.context_delete:
                if (DEBUG) Log.d(LOG_TAG, "Delete:Selected cloth with ID: " + id);
                clothDataSource.deleteCloth(id);
                //removeFile(cloth.getUri());
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateList() {

        listCloth = clothDataSource.getAllCloths();

        Resources res = getResources();
        adapter = new CustomAdapter(this, listCloth, res);
        lv.setAdapter(adapter);

        if (listCloth.size()==0)
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();

    }

    private void updateList(List<Cloth> list){

       Resources res = getResources();
       adapter = new CustomAdapter(this, list, res);
       lv.setAdapter(adapter);

       if (listCloth.size()==0)
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsClothActivity.class);
        intent.putExtra(ID, listCloth.get(position).getId());
        startActivity(intent);
    }

    private void createDialogFilter(){
        // We use position instead of string due to translation problems
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.select_filter))

                .setSingleChoiceItems(filters, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        posSelected = pos;
                        Log.d(LOG_TAG, "DialogFilter: Selected " + filters[posSelected]);
                    }
                })


                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListClothActivity.this);
                        switch (posSelected) {
                            // Bodypart
                            case 0:
                                subfilters = bodyParts;
                                break;
                            // Season
                            case 1:
                                subfilters = seasons;
                                break;
                            // Color
                            case 2:
                                subfilters = colors;
                                break;
                        }
                        builder.setSingleChoiceItems(subfilters, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int pos) {
                                Toast.makeText(getApplicationContext(), subfilters[pos],
                                        Toast.LENGTH_SHORT).show();
                                List<Cloth> selectedCloths=new ArrayList<Cloth>();
                                switch (posSelected) {
                                    // Bodypart
                                    case 0:
                                        Log.d(LOG_TAG,"Filtering database with: " + bodyPartValues[pos].toString());
                                        selectedCloths = clothDataSource.getByBodyPart(bodyPartValues[pos]);
                                        break;
                                    // Season
                                    case 1:
                                        Log.d(LOG_TAG,"Filtering database with: " + seasonValues[pos].toString());
                                        selectedCloths = clothDataSource.getBySeason(seasonValues[pos]);
                                        break;
                                    // Color
                                    case 2:
                                        Log.d(LOG_TAG,"Filtering database with: " + colorValues[pos].toString());
                                        selectedCloths = clothDataSource.getByColor(colorValues[pos]);
                                        break;
                                }
                                updateList(selectedCloths);
                                dialog.dismiss();

                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }
}

