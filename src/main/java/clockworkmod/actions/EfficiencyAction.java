package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class EfficiencyAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static String[] TEXT = {"Improve."};
    private ArrayList<AbstractCard> irreducibleCards = new ArrayList<>();
    private int amount;

    public EfficiencyAction(AbstractPlayer p, int amount){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    private boolean isReducible(AbstractCard c){
        return c.costForTurn > 0;
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isReducible(c)) {
                    this.irreducibleCards.add(c);
                }
            }
            if (this.p.hand.size() == this.irreducibleCards.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() - this.irreducibleCards.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isReducible(c)) {
                        c.modifyCostForCombat(-amount);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.irreducibleCards);
            if (this.p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1)
            {
                this.p.hand.getTopCard().modifyCostForCombat(-amount);
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            returnCards();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.modifyCostForCombat(-amount);
                p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.irreducibleCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
