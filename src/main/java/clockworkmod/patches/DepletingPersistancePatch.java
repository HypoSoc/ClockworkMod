package clockworkmod.patches;

import clockworkmod.fields.DepletingField;
import clockworkmod.variables.DepletingVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method="makeStatEquivalentCopy"
)
public class DepletingPersistancePatch {
    @SpireInsertPatch(
            rloc=1,
            localvars = {"card"}
    )
    public static SpireReturn Insert(AbstractCard abstractCard, AbstractCard card) {
        DepletingVariable.setValue(card, DepletingField.DepletingFields.depleting.get(abstractCard));
        return SpireReturn.Continue();
    }
}
