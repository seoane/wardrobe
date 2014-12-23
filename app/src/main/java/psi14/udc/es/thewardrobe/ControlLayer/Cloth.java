package psi14.udc.es.thewardrobe.ControlLayer;

import android.os.Parcel;
import android.os.Parcelable;

import psi14.udc.es.thewardrobe.Utils.BodyParts;
import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.Season;


public class Cloth implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cloth createFromParcel(Parcel in) {
            return new Cloth(in);
        }

        public Cloth[] newArray(int size) {
            return new Cloth[size];
        }
    };
    Integer id = null;
    String name;
    BodyParts bodyPart;
    String type;
    Season season;
    Colors color;
    String description;
    String uri;
    Integer frequency;

    public Cloth(Integer id, String name, BodyParts bodyPart, String type, Season season, Colors color, String description, String uri, Integer frequency) {
        this.id = id;
        this.name = name;
        this.bodyPart = bodyPart;
        this.type = type;
        this.season = season;
        this.color = color;
        this.description = description;
        this.uri = uri;
        this.frequency = frequency;
    }

    public Cloth(String name, BodyParts bodyPart, String type, Season season, Colors color, String description, String uri, Integer frequency) {
        this.name = name;
        this.bodyPart = bodyPart;
        this.type = type;
        this.season = season;
        this.color = color;
        this.description = description;
        this.uri = uri;
        this.frequency = frequency;
    }

    public Cloth(Parcel in) {
        String[] data = new String[8];

        in.readStringArray(data);
        this.id = Integer.getInteger(data[0]);
        this.name = data[1];
        this.bodyPart = BodyParts.valueOf(data[2]);
        this.type = data[3];
        this.season = Season.valueOf(data[4]);
        this.color = Colors.valueOf(data[5]);
        this.description = data[6];
        this.uri = data[7];
        this.frequency = Integer.getInteger(data[8]);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyParts getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyParts bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void use(){
        this.frequency++;
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
                ", frequency='" + frequency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Cloth)) return false;

        Cloth cloth = (Cloth) o;

        if (bodyPart != cloth.bodyPart) return false;
        if (color != cloth.color) return false;
        if (description != null ? !description.equals(cloth.description) : cloth.description != null)
            return false;
        if (frequency != null ? !frequency.equals(cloth.frequency) : cloth.frequency != null)
            return false;
        if (id != null ? !id.equals(cloth.id) : cloth.id != null) return false;
        if (name != null ? !name.equals(cloth.name) : cloth.name != null) return false;
        if (season != cloth.season) return false;
        if (type != null ? !type.equals(cloth.type) : cloth.type != null) return false;
        if (uri != null ? !uri.equals(cloth.uri) : cloth.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bodyPart != null ? bodyPart.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (season != null ? season.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                Integer.toString(this.id),
                this.name,
                this.bodyPart.toString(),
                this.type,
                this.season.toString(),
                this.color.toString(),
                this.description,
                this.uri,
                this.frequency.toString()});
    }
}
