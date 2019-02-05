package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForcePlayCardAction
        extends AbstractGameAction
{
    private AbstractCard card;
    private boolean exhaustCards;

    public ForcePlayCardAction(AbstractCard card, AbstractCreature target, boolean exhausts)
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.card = card;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (!AbstractDungeon.player.hand.contains(card))
            {
                this.isDone = true;
                return;
            }
            else
            {
                AbstractDungeon.player.hand.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = (-200.0F * Settings.scale);
                card.target_x = (Settings.WIDTH / 2.0F + 200.0F * Settings.scale);
                card.target_y = (Settings.HEIGHT / 2.0F);
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                if (!card.canUse(AbstractDungeon.player, (AbstractMonster)this.target))
                {
                    if (this.exhaustCards)
                    {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    }
                    else
                    {
                        AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));

                        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                    }
                }
                else
                {
                    card.applyPowers();
                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    if (!Settings.FAST_MODE) {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                }
            }
            this.isDone = true;
        }
    }
}
