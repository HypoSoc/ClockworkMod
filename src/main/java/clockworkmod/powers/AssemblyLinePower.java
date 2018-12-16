package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.CreateCogInDeckAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class AssemblyLinePower extends AbstractClockworkPower {
    private static final String POWER_ID = getID("AssemblyLine");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/assembly_line.png"));

    public AssemblyLinePower(AbstractCreature owner, int amount) {
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
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
        } else{
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2]);
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new CreateCogInDeckAction(this.amount));
    }
}
