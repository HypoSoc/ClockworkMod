package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StasisPower extends AbstractClockworkPower implements DamageConditionalGivePower {
    private static final String POWER_ID = getID("Stasis");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/stasis.png"));

    public StasisPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();

        this.priority = 100;

        this.isTurnBased = true;
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
        }
        else {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2]);
        }
    }

    @Override
    public void atEndOfRound()
    {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type)
    {
        return 0.0F;
    }

    @Override
    public float atDamageConditionalGive(float damage, AbstractMonster mo, DamageInfo.DamageType type) {
        return damage;
    }

    @Override
    public float atFinalDamageConditionalGive(float damage, AbstractMonster mo, DamageInfo.DamageType type) {
        if(mo != null) {
            return 0;
        }
        return damage;
    }
}
