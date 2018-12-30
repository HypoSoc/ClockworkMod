package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RitualDaggerMk0Action
        extends AbstractGameAction
{
    private int increaseAmount;
    private DamageInfo info;
    private AbstractCard card;

    public RitualDaggerMk0Action(AbstractCreature target, DamageInfo info, int incAmount, AbstractCard card)
    {
        this.info = info;
        setValues(target, info);
        this.increaseAmount = incAmount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.card = card;
    }

    public void update()
    {
        if ((this.duration == 0.1F) && (this.target != null))
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            this.target.damage(this.info);
            if (((this.target.isDying) || (this.target.currentHealth <= 0)) && (!this.target.halfDead) &&
                    (!this.target.hasPower("Minion")))
            {
                AbstractDungeon.actionManager.addToTop(new IncreaseTinkerAction(this.card, this.increaseAmount));
            }
        }
        tickDuration();
    }
}


