package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
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
