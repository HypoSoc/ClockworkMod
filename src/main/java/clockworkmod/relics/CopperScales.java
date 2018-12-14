package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CopperScales extends AbstractStackableClockworkRelic {
    private static final String ID = getID("CopperScales");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/copper_scales.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public CopperScales() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 1)
            return strings.DESCRIPTIONS[0] + 3 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter*3 + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new CopperScales();
    }


    @Override
    public void atBattleStart() {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        amount *= 3;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new ThornsPower(AbstractDungeon.player, amount), amount));
    }
}

