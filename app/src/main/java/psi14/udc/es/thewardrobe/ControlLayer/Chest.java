package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
public class Chest extends Cloth {
    ChestType chestType;

    public Chest(String name, Season season, Colors color, String photographyPath, String description, ChestType chestType) {
        super(name, season, color, photographyPath, description);
        this.chestType = chestType;
    }

    public Chest() {
        super();
    }

    public ChestType getChestType() {
        return chestType;
    }

    public void setChestType(ChestType chestType) {
        this.chestType = chestType;
    }

    @Override
    public String toString() {
        return "Chest{" + super.toString() +
                "chestType=" + chestType +
                '}';
    }
}
