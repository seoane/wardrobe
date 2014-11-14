package psi14.udc.es.thewardrobe;



import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import static psi14.udc.es.thewardrobe.Utils.Constants.THUMBSIZE;
import static psi14.udc.es.thewardrobe.Utils.Utilities.decodeSampledBitmapFromPath;
import static psi14.udc.es.thewardrobe.Utils.Utilities.getBitmapWorkerTask;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private String path = "";

    public BitmapWorkerTask(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    public String getPath() {
        return path;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        path = params[0];
        if (params.length>1) {
            int size = Integer.valueOf(params[1]);
            return decodeSampledBitmapFromPath(path, size, size);
        }
        return decodeSampledBitmapFromPath(path, THUMBSIZE, THUMBSIZE);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask =
                    getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }


}

