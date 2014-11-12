package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psi14.udc.es.thewardrobe.ControlLayer.Chest;
import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.ControlLayer.Feet;
import psi14.udc.es.thewardrobe.ControlLayer.Legs;
import psi14.udc.es.thewardrobe.DataSources.ChestDataSource;
import psi14.udc.es.thewardrobe.DataSources.FeetDataSource;
import psi14.udc.es.thewardrobe.DataSources.LegsDataSource;
import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Constants;
import psi14.udc.es.thewardrobe.Utils.FeetType;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;


public class macClothActivity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener{


    public final static String TAG = "macClothActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int THUMBSIZE = 230;

    EditText etName,etDescription;
    Spinner spBodyPart,spClothType,spSeason,spColor;
    Button butt_save;
    ImageView imageView;
    String[] bodyParts,chestTypes,legTypes,feetTypes,seasons,colors;
    String mCapturedPhotoPath,name,bodyPart,clothType,season,color,description;
    Integer id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_cloth);

        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        spBodyPart = (Spinner) findViewById(R.id.sp_bodyPart);
        spClothType = (Spinner) findViewById(R.id.sp_clothType);
        spSeason = (Spinner) findViewById(R.id.sp_season);
        spColor = (Spinner) findViewById(R.id.sp_color);
        imageView  =(ImageView) findViewById(R.id.img);
        butt_save = (Button) findViewById(R.id.butt_save);

        //Adapters
        bodyParts = getResources().getStringArray(R.array.bodyParts);
        chestTypes = getResources().getStringArray(R.array.chestTypes);
        legTypes = getResources().getStringArray(R.array.legsTypes);
        feetTypes = getResources().getStringArray(R.array.feetTypes);
        seasons = getResources().getStringArray(R.array.seasons);
        colors = getResources().getStringArray(R.array.colors);

        spBodyPart.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, bodyParts));
        spBodyPart.setHorizontalScrollBarEnabled(true);
        spBodyPart.setOnItemSelectedListener(this);
        spClothType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, chestTypes));
        spClothType.setHorizontalScrollBarEnabled(true);
        spSeason.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, seasons));
        spSeason.setHorizontalScrollBarEnabled(true);
        spColor.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, colors));
        spColor.setHorizontalScrollBarEnabled(true);

        imageView.setOnClickListener(this);
        butt_save.setOnClickListener(this);

/*        // Retrieve intent extras
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            id = extra.getInt(Constants.ID, 0);
            Cloth cloth = database.getNotas(id);
            if (cloth != null) {
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(cloth.getPhotographyPath()),
                        THUMBSIZE, THUMBSIZE);
                imageView.setImageBitmap(ThumbImage);
                etName.setText(cloth.getName());
                ArrayAdapter<String> bodyPartAdapter = (ArrayAdapter<String>) spBodyPart.getAdapter();
                spBodyPart.setSelection(bodyPartAdapter.getPosition(cloth.getBodyPart()));
                ArrayAdapter<String> clothTypeAdapter = (ArrayAdapter<String>) spClothType.getAdapter();
                spClothType.setSelection(clothTypeAdapter.getPosition(cloth.getClothType().toString()));
                ArrayAdapter<String> spSeasonAdapter = (ArrayAdapter<String>) spSeason.getAdapter();
                spSeason.setSelection(spSeasonAdapter.getPosition(cloth.getSeason().toString()));
                ArrayAdapter<String> spColorAdapter = (ArrayAdapter<String>) spColor.getAdapter();
                spColor.setSelection(spColorAdapter.getPosition(cloth.getColor().toString()));
                etDescription.setText(cloth.getDescription());
            } else {
                Log.d(TAG, "Cloth with ID: " + id + " not found");
            }
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mac_cloth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cancel:
                // Remove older image if exists
                if (mCapturedPhotoPath!=null)
                    removeFile(mCapturedPhotoPath);
                finish();
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

        switch (adapterView.getId()){
            case R.id.sp_bodyPart:
                String bodyPart = adapterView.getItemAtPosition(pos).toString();
                Log.d(TAG,"Seleccionada parte " + bodyPart );
                if (bodyPart.equalsIgnoreCase(getString(R.string.chest))){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, chestTypes));
                }else if (bodyPart.equalsIgnoreCase(getString(R.string.legs))){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, legTypes));
                }else if (bodyPart.equalsIgnoreCase(getString(R.string.feet))){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, feetTypes));
                }
            default:
                return;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == imageView){
            // Remove older image if exists
            if (mCapturedPhotoPath!=null)
                removeFile(mCapturedPhotoPath);
            Log.d(TAG,"Imagen click");
            dispatchTakePictureIntent();

        }
        else if (view == butt_save){

            if (createDatabaseEntry()) {
                finish();
            }
        }
    }

    private boolean createDatabaseEntry() {

        name = etName.getText().toString();
        bodyPart = spBodyPart.getSelectedItem().toString();
        clothType = spClothType.getSelectedItem().toString();
        season = spSeason.getSelectedItem().toString();
        color = spColor.getSelectedItem().toString();
        description = etDescription.getText().toString();

        if (!name.equalsIgnoreCase("")) {

            if (bodyPart.equalsIgnoreCase(getString(R.string.chest))) {

                Chest chest = new Chest(name,
                        Season.valueOf(season.toUpperCase().trim()),
                        Colors.valueOf(color.toUpperCase().trim()),
                        mCapturedPhotoPath, description.trim(),
                        ChestType.valueOf(clothType.toUpperCase().trim()));

                // Obtain DAO and add chest
                ChestDataSource chestDataSource = ChestDataSource.getInstance(this);
                chestDataSource.addChest(chest);

                Log.d(TAG, "Added: " + chest + " to the db");


            } else if (bodyPart.equalsIgnoreCase(getString(R.string.legs))) {

                Legs legs = new Legs(name,
                        Season.valueOf(season.toUpperCase().trim()),
                        Colors.valueOf(color.toUpperCase().trim()),
                        mCapturedPhotoPath, description.trim(),
                        LegsType.valueOf(clothType.toUpperCase().trim()));

                // Obtain DAO and add legs
                LegsDataSource legsDataSource = LegsDataSource.getInstance(this);
                legsDataSource.addLegs(legs);

                Log.d(TAG, "Added: " + legs + " to the db");


            } else if (bodyPart.equalsIgnoreCase(getString(R.string.feet))) {

                Feet feet = new Feet(name,
                        Season.valueOf(season.toUpperCase().trim()),
                        Colors.valueOf(color.toUpperCase().trim()),
                        mCapturedPhotoPath, description.trim(),
                        FeetType.valueOf(clothType.toUpperCase().trim()));

                // Obtain DAO and add legs
                FeetDataSource feetDataSource = FeetDataSource.getInstance(this);
                feetDataSource.addFeet(feet);

                Log.d(TAG, "Added: " + feet + " to the db");

            }
        }else {
            // If name is not set
            Toast.makeText(this,getString(R.string.name_not_set), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,getString(R.string.error_photo_saving),Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "CLOTH_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = null;

        if (isExternalStorageWritable()) {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            mCapturedPhotoPath = image.getAbsolutePath();
            Log.d(TAG, "createImageFile: " + mCapturedPhotoPath);
        } else{
            throw new IOException("External Storage not Writable");
        }
            return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            if (resultCode == RESULT_OK) {
                if (isExternalStorageReadable()) {
                    // TO-DO Problema raro... a veces mCapturedPhotoPath es null...
                    Log.d(TAG,"Creating thumbnail of " + mCapturedPhotoPath);
                    Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mCapturedPhotoPath),
                            THUMBSIZE, THUMBSIZE);
                    imageView.setImageBitmap(ThumbImage);
                }else{
                    Log.d(TAG,"Could not read External Storage");
                }
            }else {
               /*If result is not ok we delete the TempFile we created*/
                removeFile(mCapturedPhotoPath);
            }

        }
    }

    private void removeFile(String path){
            File file = new File(path);
            if (file.delete())
                Log.d(TAG,"Deleted file: " + path);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
