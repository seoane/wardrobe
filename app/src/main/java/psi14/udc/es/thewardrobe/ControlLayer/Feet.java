package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.FootwearType;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
public class Feet extends Cloth {
    FootwearType footwearType;

    protected Feet(String name, Season season, Colors color, String photographyPath, String description) {
        super(name, season, color, photographyPath, description);
    }
}
