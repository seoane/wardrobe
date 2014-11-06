package psi14.udc.es.thewardrobe.DataSourcesTests;

import android.test.AndroidTestCase;

import psi14.udc.es.thewardrobe.ControlLayer.Feet;
import psi14.udc.es.thewardrobe.DataSources.FeetDataSource;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.FeetType;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 06/11/14.
 */
public class TestFeetDataSource extends AndroidTestCase {
    FeetDataSource feetDataSource;

    protected void setUp() throws Exception {

        feetDataSource = FeetDataSource.getInstance(getContext());
    }


    public void testAddAndFindToDataBase() {
        Feet feet =
                new Feet("Zapatitos", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", FeetType.SANDALS);
        int id = feetDataSource.addFeet(feet);
        Feet foundFeet = feetDataSource.getFeet(id);
        assertEquals(feet, foundFeet);
    }

    public void testDeleteFromDataBase() {
        Feet feet =
                new Feet("Zapatitos", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", FeetType.SANDALS);
        int id = feetDataSource.addFeet(feet);
        assertTrue(feetDataSource.deleteFeet(id));
    }

    public void tearDown() throws Exception {
        feetDataSource.close();
        super.tearDown();
    }
}
