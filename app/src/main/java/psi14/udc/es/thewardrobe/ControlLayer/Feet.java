package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.FeetType;
import psi14.udc.es.thewardrobe.Utils.Season;


public class Feet extends Cloth {
    FeetType feetType;

    public Feet(String name, Season season, Colors color, String photographyPath, String description, FeetType feetType) {
        super(name, season, color, photographyPath, description);
        this.feetType = feetType;
    }

    public Feet(int id, String name, Season season, Colors color, String photographyPath, String description, FeetType feetType) {
        super(id, name, season, color, photographyPath, description);
        this.feetType = feetType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Feet feet = (Feet) o;

        if (feetType != feet.feetType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (feetType != null ? feetType.hashCode() : 0);
        return result;
    }
}
