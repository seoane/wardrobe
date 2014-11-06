package psi14.udc.es.thewardrobe.DataSourcesTests;

/**
 * Created by Sokun on 06/11/14.
 */

import android.test.AndroidTestCase;

import psi14.udc.es.thewardrobe.ControlLayer.Legs;
import psi14.udc.es.thewardrobe.DataSources.LegsDataSource;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;

public class TestLegsDataSource extends AndroidTestCase {
    LegsDataSource legsDataSource;

    protected void setUp() throws Exception {

        legsDataSource = LegsDataSource.getInstance(getContext());
    }


    public void testAddAndFindToDataBase() {
        Legs legs =
                new Legs("Leggins", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", LegsType.LEGGINGS);
        int id = legsDataSource.addLegs(legs);
        Legs foundLegs = legsDataSource.getLegs(id);
        assertEquals(legs, foundLegs);
    }

    public void testDeleteFromDataBase() {
        Legs legs =
                new Legs("Leggins", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", LegsType.LEGGINGS);
        int id = legsDataSource.addLegs(legs);
        assertTrue(legsDataSource.deleteLegs(id));
    }

    public void testUpdateLegsToDatabase() {
        int id = 0;
        try {
            Legs legs =
                    new Legs("Leggins", Season.AUTUMN, Colors.BLUE,
                            "/NULL", "No hay descripción disponible", LegsType.LEGGINGS);
            id = legsDataSource.addLegs(legs);
            Legs foundLegs = legsDataSource.getLegs(id);
            legs.setDescription("Nueva Descripción");
            legsDataSource.updateLegs(legs);
            Legs foundUpdatedLegs = legsDataSource.getLegs(id);
            assertEquals(foundLegs, foundUpdatedLegs);

        } finally {
            legsDataSource.deleteLegs(id);

        }

    }

    public void tearDown() throws Exception {
        legsDataSource.close();
        super.tearDown();
    }
}
