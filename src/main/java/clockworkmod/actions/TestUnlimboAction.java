package clockworkmod.actions;


import clockworkmod.ClockworkMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class TestUnlimboAction
        extends AbstractGameAction
{
    private AbstractCard card;
    private boolean exhaust;

    public TestUnlimboAction(AbstractCard card, boolean exhaust)
    {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.card = card;
        this.exhaust = exhaust;
    }

    public TestUnlimboAction(AbstractCard card)
    {
        this(card, false);
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_XFAST)
        {
            if (!this.exhaust) {}
            AbstractDungeon.player.limbo.removeCard(this.card);
            if (this.exhaust) {
                ClockworkMod.logger.debug("HOW????");
                AbstractDungeon.effectList.add(new ExhaustCardEffect(this.card));
            }
            this.isDone = true;
        }
    }
}