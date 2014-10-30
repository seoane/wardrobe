package psi14.udc.es.thewardrobe.DataSources;

/**
 * Created by Sokun on 29/10/14.
 */
public interface DataSourceInterface {
    /*
     * Open the connection to the database
     */
    public void open();


    /*
     * Closes the connection to the database
     */
    public void close();


}
