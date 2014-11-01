package psi14.udc.es.thewardrobe.DataSources;


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
