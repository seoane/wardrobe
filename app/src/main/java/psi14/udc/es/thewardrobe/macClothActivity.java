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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psi14.udc.es.thewardrobe.Utils.Constants;

public class macClothActivity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener{


    public final static String TAG = "macClothActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int THUMBSIZE = 128;

    EditText etName,etDescription;
    Spinner spBodyPart,spClothType,spSeason,spColor;
    ImageView imageView;
    String[] bodyParts,chestTypes,legTypes,feetTypes,seasons,colors;
    String mCapturedPhotoPath;

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mac_cloth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:

                return true;
            case R.id.action_cancel:
                finish();
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

        switch (adapterView.getId()){
            case R.id.sp_bodyPart:
                String part = adapterView.getItemAtPosition(pos).toString();
                Log.d(TAG,"Seleccionada parte " + part );
                if (part.equalsIgnoreCase(getString(R.string.chest))){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, chestTypes));
                }else if (part.equalsIgnoreCase(getString(R.string.legs))){
                    spClothType.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item, legTypes));
                }else if (part.equalsIgnoreCase(getString(R.string.feet))){
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
        if (view == imageView)
        {
            Log.d(TAG,"Imagen click");
            dispatchTakePictureIntent();
        }
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

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCapturedPhotoPath = image.getAbsolutePath();
        Log.d(TAG,"createImageFile: " + mCapturedPhotoPath);
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mCapturedPhotoPath),
                    THUMBSIZE, THUMBSIZE);
            imageView.setImageBitmap(ThumbImage);
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
