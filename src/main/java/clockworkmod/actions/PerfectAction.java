package clockworkmod.actions;

import clockworkmod.cards.AbstractClockworkCard;
import clockworkmod.cards.Randomization;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.ArrayList;

public class PerfectAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static String[] TEXT = {"Perfect."};
    private ArrayList<AbstractCard> perfectCards = new ArrayList<>();
    private int amount;
    private int reduction;
    private int momentum;

    public PerfectAction(AbstractPlayer p, int amount, int reduction, int momentum){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.reduction = reduction;
        this.momentum = momentum;
    }

    private boolean isImperfect(AbstractCard c){
        if(c.type == AbstractCard.CardType.STATUS || c.type == AbstractCard.CardType.CURSE){
            return false;
        }
        return (c.costForTurn > 0) || (c.canUpgrade()) || (c.baseBlock > 0) || (c.baseDamage > 0) || (c instanceof Randomization);
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isImperfect(c)) {
                    this.perfectCards.add(c);
                }
            }
            if (this.p.hand.size() == this.perfectCards.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() - this.perfectCards.size() <= this.amount) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isImperfect(c)) {
                        applyEffect(c);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.perfectCards);
            if (this.p.hand.group.size() > this.amount)
            {
                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0], this.amount, true, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1)
            {
                applyEffect(this.p.hand.getTopCard());
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            returnCards();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                applyEffect(c);
                p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.perfectCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }

    private void applyEffect(AbstractCard card){
        card.superFlash();
        if(card.costForTurn > 0) {
            card.modifyCostForCombat(-this.reduction);
        }
        if(card.canUpgrade()){
            card.upgrade();
        }
        if (card.baseDamage > 0) {
            card.baseDamage += this.momentum;
        }
        if (card.baseBlock > 0) {
            card.baseBlock += this.momentum;
        }
        if(card instanceof AbstractClockworkCard){
            ((AbstractClockworkCard) card).momentumIncrementor += this.momentum;
        }
        if(card instanceof Randomization) {
            card.baseMagicNumber += this.momentum;
            card.magicNumber += this.momentum;
        }
        for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
            if(c != card) {
                if(c.costForTurn > 0) {
                    c.modifyCostForCombat(-this.reduction);
                }
                if(c.canUpgrade()){
                    c.upgrade();
                }
                if (c.baseDamage > 0) {
                    c.baseDamage += this.momentum;
                }
                if (c.baseBlock > 0) {
                    c.baseBlock += this.momentum;
                }
                if(c instanceof AbstractClockworkCard){
                    ((AbstractClockworkCard) c).momentumIncrementor += this.momentum;
                }
                if(c instanceof Randomization) {
                    c.baseMagicNumber += this.momentum;
                    c.magicNumber += this.momentum;
                }
            }
        }
    }
}
