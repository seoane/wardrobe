package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;


public class Cloth {
    Integer id=null;
    String name;
    BodyParts bodyPart;
    String type;
    Season season;
    Colors color;
    String description;
    String uri;

    public Cloth(Integer id, String name, BodyParts bodyPart, String type, Season season, Colors color, String description, String uri) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.type = type;
        this.season = season;
        this.color = color;
        this.description = description;
        this.uri = uri;
    }

    public Cloth(String name, BodyParts bodyPart, String type, Season season, Colors color, String description, String uri) {
        this.name = name;
        this.bodyPart = bodyPart;
        this.type = type;
        this.season = season;
        this.color = color;
        this.description = description;
        this.uri = uri;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BodyParts getBodyPart() {
        return bodyPart;
    }

    public String getType() {
        return type;
    }

    public Season getSeason() {
        return season;
    }

    public Colors getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBodyPart(BodyParts bodyPart) {
        this.bodyPart = bodyPart;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bodyPart=" + bodyPart +
                ", type='" + type + '\'' +
                ", season=" + season +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cloth cloth = (Cloth) o;

        if (bodyPart != cloth.bodyPart) return false;
        if (color != cloth.color) return false;
        if (!description.equals(cloth.description)) return false;
        if (id != null ? !id.equals(cloth.id) : cloth.id != null) return false;
        if (!name.equals(cloth.name)) return false;
        if (season != cloth.season) return false;
        if (!type.equals(cloth.type)) return false;
        if (!uri.equals(cloth.uri)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + bodyPart.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + season.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + uri.hashCode();
        return result;
    }
}
