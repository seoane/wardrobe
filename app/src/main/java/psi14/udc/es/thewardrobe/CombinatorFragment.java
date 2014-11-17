package psi14.udc.es.thewardrobe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;

import static psi14.udc.es.thewardrobe.Utils.Constants.APP_TAG;
import static psi14.udc.es.thewardrobe.Utils.Constants.DEBUG;
import static psi14.udc.es.thewardrobe.Utils.Constants.ID;
import static psi14.udc.es.thewardrobe.Utils.Constants.PARCELABLE_CLOTH_KEY;
import static psi14.udc.es.thewardrobe.Utils.Utilities.cancelPotentialWork;

public class CombinatorFragment extends Fragment implements ImageView.OnClickListener {
    public Resources res;
    public String activityTag = "CombinerFragment";
    public Cloth cloth;
    public ImageView imageView;
    Bitmap mPlaceHolderBitmap;

    public CombinatorFragment() {
    }

    public static CombinatorFragment newInstance(Cloth cloth) {
        CombinatorFragment combinerFragment = new CombinatorFragment();
        Bundle frag1Args = new Bundle();
        frag1Args.putParcelable(PARCELABLE_CLOTH_KEY, cloth);
        combinerFragment.setArguments(frag1Args);
        combinerFragment.setRetainInstance(true);
        return combinerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combiner, container, false);
        imageView = (ImageView) view.findViewById(R.id.fragmentImageView);
        imageView.setOnClickListener(this);
        if (DEBUG) Log.d(APP_TAG + activityTag, "onCreateView : View Instantiated");
        if (cloth.getUri() != null) {
            if (DEBUG)
                Log.d(APP_TAG + activityTag, "onCreateView : img URI: " + cloth.getUri());
            loadBitmap(cloth.getUri(), imageView);

        }
        //else loadBitmap(c); cargar Imagen por defecto
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.getArguments().isEmpty()) {
            cloth = this.getArguments().getParcelable(PARCELABLE_CLOTH_KEY);
            if (DEBUG) Log.d(APP_TAG + activityTag, cloth.toString());
        } else if (DEBUG) Log.d(APP_TAG + activityTag, "onCreate : NO BUNDLE AVAILABLE");
    }

    public void loadBitmap(String path, ImageView imageView) {
        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(res, mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            String bitmapSize = "128";
            task.execute(path,bitmapSize);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getActivity(), DetailsClothActivity.class);
        intent.putExtra(ID, cloth.getId());
        startActivity(intent);
    }

}