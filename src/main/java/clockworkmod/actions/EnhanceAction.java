package clockworkmod.actions;

import clockworkmod.cards.AbstractClockworkCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnhanceAction extends AbstractGameAction{

    private int enhanceAmount;

    public EnhanceAction(int enhanceAmount){
        this.duration = 0.0F;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.enhanceAmount = enhanceAmount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }
        AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
        if(c.type != AbstractCard.CardType.STATUS
                && c.type != AbstractCard.CardType.CURSE) {
            if (c.baseDamage > 0) {
                c.baseDamage += this.enhanceAmount;
            }
            if (c.baseBlock > 0) {
                c.baseBlock += this.enhanceAmount;
            }
            if (c instanceof AbstractClockworkCard) {
                ((AbstractClockworkCard) c).momentumIncrementor += this.enhanceAmount;
            }
        }
        this.isDone = true;
    }
}
