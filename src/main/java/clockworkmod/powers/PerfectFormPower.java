package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.PerfectAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class PerfectFormPower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("PerfectForm");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/perfect_form.png"));

    private int reduction;
    private int momentum;

    public PerfectFormPower(AbstractCreature owner, int amount, int reduction, int momentum) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.reduction = reduction;
        this.momentum = momentum;
        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = strings.DESCRIPTIONS[0] + this.reduction + strings.DESCRIPTIONS[1];
        } else{
            this.description = strings.DESCRIPTIONS[2] + this.amount + strings.DESCRIPTIONS[3] +
                this.reduction + strings.DESCRIPTIONS[4];
        }
        this.description += (this.momentum + strings.DESCRIPTIONS[5]);
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new PerfectAction((AbstractPlayer)this.owner, amount, reduction, momentum));
    }
}