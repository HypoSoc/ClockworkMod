package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class StranglePower extends AbstractClockworkPower implements OnCardDrawEnemyPower {
    private static final String POWER_ID = getID("Strangled");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/strangled.png"));

    public StranglePower (AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    public void atStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onCardDraw(AbstractCard abstractCard) {
        flash();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, null, this.amount));
    }
}

