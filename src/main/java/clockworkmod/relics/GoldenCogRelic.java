package clockworkmod.relics;

import clockworkmod.actions.CreateCogInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GoldenCogRelic extends AbstractStackableClockworkRelic {
    private static final String ID = getID("GoldenCog");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/goldencog.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public GoldenCogRelic() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 1)
            return strings.DESCRIPTIONS[0] + 1 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter + strings.DESCRIPTIONS[2];
    }

    public AbstractRelic makeCopy() {
        return new GoldenCogRelic();
    }


    @Override
    public void atBattleStart() {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        AbstractDungeon.actionManager.addToTop(new CreateCogInHandAction(amount));
    }
}

