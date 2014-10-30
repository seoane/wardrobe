package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
public class Legs extends Cloth {
    LegsType legsType;

    protected Legs(String name, Season season, Colors color, String photographyPath, String description) {
        super(name, season, color, photographyPath, description);
    }

    public Legs() {
        super();
    }

    public LegsType getLegsType() {
        return legsType;
    }

    public void setLegsType(ChestType chestType) {
        this.legsType = legsType;
    }

    @Override
    public String toString() {
        return "Legs{" + super.toString() +
                ", legsType=" + legsType +
                '}';
    }
}
