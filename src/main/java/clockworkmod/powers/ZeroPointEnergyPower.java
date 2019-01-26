package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ZeroPointEnergyPower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("ZeroPointEnergy");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/zero_point_energy.png"));

    private boolean pacifist;

    public ZeroPointEnergyPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.priority = 1;
        this.updateDescription();
        this.pacifist = true;
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
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        ClockworkMod.logger.debug("!!!");
        if(target != this.owner && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0){
            this.pacifist = false;
            ClockworkMod.logger.debug("???");
        }
    }

    @Override
    public void atStartOfTurn() {
        ClockworkMod.logger.debug("...");
        if(this.pacifist) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
        this.pacifist = true;
    }
}
