package clockworkmod.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.ArrayList;

public class DownsizeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static String[] TEXT = {"Improve."};
    private ArrayList<AbstractCard> unpurgableCards = new ArrayList<>();

    private boolean isUpgraded;

    public DownsizeAction(AbstractPlayer p, boolean isUpgraded){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isUpgraded = isUpgraded;
    }

    private boolean isPurgable(AbstractCard c){
        return (c.type != AbstractCard.CardType.CURSE);
    }

    private void act(AbstractCard card){
        if(this.isUpgraded){
            for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)){
                FleetingField.fleeting.set(c, true);
                if(c.costForTurn > 0) {
                    c.costForTurn = 0;
                    c.isCostModifiedForTurn = true;
                    c.cost = 0;
                    c.isCostModified = true;
                    c.name = "Fleeting: " + c.name;
                }
            }
        }
        else {
            AbstractDungeon.actionManager.addToBottom(
                    new PurgeAction(card));
        }
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isPurgable(c)) {
                    this.unpurgableCards.add(c);
                }
            }
            if (this.p.hand.size() == this.unpurgableCards.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() - this.unpurgableCards.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isPurgable(c)) {
                        act(c);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.unpurgableCards);
            if (this.p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1)
            {
                act(this.p.hand.getTopCard());
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            returnCards();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.addToTop(c);
                act(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.unpurgableCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
