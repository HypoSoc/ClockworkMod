package clockworkmod.relics;

import clockworkmod.powers.MomentumPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class MomentumEngine extends AbstractClockworkRelic {
    private static final String ID = getID("MomentumEngine");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = AbstractRelic.RelicTier.STARTER;
    private static final String IMG = "relics/momentumengine.png";
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.HEAVY;

    public MomentumEngine() {
        super(ID, IMG, TIER, SOUND);
        setCounter(-1);
    }

    @Override
    public void setCounter(int c)
    {
        super.setCounter(c);
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 0)
            return strings.DESCRIPTIONS[0] + 1 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new MomentumEngine();
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
                new MomentumPower(AbstractDungeon.player, amount), amount));
    }
}

