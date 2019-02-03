package clockworkmod.relics;

import clockworkmod.fields.ReboundField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RubberBall extends AbstractClockworkRelic {
    private static final String ID = getID("RubberBall");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SHOP;
    private static final String IMG = "relics/rubber_ball.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    private boolean hasRebounded = false;

    public RubberBall() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RubberBall();
    }

    @Override
    public void atTurnStart()
    {
        this.hasRebounded = false;
        this.pulse = true;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(hasRebounded){
            return;
        }
        if(targetCard.exhaust || FleetingField.fleeting.get(targetCard)){
            return;
        }
        if(ReboundField.rebound.get(targetCard) == ReboundField.NO_REBOUND){
            flash();
            this.pulse = false;
            this.hasRebounded = true;
            ReboundField.rebound.set(targetCard, ReboundField.REBOUND_EXACTLY_ONCE);
        }
    }
}
