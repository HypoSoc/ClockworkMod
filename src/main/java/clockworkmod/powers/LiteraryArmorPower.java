package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class LiteraryArmorPower extends AbstractClockworkPower implements OnCardDrawPower {
    private static final String POWER_ID = getID("LiteraryArmor");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/literary_armor.png"));

    private static final int CARDS_TO_DRAW = 7;
    private int armor;

    public LiteraryArmorPower(AbstractCreature owner, int armor) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = CARDS_TO_DRAW;
        this.armor = armor;

        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount <= -1){
            this.description = strings.DESCRIPTIONS[4];
            return;
        }
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1] + this.armor);
        } else{
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2] + this.armor);
        }
        this.description += strings.DESCRIPTIONS[3];
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.armor += stackAmount;
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard) {
        if(this.amount > 0) {
            this.amount -= 1;
            if (this.amount == 0) {
                flash();
                this.amount = -1;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                        new PlatedArmorPower(this.owner, this.armor), this.armor));
            }
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurn()
    {
        this.amount = CARDS_TO_DRAW;
        updateDescription();
    }
}
