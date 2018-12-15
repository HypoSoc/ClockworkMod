package clockworkmod.actions;

import clockworkmod.cards.AbstractClockworkCard;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class IncreaseTinkerAction
        extends AbstractGameAction
{
    private int tinkerIncrease;
    private AbstractClockworkCard card;

    public IncreaseTinkerAction(AbstractClockworkCard card, int tinkerIncrease)
    {
        this.tinkerIncrease = tinkerIncrease;

        this.card = card;
    }

    public void update()
    {
        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(this.card);
        if(masterCard != null){
            if(masterCard.baseBlock > 0){
                masterCard.baseBlock += this.tinkerIncrease;
            }
            if(masterCard.baseDamage > 0){
                masterCard.baseDamage += this.tinkerIncrease;
            }
            if(masterCard instanceof AbstractClockworkCard){
                ((AbstractClockworkCard)masterCard).tinkerIncrementor += this.tinkerIncrease;
            }
        }

        for (AbstractCard c : GetAllInBattleInstances.get(this.card.uuid))
        {
            if(c.baseBlock > 0){
                c.baseBlock += this.tinkerIncrease;
            }
            if(c.baseDamage > 0){
                c.baseDamage += this.tinkerIncrease;
            }
            if(c instanceof AbstractClockworkCard){
                ((AbstractClockworkCard)c).tinkerIncrementor += this.tinkerIncrease;
            }
            c.applyPowers();
        }
        this.isDone = true;
    }
}
