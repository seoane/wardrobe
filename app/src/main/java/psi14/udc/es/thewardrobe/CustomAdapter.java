package psi14.udc.es.thewardrobe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Cloth tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView season;
        public TextView color;
        public TextView uri;
        public TextView description;
        public TextView type;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.tv_row_name);
            holder.color=(TextView)vi.findViewById(R.id.tv_row_color);
            holder.season=(TextView)vi.findViewById(R.id.tv_row_season);
            holder.uri=(TextView)vi.findViewById(R.id.tv_row_uri);
            holder.description=(TextView)vi.findViewById(R.id.tv_row_description);
            holder.type=(TextView)vi.findViewById(R.id.tv_row_type);



            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( Cloth ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.name.setText(tempValues.getName());
            holder.color.setText(tempValues.getColor().toString());
            holder.season.setText(tempValues.getSeason().toString());
            holder.uri.setText(tempValues.getPhotographyPath());
            holder.description.setText(tempValues.getDescription());


        }
        return vi;
    }
}
