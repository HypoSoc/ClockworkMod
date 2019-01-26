package clockworkmod.actions;

import clockworkmod.cards.QuantumStrike;
import clockworkmod.fields.DepletingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class CloneAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static String[] TEXT = {"Clone."};
    private ArrayList<AbstractCard> quantumLockedCards = new ArrayList<>();
    private int amount;

    public CloneAction(AbstractPlayer p, int amount){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    private boolean isCloneable(AbstractCard c){
        if(FleetingField.fleeting.get(c) ||
                DepletingField.DepletingFields.baseDepleting.get(c) != -1 ||
                ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c) != -1){
            return false;
        }
        return true;
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.p.hand.group) {
                if (!isCloneable(c)) {
                    this.quantumLockedCards.add(c);
                }
            }
            if (this.p.hand.size() == this.quantumLockedCards.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() - this.quantumLockedCards.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isCloneable(c)) {
                        act(c);
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.quantumLockedCards);
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
                act(c);
                p.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.quantumLockedCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }

    private void act(AbstractCard card) {
        for(int i=0;i<this.amount;i++){
            AbstractCard c = card.makeSameInstanceOf();
            if(c instanceof QuantumStrike){
                c.rawDescription = QuantumStrike.strings.EXTENDED_DESCRIPTION[0];
                c.initializeDescription();
            }
            AbstractDungeon.effectList.add(
                    new ShowCardAndAddToDrawPileEffect(c, true, false));
        }
    }
}
