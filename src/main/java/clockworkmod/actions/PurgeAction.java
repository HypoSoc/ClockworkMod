package clockworkmod.actions;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class PurgeAction
        extends AbstractGameAction
{
    private AbstractCard card;
    private static final float PURGE_DURATION = 0.2F;

    public PurgeAction(AbstractCard card)
    {
        setValues(AbstractDungeon.player, null, 1);
        this.card = card;
        this.duration = PURGE_DURATION;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if (this.duration == PURGE_DURATION)
        {
            AbstractCard master = StSLib.getMasterDeckEquivalent(this.card);
            if (master != null) {
                AbstractDungeon.player.masterDeck.removeCard(master);
            }
            for (AbstractCard c : GetAllInBattleInstances.get(this.card.uuid))
            {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                if (AbstractDungeon.player.limbo.contains(c)) {
                    AbstractDungeon.player.limbo.removeCard(c);
                }
                if (AbstractDungeon.player.hand.contains(c)) {
                    AbstractDungeon.player.hand.removeCard(c);
                }
                if (AbstractDungeon.player.drawPile.contains(c)) {
                    AbstractDungeon.player.drawPile.removeCard(c);
                }
                if (AbstractDungeon.player.discardPile.contains(c)) {
                    AbstractDungeon.player.discardPile.removeCard(c);
                }
                if (AbstractDungeon.player.exhaustPile.contains(c)) {
                    AbstractDungeon.player.exhaustPile.removeCard(c);
                }
            }
        }
        tickDuration();
    }
}

