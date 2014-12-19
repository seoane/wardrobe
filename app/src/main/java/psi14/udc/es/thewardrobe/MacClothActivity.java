package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;
import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;
import psi14.udc.es.thewardrobe.Utils.Utilities;

import static psi14.udc.es.thewardrobe.Utils.Constants.*;
import static psi14.udc.es.thewardrobe.Utils.Utilities.isLandscape;


public class MacClothActivity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener{


    public final static String LOG_TAG = "MacClothActivity";
    private final static String PATH="mCapturedPhotoPath";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;

    EditText etName,etDescription;
    Spinner spBodyPart,spClothType,spSeason,spColor;
    ImageView imageView;
    String[] bodyParts,chestTypes,legTypes,feetTypes,seasons,colors;
    String prevCapturedPhotoPath,mCapturedPhotoPath,name,clothType,description;
    Integer id=null,indexBodyPart,indexSeason,indexColor;
    ClothDataSource clothDataSource;
    Boolean ignoreEvent = true;
    Cloth oldCloth=null;

    // Enum values
    BodyParts[] bodyPartValues = BodyParts.values();
    Colors[] colorValues = Colors.values();
    Season[] seasonValues = Season.values();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEBUG) Log.d(LOG_TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_cloth);


        //Views
        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        spBodyPart = (Spinner) findViewById(R.id.sp_bodyPart);
        spClothType = (Spinner) findViewById(R.id.sp_clothType);
        spSeason = (Spinner) findViewById(R.id.sp_season);
        spColor = (Spinner) findViewById(R.id.sp_color);
        imageView  =(ImageView) findViewById(R.id.img);

        //Adapters (could be done on xml)
        bodyParts = getResources().getStringArray(R.array.bodyParts);
        chestTypes = getResources().getStringArray(R.array.chestTypes);
        legTypes = getResources().getStringArray(R.array.legsTypes);
        feetTypes = getResources().getStringArray(R.array.feetTypes);
        seasons = getResources().getStringArray(R.array.seasons);
        colors = getResources().getStringArray(R.array.colors);

        spBodyPart.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, bodyParts));
        spBodyPart.setHorizontalScrollBarEnabled(true);
        spClothType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, chestTypes));
        spClothType.setHorizontalScrollBarEnabled(true);
        spSeason.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, seasons));
        spSeason.setHorizontalScrollBarEnabled(true);
        spColor.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, colors));
        spColor.setHorizontalScrollBarEnabled(true);

        // Obtain DAO and add chest
        clothDataSource = ClothDataSource.getInstance(this);

        // Retrieve intent extras
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            id = extra.getInt(ID, 0);
            oldCloth = clothDataSource.getCloth(id);
            if (oldCloth != null) {
                // Loading bitmap in background
                Utilities.loadBitmap(oldCloth.getUri(), imageView);

                etName.setText(oldCloth.getName());

                // We use the ordinal o the enum to avoid conflicts with languages*
                spBodyPart.setSelection(oldCloth.getBodyPart().ordinal());

                //Check which bodypart is selected to set type adapter properly
                if (oldCloth.getBodyPart()==BodyParts.CHEST){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, chestTypes));
                }else if (oldCloth.getBodyPart()==BodyParts.LEGS){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, legTypes));
                }else if (oldCloth.getBodyPart()==BodyParts.FEET){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, feetTypes));
                }

                ArrayAdapter<String> clothTypeAdapter = (ArrayAdapter<String>) spClothType.getAdapter();
                spClothType.setSelection(clothTypeAdapter.getPosition(oldCloth.getType()));

                spSeason.setSelection(oldCloth.getSeason().ordinal());

                spColor.setSelection(oldCloth.getColor().ordinal());

                etDescription.setText(oldCloth.getDescription());

                mCapturedPhotoPath=oldCloth.getUri();

            } else {
                if (DEBUG) Log.d(LOG_TAG, "Cloth with ID: " + id + " not found");
            }
        }

        //Listeners
        spBodyPart.setOnItemSelectedListener(this);
        imageView.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mac_cloth, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Needed because android kills the activity sometimes when calling the camera
        if (mCapturedPhotoPath!=null)
            outState.putString(PATH,mCapturedPhotoPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null) {
            mCapturedPhotoPath = savedInstanceState.getString(PATH);
            if (DEBUG) Log.d(LOG_TAG, "Retrieved value:" + mCapturedPhotoPath);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cancel:
                // Remove older image if exists and we arent updating an existing one
                if (mCapturedPhotoPath!=null && oldCloth==null)
                    removeFile(mCapturedPhotoPath);
                // Ask for user confirmation
                createConfirmDialog();
                return true;
            case R.id.action_ok:
                name = etName.getText().toString();
                indexBodyPart = spBodyPart.getSelectedItemPosition();
                clothType = spClothType.getSelectedItem().toString();
                indexSeason = spSeason.getSelectedItemPosition();
                indexColor = spColor.getSelectedItemPosition();
                description = etDescription.getText().toString();

                if (oldCloth != null){
                    // We are modifying a cloth
                    if(updateDatabaseEntry(oldCloth,name,indexBodyPart,clothType,indexSeason,indexColor,description)){
                        Toast.makeText(this,getString(R.string.cloth_updated), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    // Creating a new cloth
                    if (createDatabaseEntry(name,indexBodyPart,clothType,indexSeason,indexColor,description)) {
                        Toast.makeText(this,getString(R.string.cloth_added), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // Flag to avoid event firing onCreate
        if (ignoreEvent){
            ignoreEvent=false;
            return;
        }

        switch (adapterView.getId()){
            case R.id.sp_bodyPart:

                // Again we use position in array to avoid language conflicts
                indexBodyPart = adapterView.getSelectedItemPosition();

                if (bodyPartValues[indexBodyPart]==BodyParts.CHEST){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, chestTypes));
                }else if (bodyPartValues[indexBodyPart]==BodyParts.LEGS){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, legTypes));
                }else if (bodyPartValues[indexBodyPart]==BodyParts.FEET){
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
            // Remember older image if we fail to get a new one
            prevCapturedPhotoPath = mCapturedPhotoPath;
            if (DEBUG) Log.d(LOG_TAG,"Image click");

            final String[] items = getResources().getStringArray(R.array.img_sources);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.select_source));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            dispatchTakePictureIntent();
                            break;
                        case 1:
                            dispatchGalleryIntent();
                            break;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    private boolean updateDatabaseEntry(Cloth cloth,String name,Integer indexBodyPart,String clothType,Integer indexSeason,
                                        Integer indexColor,String description) {


        if (!name.equalsIgnoreCase("") && mCapturedPhotoPath!=null) {
            cloth.setName(name);
            cloth.setBodyPart(bodyPartValues[indexBodyPart]);
            cloth.setType(clothType);
            cloth.setSeason(seasonValues[indexSeason]);
            cloth.setColor(colorValues[indexColor]);
            cloth.setDescription(description);
            cloth.setUri(mCapturedPhotoPath);

            clothDataSource.updateCloth(cloth);

            Log.d(LOG_TAG, "Update: " + cloth + " into the db");

        }else {
            // If name and image are not set
            Toast.makeText(this,getString(R.string.name_not_set), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    private boolean createDatabaseEntry(String name,Integer indexBodyPart,String clothType,Integer indexSeason,
                                        Integer indexColor,String description) {


        if (!name.equalsIgnoreCase("") && mCapturedPhotoPath!=null) {

            Cloth cloth = new Cloth(name,
                    bodyPartValues[indexBodyPart],
                    clothType,
                    seasonValues[indexSeason],
                    colorValues[indexColor],
                    description.trim(),
                    mCapturedPhotoPath,
                    0);

            clothDataSource.addCloth(cloth);

            Log.d(LOG_TAG, "Add: " + cloth + " to the db");

        }else {
            // If name is not set
            Toast.makeText(this,getString(R.string.name_not_set), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    private void dispatchGalleryIntent(){
        if (DEBUG) Log.d(LOG_TAG,"Gallery Intent Dispatched");
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , REQUEST_IMAGE_GALLERY);

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
        File storageDir = getExternalFilesDir(DIRECTORY_PICTURES);
        File image;

        if (isExternalStorageWritable()) {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            mCapturedPhotoPath = image.getAbsolutePath();
            Log.d(LOG_TAG, "createImageFile: " + mCapturedPhotoPath);
        } else{
            throw new IOException("External Storage not Writable");
        }
            return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            if (resultCode == RESULT_OK) {
                if (isExternalStorageReadable()) {
                    if (DEBUG) Log.d(LOG_TAG,"Creating thumbnail of " + mCapturedPhotoPath);
                    // Loading bitmap in background
                    if (isLandscape(mCapturedPhotoPath)) {
                        Utilities.loadBitmap(mCapturedPhotoPath, imageView);
                        //Delete the old image
                        if (prevCapturedPhotoPath!=null){
                            removeFile(prevCapturedPhotoPath);
                            prevCapturedPhotoPath=null;
                        }
                    }else{
                        Toast.makeText(this,getString(R.string.landscape),Toast.LENGTH_SHORT).show();
                        mCapturedPhotoPath= prevCapturedPhotoPath;
                    };
                }else{
                    if (DEBUG) Log.d(LOG_TAG,"Could not read External Storage");
                }
            }else {
               /*If result is not ok we delete the TempFile we created*/
                if (mCapturedPhotoPath!=null)
                    removeFile(mCapturedPhotoPath);
                /*We left the old one in place*/
                mCapturedPhotoPath=prevCapturedPhotoPath;
            }

        }else if (requestCode == REQUEST_IMAGE_GALLERY){
            if (resultCode == RESULT_OK){
                if (DEBUG) Log.d(LOG_TAG, "Receiving image from gallery" + returnedIntent.getData());
                Uri selectedImage = returnedIntent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                mCapturedPhotoPath=filePath;
                if (isExternalStorageReadable()) {
                    if (DEBUG) Log.d(LOG_TAG,"Creating thumbnail of " + mCapturedPhotoPath);
                    // Loading bitmap in background
                    if (isLandscape(mCapturedPhotoPath)) {
                        Utilities.loadBitmap(mCapturedPhotoPath, imageView);
                    }else{
                        Toast.makeText(this,getString(R.string.landscape),Toast.LENGTH_SHORT).show();
                        mCapturedPhotoPath=prevCapturedPhotoPath;
                    }
                }else{
                    if (DEBUG) Log.d(LOG_TAG,"Could not read External Storage");
                }

            }
        }
    }

    private void removeFile(String path){
            File file = new File(path);
            if (file.delete())
                Log.d(LOG_TAG,"Deleted file: " + path);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }


    private void createConfirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setTitle(getString(R.string.confirm));

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
