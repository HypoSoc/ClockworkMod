package clockworkmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static clockworkmod.patches.TagEnum.RANDOMIZATION_WORKAROUND;

@SpirePatch(
        clz= GameActionManager.class,
        method="getNextAction"
)
public class RandomizationWorkaroundPatch
{
    @SpireInsertPatch(
            rloc=112
    )
    public static SpireReturn infix(GameActionManager gameActionManager)
    {
        CardQueueItem top = gameActionManager.cardQueue.get(0);
        if(top.card.target == RANDOMIZATION_WORKAROUND){
            top.card.target = AbstractCard.CardTarget.ENEMY;
            if (!((top.card.exhaustOnUseOnce) || (top.card.exhaust)) && (top.monster == null || top.monster.isDying)){
                top.card.freeToPlayOnce = false;
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(top.card, 1));
                gameActionManager.cardQueue.remove(0);
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
