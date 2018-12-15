package clockworkmod.cards;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import clockworkmod.ClockworkMod;
import clockworkmod.patches.AbstractCardEnum;

import java.lang.reflect.Type;

public abstract class AbstractClockworkCard extends CustomCard implements CustomSavable<Integer> {
    static String getID(String ID){
        return "Clockwork:"+ID;
    }

    public int momentumIncrementor; // For cards like Perfected Strike that modify their own baseDamage/Block
    public int tinkerIncrementor; //Permanent increases to damage/block

    AbstractClockworkCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                               CardRarity rarity, CardTarget target) {
        super(id, name, ClockworkMod.getResourcePath(img), cost, rawDescription, type,
                AbstractCardEnum.CLOCKWORK, rarity, target);
        momentumIncrementor = 0;
        tinkerIncrementor = 0;
    }

    @Override
    public Integer onSave(){
        return tinkerIncrementor;
    }

    @Override
    public void onLoad(Integer i){
        tinkerIncrementor = i;
        if(this.baseBlock > 0){
            baseBlock += tinkerIncrementor;
        }
        if(this.baseDamage > 0){
            baseDamage += tinkerIncrementor;
        }
    }

    @Override
    public Type savedType(){
        return Integer.class;
    }
}

