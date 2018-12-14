package clockworkmod.cards;

import basemod.abstracts.CustomCard;
import clockworkmod.ClockworkMod;
import clockworkmod.patches.AbstractCardEnum;

public abstract class AbstractClockworkCard extends CustomCard {
    static String getID(String ID){
        return "Clockwork:"+ID;
    }

    public int momentumIncrementor;

    AbstractClockworkCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                               CardRarity rarity, CardTarget target) {
        super(id, name, ClockworkMod.getResourcePath(img), cost, rawDescription, type,
                AbstractCardEnum.CLOCKWORK, rarity, target);
        momentumIncrementor = 0;
    }
}

