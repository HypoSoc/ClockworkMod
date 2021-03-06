package clockworkmod.actions;

import clockworkmod.relics.BeatingHeart;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class KaliMaAction extends AbstractGameAction
{
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public KaliMaAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = DURATION;
    }

    public void update()
    {
        if ((this.duration == DURATION) &&
                (this.target != null))
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));

            this.target.damage(this.info);
            if (((((AbstractMonster)this.target).isDying) || (this.target.currentHealth <= 0)) && (!this.target.halfDead) &&
                    ((((AbstractMonster)this.target).type == AbstractMonster.EnemyType.ELITE) ||
                            (((AbstractMonster)this.target).type == AbstractMonster.EnemyType.BOSS)))
            {
                AbstractDungeon.actionManager.addToTop(new GainRelicAction(new BeatingHeart()));
            }
        }
        tickDuration();
    }
}
