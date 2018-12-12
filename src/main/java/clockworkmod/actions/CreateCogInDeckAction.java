package clockworkmod.actions;

import clockworkmod.ClockworkMod;
import clockworkmod.cards.DefectiveCog;
import clockworkmod.cards.HelicalCog;
import clockworkmod.cards.SpurCog;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    private AbstractCard cog(){
        AbstractCard cog = (new SpurCog()).makeCopy();
        int v = AbstractDungeon.cardRandomRng.random(4);
        ClockworkMod.logger.debug(v);
        if(v == 0 || v == 3) {
            cog = (new SpurCog()).makeCopy();
        }
        else if (v == 1 || v == 4){
            cog = (new HelicalCog()).makeCopy();
        } else if (v == 2) {
            cog = (new DefectiveCog()).makeCopy();
        }
        if(this.upgrade){
            cog.upgrade();
        }
        return cog;
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
                    new MakeTempCardInDrawPileAction(cog(), 1, true, true));
        }
        this.isDone = true;
    }
}
