package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;


public abstract class Cloth {
    String name;
    Season season;
    Colors color;
    String photographyPath;
    String description;

    protected Cloth(String name, Season season, Colors color, String photographyPath, String description) {
        this.name = name;
        this.season = season;
        this.color = color;
        this.photographyPath = photographyPath;
        this.description = description;
    }

    protected Cloth() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cloth cloth = (Cloth) o;

        if (color != cloth.color) return false;
        if (description != null ? !description.equals(cloth.description) : cloth.description != null)
            return false;
        if (name != null ? !name.equals(cloth.name) : cloth.name != null) return false;
        if (photographyPath != null ? !photographyPath.equals(cloth.photographyPath) : cloth.photographyPath != null)
            return false;
        if (season != cloth.season) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (season != null ? season.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (photographyPath != null ? photographyPath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public String getPhotographyPath() {
        return photographyPath;
    }

    public void setPhotographyPath(String photographyPath) {
        this.photographyPath = photographyPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", season='" + season + '\'' +
                ", color=" + color +
                ", photographyPath='" + photographyPath + '\'' +
                ", description='" + description + '\'';
    }
}
