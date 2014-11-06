package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;


public class Legs extends Cloth {
    LegsType legsType;

    public Legs(String name, Season season, Colors color, String photographyPath, String description, LegsType legsType) {
        super(name, season, color, photographyPath, description);
        this.legsType = legsType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Legs legs = (Legs) o;

        if (legsType != legs.legsType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (legsType != null ? legsType.hashCode() : 0);
        return result;
    }
}
