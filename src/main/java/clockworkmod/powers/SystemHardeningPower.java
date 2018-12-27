package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SystemHardeningPower extends AbstractClockworkPower implements OnCardDrawPower {
    private static final String POWER_ID = getID("SystemHardening");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/system_hardening.png"));

    public SystemHardeningPower(AbstractCreature owner, int amount) {
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
    public void onCardDraw(AbstractCard abstractCard) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
}

