package clockworkmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelectivityAction extends AbstractGameAction
{
    public SelectivityAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            int theSize = AbstractDungeon.player.hand.size();
            AbstractDungeon.actionManager.addToBottom(
                    new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
            AbstractDungeon.actionManager.addToBottom(
                    new FetchAction(AbstractDungeon.player.drawPile, theSize));
        }
        tickDuration();
    }
}
