package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Varja extends AbstractStackableClockworkRelic {
    private static final String ID = getID("Varja");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/varja.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public Varja() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 1)
            return strings.DESCRIPTIONS[0] + 1 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new Varja();
    }


    @Override
    public void atBattleStart() {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, amount), amount));
    }
}

