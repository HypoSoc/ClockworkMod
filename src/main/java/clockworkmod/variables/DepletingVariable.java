package clockworkmod.variables;

import basemod.abstracts.DynamicVariable;
import clockworkmod.fields.DepletingField;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DepletingVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "clock:dep";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return DepletingField.DepletingFields.depleting.get(card).intValue() !=
                DepletingField.DepletingFields.baseDepleting.get(card).intValue();
    }

    @Override
    public int value(AbstractCard card)
    {
        return DepletingField.DepletingFields.depleting.get(card);
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return DepletingField.DepletingFields.baseDepleting.get(card);
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return DepletingField.DepletingFields.isDepletingUpgraded.get(card);
    }

    public static void upgrade(AbstractCard card, int increase){
        upgrade(card, increase, false);
    }

    public static void upgrade(AbstractCard card, int increase, boolean alsoUpgradeOutOfCombat)
    {
        DepletingField.DepletingFields.isDepletingUpgraded.set(card, true);
        DepletingField.DepletingFields.baseDepleting.set(card,
                DepletingField.DepletingFields.baseDepleting.get(card) + increase);
        DepletingField.DepletingFields.depleting.set(card,
                DepletingField.DepletingFields.depleting.get(card) + increase);
        if(alsoUpgradeOutOfCombat){
            AbstractCard master = StSLib.getMasterDeckEquivalent(card);
            if(!DepletingField.DepletingFields.isDepletingUpgraded.get(master)) {
                upgrade(master, increase);
            }
        }
    }

    public static void setBaseValue(AbstractCard card, int amount)
    {
        DepletingField.DepletingFields.baseDepleting.set(card, amount);
        DepletingField.DepletingFields.depleting.set(card, amount);
        card.initializeDescription();
    }

    public static void setValue(AbstractCard card, int amount)
    {
        DepletingField.DepletingFields.depleting.set(card, amount);
        card.initializeDescription();
    }

    public static void increment(AbstractCard card)
    {
        DepletingField.DepletingFields.depleting.set(card, DepletingField.DepletingFields.depleting.get(card) - 1);
        AbstractCard master = StSLib.getMasterDeckEquivalent(card);
        if(master != null)
            DepletingField.DepletingFields.depleting.set(master, DepletingField.DepletingFields.depleting.get(master) - 1);
        if (DepletingField.DepletingFields.depleting.get(card) <= 0) {
            FleetingField.fleeting.set(card, true);
        }
        card.initializeDescription();
    }

    public static boolean isDepleted(AbstractCard card)
    {
        return DepletingField.DepletingFields.depleting.get(card) <= 0;
    }
}
