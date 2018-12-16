package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.CreateCogInHandAction;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class UnlimitedCogworksPower extends AbstractClockworkPower implements OnCardDrawPower {
    private static final String POWER_ID = getID("UnlimitedCogworks");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/infinite_cogs.png"));

    private static final int CARDS_TO_DRAW = 7;
    private int cogs;

    public UnlimitedCogworksPower(AbstractCreature owner, int cogs) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = CARDS_TO_DRAW;
        this.cogs = cogs;

        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.cogs);
        } else{
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2] + this.cogs);
        }
        if(this.cogs == 1){
            this.description += strings.DESCRIPTIONS[3];
        } else {
            this.description += strings.DESCRIPTIONS[4];
        }
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.cogs += stackAmount;
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard) {
        this.amount -= 1;
        if (this.amount == 0)
        {
            flash();
            this.amount = CARDS_TO_DRAW;
            AbstractDungeon.actionManager.addToBottom(new CreateCogInHandAction(this.cogs));
        }
        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        this.amount = CARDS_TO_DRAW;
        updateDescription();
    }
}
