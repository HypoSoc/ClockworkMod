package clockworkmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ScourAction extends AbstractGameAction {

    private AbstractCard.CardType type;

    public ScourAction(AbstractCard.CardType type) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.type = type;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE){
                this.isDone = true;
            }
            else if(AbstractDungeon.player.drawPile.isEmpty()){
                if(AbstractDungeon.player.discardPile.isEmpty()){
                    this.isDone = true;
                } else {
                    AbstractDungeon.actionManager.addToTop(new ScourAction(type));
                    AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                }
            }
            else{
                if(AbstractDungeon.player.drawPile.getTopCard().type == type){
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                } else {
                    AbstractDungeon.actionManager.addToTop(new ScourAction(type));
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                }
            }
        }
        tickDuration();
    }
}
