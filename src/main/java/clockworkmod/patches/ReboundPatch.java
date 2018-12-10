package clockworkmod.patches;

import clockworkmod.fields.ReboundField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz= UseCardAction.class,
        method=SpirePatch.CONSTRUCTOR,
        paramtypez={
                AbstractCard.class,
                AbstractCreature.class
        }
)
public class ReboundPatch {
    @SpirePostfixPatch
    public static void Postfix(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
        int reboundValue = ReboundField.rebound.get(card);
        if(reboundValue == ReboundField.ALWAYS_REBOUND){
            __instance.reboundCard = true;
        }
        else if(reboundValue == ReboundField.REBOUND_EXACTLY_ONCE){
            ReboundField.rebound.set(card, ReboundField.NO_REBOUND);
            __instance.reboundCard = true;
        }
        else if(reboundValue >= ReboundField.REBOUND_ONCE_PER_TURN){
            if(reboundValue < GameActionManager.turn){
                ReboundField.rebound.set(card, GameActionManager.turn);
                __instance.reboundCard = true;
            }
        }
    }
}
