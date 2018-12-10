package clockworkmod.patches;

import clockworkmod.powers.DamageConditionalGivePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
public class DamageConditionalGivePatchCalculate {
    @SpireInsertPatch(
            rlocs=38,
            localvars={"p", "tmp"}
    )
    public static SpireReturn Insert(AbstractCard abstractCard, AbstractMonster mo,
                                     AbstractPower p, @ByRef float[] tmp) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[0];
            tmp[0] = ((DamageConditionalGivePower)p).atDamageConditionalGive(tmpcpy, abstractCard,
                    mo, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=53,
            localvars={"p", "tmp"}
    )
    public static SpireReturn FinalInsert(AbstractCard abstractCard, AbstractMonster mo,
                                          AbstractPower p, @ByRef float[] tmp) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[0];
            tmp[0] = ((DamageConditionalGivePower)p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                    mo, abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rlocs=99,
            localvars={"p", "tmp", "i"}
    )
    public static SpireReturn MultiInsert(AbstractCard abstractCard, AbstractMonster mo,
                                     AbstractPower p, float[] tmp, int i) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[i];
            tmp[i] = ((DamageConditionalGivePower)p).atDamageConditionalGive(tmpcpy, abstractCard,
                    AbstractDungeon.getCurrRoom().monsters.monsters.get(i), abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=118,
            localvars={"p", "tmp", "i"}
    )
    public static SpireReturn MultiFinalInsert(AbstractCard abstractCard, AbstractMonster mo,
                                          AbstractPower p, float[] tmp, int i) {
        if(p instanceof DamageConditionalGivePower) {
            float tmpcpy = tmp[i];
            tmp[i] = ((DamageConditionalGivePower)p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                    AbstractDungeon.getCurrRoom().monsters.monsters.get(i), abstractCard.damageTypeForTurn);
        }
        return SpireReturn.Continue();
    }
}
