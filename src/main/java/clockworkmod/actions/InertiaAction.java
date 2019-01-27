package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InertiaAction extends AbstractGameAction{

    private int number;

    public InertiaAction(int number){
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.number = number;
    }

    @Override
    public void update() {
        int drawAmount = AbstractDungeon.player.currentBlock / number;
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, drawAmount));
        this.isDone = true;
    }
}
