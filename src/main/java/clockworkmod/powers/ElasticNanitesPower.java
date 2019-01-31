package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.BufferPower;

public class ElasticNanitesPower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("ElasticNanites");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/elastic_nanites.png"));

    private static final int TURNS = 4;
    private int buffer;

    public ElasticNanitesPower(AbstractCreature owner, int buffer) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = TURNS;
        this.buffer = buffer;

        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.buffer);
        } else{
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2] + this.buffer);
        }
        this.description += strings.DESCRIPTIONS[3];
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.buffer += stackAmount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        this.amount -= 1;
        if (this.amount == 0)
        {
            flash();
            this.amount = TURNS;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                    new BufferPower(this.owner, buffer), buffer));
        }
        updateDescription();
    }
}
