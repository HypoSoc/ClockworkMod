package clockworkmod.actions;

import clockworkmod.cards.AbstractClockworkCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class TinkerAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static String[] TEXT = {"Improve."};
    private ArrayList<AbstractCard> untinkerableCards = new ArrayList<>();
    private int amount;

    public TinkerAction(AbstractPlayer p){
        this(p, 1);
    }

    public TinkerAction(AbstractPlayer p, int amount){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    private boolean isTinkerable(AbstractCard c){
        if(c instanceof AbstractClockworkCard){
            if(c.baseBlock > 0 || c.baseDamage > 0){
                return true;
            }
        }
        return false;
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isTinkerable(c)) {
                    this.untinkerableCards.add(c);
                }
            }
            if (this.p.hand.size() == this.untinkerableCards.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() - this.untinkerableCards.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isTinkerable(c)) {
                        AbstractDungeon.actionManager.addToBottom(
                                new IncreaseTinkerAction(c, this.amount));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.untinkerableCards);
            if (this.p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1)
            {
                AbstractDungeon.actionManager.addToBottom(
                        new IncreaseTinkerAction(this.p.hand.getTopCard(), this.amount));
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            returnCards();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToBottom(
                        new IncreaseTinkerAction(c, this.amount));
                p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.untinkerableCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
