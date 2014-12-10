package psi14.udc.es.thewardrobe.ControlLayer;

/**
 * Created by Sokun on 10/12/14.
 */
public class Combination {
    /*
    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
    COMB_NAME + " TEXT NOT NULL," +
    CHEST_ID +  " INTEGER NOT NULL," +
    LEGS_ID + " INTEGER NOT NULL," +
    FEET_ID + " INTEGER NOT NULL," +*/
    Integer id = null;
    String name;
    int chestId;
    int legsId;
    int feetId;

    public Combination(Integer id, String name, int chestId, int legsId, int feetId) {
        this.id = id;
        this.name = name;
        this.chestId = chestId;
        this.legsId = legsId;
        this.feetId = feetId;
    }

    public Combination(String name, int chestId, int legsId, int feetId) {
        this.name = name;
        this.chestId = chestId;
        this.legsId = legsId;
        this.feetId = feetId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getChestId() {
        return chestId;
    }

    public int getLegsId() {
        return legsId;
    }

    public int getFeetId() {
        return feetId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChestId(int chestId) {
        this.chestId = chestId;
    }

    public void setLegsId(int legsId) {
        this.legsId = legsId;
    }

    public void setFeetId(int feetId) {
        this.feetId = feetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combination that = (Combination) o;

        if (chestId != that.chestId) return false;
        if (feetId != that.feetId) return false;
        if (legsId != that.legsId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + chestId;
        result = 31 * result + legsId;
        result = 31 * result + feetId;
        return result;
    }

    @Override
    public String toString() {
        return "Combination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chestId=" + chestId +
                ", legsId=" + legsId +
                ", feetId=" + feetId +
                '}';
    }
}
