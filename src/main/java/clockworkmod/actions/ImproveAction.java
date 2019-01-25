package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class ImproveAction extends AbstractGameAction{

    private int costReduction;

    public ImproveAction() {
        this(0);
    }
    public ImproveAction(int costReduction){
        this.duration = 0.0F;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.costReduction = costReduction;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }
        AbstractCard baseCard = AbstractDungeon.player.drawPile.getTopCard();

        for (AbstractCard c : GetAllInBattleInstances.get(baseCard.uuid)) {
            if(c.upgraded) {
                c.modifyCostForCombat(-costReduction);
            }
            c.upgrade();
        }

        this.isDone = true;
    }
}
