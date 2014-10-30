package psi14.udc.es.thewardrobe.ControlLayer;

import psi14.udc.es.thewardrobe.Utils.Colors;
import psi14.udc.es.thewardrobe.Utils.LegsType;
import psi14.udc.es.thewardrobe.Utils.Season;

/**
 * Created by Sokun on 29/10/14.
 */
public class Legs extends Cloth {
    LegsType legsType;

    protected Legs(String name, Season season, Colors color, String photographyPath, String description) {
        super(name, season, color, photographyPath, description);
    }
}
