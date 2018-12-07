package clockworkmod.relics;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;

abstract class AbstractClockworkRelic extends CustomRelic{
    static String getID(String ID){
        return "Clockwork:"+ID;
    }

    AbstractClockworkRelic(String id, String img, RelicTier tier, LandingSound sfx) {
        super(id, new Texture(ClockworkMod.getResourcePath(img)), tier, sfx);
    }

}

