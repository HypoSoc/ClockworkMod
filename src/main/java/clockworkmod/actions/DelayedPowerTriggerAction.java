package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DelayedPowerTriggerAction extends AbstractGameAction
{
    private AbstractPower power;

    public DelayedPowerTriggerAction(AbstractPower power)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.power = power;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            for(AbstractGameAction a : AbstractDungeon.actionManager.actions){
                if(!(a instanceof DelayedPowerTriggerAction)){
                    AbstractDungeon.actionManager.addToBottom(new DelayedPowerTriggerAction(power));
                    this.isDone = true;
                    return;
                }
            }
            power.onSpecificTrigger();
        }
        tickDuration();
    }
}
