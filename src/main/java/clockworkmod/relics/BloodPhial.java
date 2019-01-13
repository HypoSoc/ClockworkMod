package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BloodPhial extends AbstractStackableClockworkRelic {
    private static final String ID = getID("BloodPhial");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/blood_phial.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public BloodPhial() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 1)
            return strings.DESCRIPTIONS[0] + 2 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter*2 + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new BloodPhial();
    }


    @Override
    public void atBattleStart() {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        amount *= 2;
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, amount));
    }
}

