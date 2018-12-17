package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.DelayedPowerTriggerAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ZeroPointEnergyPower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("ZeroPointEnergy");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/zero_point_energy.png"));

    public ZeroPointEnergyPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 1;
        this.updateDescription();
    }

    public void updateDescription()
    {
        StringBuilder sb = new StringBuilder(strings.DESCRIPTIONS[0]);
        for(int i=0; i<this.amount; i++){
            sb.append(strings.DESCRIPTIONS[1]);
        }
        sb.append(strings.DESCRIPTIONS[2]);

        this.description = sb.toString();
    }

    @Override
    public void onSpecificTrigger(){
        if(this.owner.hasPower(StasisPower.POWER_ID)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new DelayedPowerTriggerAction(this));
    }
}
