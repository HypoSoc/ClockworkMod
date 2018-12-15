package clockworkmod.patches;

import clockworkmod.powers.DamageConditionalGivePower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCard.class, method = "applyPowers")
public class DamageConditionalGivePatchApply {
    @SpireInsertPatch(
            rloc=38,
            localvars={"p", "tmp"}
    )
    public static SpireReturn Insert(AbstractCard abstractCard, AbstractPower p, @ByRef float[] tmp) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[0];
            tmp[0] = ((DamageConditionalGivePower)p).atDamageConditionalGive(tmpcpy, abstractCard,
                    null, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=46,
            localvars={"p", "tmp"}
    )
    public static SpireReturn FinalInsert(AbstractCard abstractCard, AbstractPower p, @ByRef float[] tmp) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[0];
            tmp[0] = ((DamageConditionalGivePower)p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                    null, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rlocs=80,
            localvars={"p", "tmp", "i"}
    )
    public static SpireReturn MultiInsert(AbstractCard abstractCard, AbstractPower p, float[] tmp, int i) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[i];
            tmp[i] = ((DamageConditionalGivePower)p).atDamageConditionalGive(tmpcpy, abstractCard,
                    null, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=90,
            localvars={"p", "tmp", "i"}
    )
    public static SpireReturn MultiFinalInsert(AbstractCard abstractCard, AbstractPower p, float[] tmp, int i) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[i];
            tmp[i] = ((DamageConditionalGivePower)p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                    null, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }
}
