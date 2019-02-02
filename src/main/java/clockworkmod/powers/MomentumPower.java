package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.cards.AbstractClockworkCard;
import clockworkmod.cards.Randomization;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.time.Clock;

public class MomentumPower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("Momentum");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/momentum.png"));
    private static final Texture IMG2 = new Texture(ClockworkMod.getResourcePath("powers/momentum2.png"));

    private boolean left = true;

    public MomentumPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    @Override
    public void atStartOfTurn() {
        this.left = true;
        this.img = IMG;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        for (AbstractCard c : GetAllInBattleInstances.get(card.uuid))
        {
            if (c.baseDamage > 0) {
                c.baseDamage += this.amount;
            }
            if (c.baseBlock > 0) {
                c.baseBlock += this.amount;
            }
            if(c instanceof AbstractClockworkCard){
                ((AbstractClockworkCard) c).momentumIncrementor += this.amount;
            }
            if(c instanceof Randomization) {
                c.baseMagicNumber += this.amount;
                c.magicNumber += this.amount;
            }
        }
        if(this.left){
            this.left = false;
            this.img = IMG2;
            if(ClockworkMod.tick_tock)
                ClockworkMod.TICK.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * 120);
        }
        else {
            this.left = true;
            this.img = IMG;
            if(ClockworkMod.tick_tock)
                ClockworkMod.TOCK.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * 120);
        }
    }
}
