package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.ControlLayer.Combination;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;

import static psi14.udc.es.thewardrobe.Utils.Utilities.cancelPotentialWork;

/********* Adapter class extends with BaseAdapter  ************/
public class CombinerCustomAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private List<Combination> list;
    private static LayoutInflater inflater=null;
    public Resources res;
    Combination tempCombination = null;
    Bitmap mPlaceHolderBitmap;
    ClothDataSource clothDataSource;

    /*************  CustomAdapter Constructor *****************/
    public CombinerCustomAdapter(Activity activity, List<Combination> list, Resources res) {

        /********** Take passed values **********/
        this.activity = activity;
        this.list=list;
        this.res = res;

        /************Placeholder bitmap******************/
        mPlaceHolderBitmap = BitmapFactory.decodeResource(res, android.R.drawable.ic_menu_gallery);


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    /******** What is the size of Passed List Size ************/
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public ImageView chest;
        public ImageView legs;
        public ImageView feet;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate list_row.xml file for each row ( Defined below ) *******/
            view = inflater.inflate(R.layout.list_row_combiner, null);

            /****** View Holder Object to contain list_row.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView)view.findViewById(R.id.tv_comb_row_name);
            holder.chest = (ImageView)view.findViewById(R.id.image_chest);
            holder.legs = (ImageView)view.findViewById(R.id.image_legs);
            holder.feet = (ImageView)view.findViewById(R.id.image_feet);


            /************  Set holder with LayoutInflater ************/
            view.setTag( holder );
        }
        else
            holder=(ViewHolder)view.getTag();

        /***** Get each Cloth from Arraylist ********/
        tempCombination = null;
        tempCombination = list.get( position );

        /************  Set Model values in Holder elements ***********/
        clothDataSource = ClothDataSource.getInstance(activity);
        holder.name.setText(tempCombination.getName());
        loadBitmap(clothDataSource.getCloth(tempCombination.getChestId()).getUri(),holder.chest);
        loadBitmap(clothDataSource.getCloth(tempCombination.getLegsId()).getUri(),holder.legs);
        loadBitmap(clothDataSource.getCloth(tempCombination.getFeetId()).getUri(),holder.feet);

        return view;
    }

    // Method to load images in background
    public void loadBitmap(String path, ImageView imageView) {
        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(res, mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(path);
        }
    }
}
