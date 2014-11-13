package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private List<Cloth> list;
    private static LayoutInflater inflater=null;
    public Resources resLocal;
    Cloth tempValues=null;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity activity, List<Cloth> list,Resources resLocal) {

        /********** Take passed values **********/
        this.activity = activity;
        this.list=list;
        this.resLocal = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
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
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView bodyPart;
        public TextView type;
        public TextView season;
        public TextView color;
        public TextView description;
        public TextView uri;
        public ImageView image;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            view = inflater.inflate(R.layout.list_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_row_name);
            holder.bodyPart = (TextView) view.findViewById(R.id.tv_row_bodyPart);
            holder.type = (TextView) view.findViewById(R.id.tv_row_type);
            holder.color=(TextView)view.findViewById(R.id.tv_row_color);
            holder.season=(TextView)view.findViewById(R.id.tv_row_season);
            holder.uri=(TextView)view.findViewById(R.id.tv_row_uri);
            holder.description=(TextView)view.findViewById(R.id.tv_row_description);
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
            tempValues = ( Cloth ) list.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.name.setText(tempValues.getName());
            holder.bodyPart.setText(tempValues.getBodyPart().toString());
            holder.type.setText(tempValues.getType().toString());
            holder.season.setText(tempValues.getSeason().toString());
            holder.color.setText(tempValues.getColor().toString());
            holder.description.setText(tempValues.getDescription());
            holder.uri.setText(tempValues.getUri());
           /* Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(tempValues.getPhotographyPath()),
                    64, 64);
            holder.image.setImageBitmap(thumbImage);*/


        }
        return view;
    }
}
