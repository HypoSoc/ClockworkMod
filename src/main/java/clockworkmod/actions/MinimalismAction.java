package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static java.lang.Math.min;

public class MinimalismAction extends AbstractGameAction{

    private int exhaustAmount;

    public MinimalismAction(int exhaustAmount){
        this.duration = 0.0F;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.exhaustAmount = exhaustAmount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }
        int realExhaustAmount = min(exhaustAmount, AbstractDungeon.player.drawPile.size());
        for(int i=0; i<realExhaustAmount;i++){
            AbstractCard c = AbstractDungeon.player.drawPile.getNCardFromTop(i);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
        }
        this.isDone = true;
    }
}
