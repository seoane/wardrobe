package psi14.udc.es.thewardrobe.DataSourcesTests;

import android.test.AndroidTestCase;

import psi14.udc.es.thewardrobe.ControlLayer.Chest;
import psi14.udc.es.thewardrobe.DataSources.ChestDataSource;
import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 06/11/14.
 */
public class TestChestDataSource extends AndroidTestCase {
    ChestDataSource chestDataSource;

    protected void setUp() throws Exception {

        chestDataSource = ChestDataSource.getInstance(getContext());
    }

    public void testAddAndFindToDataBase() {
        Chest chest =
                new Chest("Camiseta de manga corta", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", ChestType.BLOUSES);
        int id = chestDataSource.addChest(chest);
        Chest foundChest = chestDataSource.getChest(id);
        assertEquals(chest, foundChest);
    }

    public void testDeleteFromDataBase() {
        Chest chest =
                new Chest("Camiseta de manga corta", Season.AUTUMN, Colors.BLUE,
                        "/NULL", "No hay descripción disponible", ChestType.BLOUSES);
        int id = chestDataSource.addChest(chest);
        assertTrue(chestDataSource.deleteChest(id));
    }

    public void tearDown() throws Exception {
        chestDataSource.close();
        super.tearDown();
    }
}
