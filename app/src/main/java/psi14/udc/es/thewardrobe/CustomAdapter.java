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

import static psi14.udc.es.thewardrobe.Utils.Utilities.cancelPotentialWork;

/********* Adapter class extends with BaseAdapter  ************/
public class CustomAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private List<Cloth> list;
    private static LayoutInflater inflater=null;
    public Resources res;
    Cloth tempValues=null;
    Bitmap mPlaceHolderBitmap;
    String[] bodyParts,seasons,colors;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity activity, List<Cloth> list,Resources res) {

        /********** Take passed values **********/
        this.activity = activity;
        this.list=list;
        this.res = res;

        /************Placeholder bitmap******************/
        mPlaceHolderBitmap = BitmapFactory.decodeResource(res, android.R.drawable.gallery_thumb);


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        /******** Arrays locale Strings *************/
        bodyParts = res.getStringArray(R.array.bodyParts);
        seasons = res.getStringArray(R.array.seasons);
        colors = res.getStringArray(R.array.colors);

    }

    /******** What is the size of Passed List Size ************/
    public int getCount() {

        if(list.size()<=0)
            return 1;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return list.get(position).getId();
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView bodyPart;
        public TextView type;
        public TextView season;
        public TextView color;
        public ImageView image;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate list_row.xml file for each row ( Defined below ) *******/
            view = inflater.inflate(R.layout.list_row, null);

            /****** View Holder Object to contain list_row.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_row_name);
            holder.bodyPart = (TextView) view.findViewById(R.id.tv_row_bodyPart);
            holder.type = (TextView) view.findViewById(R.id.tv_row_type);
            holder.color=(TextView)view.findViewById(R.id.tv_row_color);
            holder.season=(TextView)view.findViewById(R.id.tv_row_season);
            holder.type=(TextView)view.findViewById(R.id.tv_row_type);
            holder.image=(ImageView)view.findViewById(R.id.image);


            /************  Set holder with LayoutInflater ************/
            view.setTag( holder );
        }
        else
            holder=(ViewHolder)view.getTag();

        if(list.size()<=0)
        {
            //holder.name.setText("No Data");
            view = inflater.inflate(R.layout.no_data,null);

        }
        else
        {
            /***** Get each Cloth from Arraylist ********/
            tempValues=null;
            tempValues = list.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.name.setText(tempValues.getName());
            holder.bodyPart.setText(bodyParts[tempValues.getBodyPart().ordinal()]);
            holder.type.setText(tempValues.getType());
            holder.season.setText(seasons[tempValues.getSeason().ordinal()]);
            holder.color.setText(colors[tempValues.getColor().ordinal()]);
            loadBitmap(tempValues.getUri(),holder.image);


        }
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
