package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;


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

    public void setLegsType(LegsType legsType) {
        this.legsType = legsType;
    }

    @Override
    public String toString() {
        return "Legs{" + super.toString() +
                ", legsType=" + legsType +
                '}';
    }
}
