package clockworkmod.fields;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

class ArrayListOfIntegers extends ArrayList<Integer> {}

public class DepletingSave implements CustomSavable<ArrayListOfIntegers> {
    @Override
    public ArrayListOfIntegers onSave() {
        ArrayListOfIntegers depletingValues = new ArrayListOfIntegers();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            depletingValues.add(DepletingField.DepletingFields.depleting.get(card));
        }
        return depletingValues;
    }

    @Override
    public void onLoad(ArrayListOfIntegers depletingValues) {
        if(depletingValues != null){
            int i = 0;
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if(i <= depletingValues.size()) {
                    DepletingField.DepletingFields.depleting.set(card, depletingValues.get(i));
                }
                i++;
            }
        }
    }
}
