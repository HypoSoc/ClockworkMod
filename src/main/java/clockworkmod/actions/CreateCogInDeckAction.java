package clockworkmod.actions;

import clockworkmod.ClockworkMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CreateCogInDeckAction extends AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.35F;
    private static final float PADDING = 25.0F * Settings.scale;

    private boolean upgrade;

    public CreateCogInDeckAction()
    {
        this(1, false);
    }

    public CreateCogInDeckAction(int amount)
    {
        this(amount, false);
    }

    public CreateCogInDeckAction(int amount, boolean upgrade)
    {
        this.amount = amount;
        this.upgrade = upgrade;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = DURATION_PER_CARD;
    }

    public void update()
    {
        if (this.amount == 0)
        {
            this.isDone = true;
            return;
        }
        for(int i=0; i < this.amount; i++){
            AbstractDungeon.actionManager.addToBottom(
                    new MakeTempCardInDrawPileAction(ClockworkMod.cog(this.upgrade), 1, true, true));
        }
        this.isDone = true;
    }
}
