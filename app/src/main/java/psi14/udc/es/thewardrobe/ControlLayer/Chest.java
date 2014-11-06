package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.ChestType;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;


public class Chest extends Cloth {
    ChestType chestType;

    public Chest(String name, Season season, Colors color, String photographyPath, String description, ChestType chestType) {
        super(name, season, color, photographyPath, description);
        this.chestType = chestType;
    }

    public Chest(int id, String name, Season season, Colors color, String photographyPath, String description, ChestType chestType) {
        super(id, name, season, color, photographyPath, description);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Chest chest = (Chest) o;

        if (chestType != chest.chestType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (chestType != null ? chestType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chest{" + super.toString() +
                ", chestType=" + chestType +
                '}';
    }
}
