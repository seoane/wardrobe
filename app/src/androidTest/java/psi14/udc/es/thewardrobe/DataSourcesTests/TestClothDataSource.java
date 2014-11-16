package psi14.udc.es.thewardrobe.DataSourcesTests;

import android.test.AndroidTestCase;

import psi14.udc.es.thewardrobe.ControlLayer.Cloth;
import psi14.udc.es.thewardrobe.DataSources.ClothDataSource;
import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

public class TestClothDataSource extends AndroidTestCase {
    ClothDataSource clothDataSource;

    protected void setUp() throws Exception {

        clothDataSource = ClothDataSource.getInstance(getContext());
    }

    public void testAddAndFindToDataBase() {
        Integer id = 0;
        try {
            Cloth cloth =
                    new Cloth("Camiseta de manga corta",
                            BodyParts.CHEST,
                            "T_SHIRT",
                            Season.AUTUMN,
                            Colors.BLUE,
                            "No hay descripción disponible",
                            "/path/to/picture.jpg");
            id = clothDataSource.addCloth(cloth);
            Cloth foundCloth = clothDataSource.getCloth(id);
            assertEquals(cloth, foundCloth);
        } finally {
            clothDataSource.deleteCloth(id);
        }
    }

    public void testDeleteFromDataBase() {
        Cloth cloth =
                new Cloth("Camiseta de manga corta",
                        BodyParts.CHEST,
                        "T_SHIRT",
                        Season.AUTUMN,
                        Colors.BLUE,
                        "No hay descripción disponible",
                        "/path/to/picture.jpg");
        Integer id = clothDataSource.addCloth(cloth);
        assertTrue(clothDataSource.deleteCloth(id));
    }

    public void testUpdateChestToDatabase() {
        Integer id = 0;
        try {
            Cloth cloth =
                    new Cloth("Camiseta de manga corta",
                            BodyParts.CHEST,
                            "T_SHIRT",
                            Season.AUTUMN,
                            Colors.BLUE,
                            "No hay descripción disponible",
                            "/path/to/picture.jpg");
            id = clothDataSource.addCloth(cloth);
            Cloth foundCloth = clothDataSource.getCloth(id);
            cloth.setDescription("Nueva Descripción");
            clothDataSource.updateCloth(cloth);
            Cloth foundUpdatedChest = clothDataSource.getCloth(id);
            assertEquals(foundCloth, foundUpdatedChest);
        } finally {
            clothDataSource.deleteCloth(id);
        }

    }

    public void tearDown() throws Exception {
        clothDataSource.close();
        super.tearDown();
    }
}
