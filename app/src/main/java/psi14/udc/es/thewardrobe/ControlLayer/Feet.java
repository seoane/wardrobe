package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.FeetType;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
public class Feet extends Cloth {
    FeetType feetType;

    protected Feet(String name, Season season, Colors color, String photographyPath, String description) {
        super(name, season, color, photographyPath, description);
    }

    public Feet(){
        super();
    }

    public FeetType getFeetType() {
        return feetType;
    }

    public void setFeetType(FeetType feetType) {
        this.feetType = feetType;
    }

    @Override
    public String toString() {
        return "Feet{" + super.toString() +
                ", feetType=" + feetType +
                '}';
    }
}
