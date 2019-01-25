package clockworkmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class TiltAWhirlAction
        extends AbstractGameAction
{
    public int[] multiDamage;
    private boolean freeToPlayOnce;
    private DamageInfo.DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;

    public TiltAWhirlAction(AbstractPlayer p, int[] multiDamage, DamageInfo.DamageType damageType,
                            boolean upgraded, boolean freeToPlayOnce, int energyOnUse)
    {
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update()
    {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if(this.upgraded){
            effect += 1;
        }
        if (this.p.hasRelic("Chemical X"))
        {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0)
        {
            for (int i = 0; i < effect; i++)
            {
                if (i == 0)
                {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new WhirlwindEffect(), 0.0F));
                }
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.p, new CleaveEffect(), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.p, effect));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
