package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GainRelicAction extends AbstractGameAction
{
    private AbstractRelic relic;
    private static final float DURATION = 0.1F;

    public GainRelicAction(AbstractRelic r)
    {
        this.relic = r.makeCopy();
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }

    public void update()
    {
        if (this.duration == 0.1F)
        {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f,
                    Settings.HEIGHT / 2.0f, this.relic);
        }
        tickDuration();
    }
}
