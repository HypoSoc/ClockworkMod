package clockworkmod.patches;

import clockworkmod.fields.DepletingField;
import clockworkmod.variables.DepletingVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(
        clz=AbstractPlayer.class,
        method="useCard"
)
public class DepletingPatch
{
    public static void Prefix(AbstractPlayer p, AbstractCard c, AbstractMonster monster, int energyOnUse)
    {
        if (DepletingField.DepletingFields.depleting.get(c) > -1) {
            DepletingVariable.increment(c);
        }
    }
}
