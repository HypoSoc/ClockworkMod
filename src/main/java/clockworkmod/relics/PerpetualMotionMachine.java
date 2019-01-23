package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PerpetualMotionMachine extends AbstractClockworkRelic {
    private static final String ID = getID("PerpetualMotionMachine");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/perpetual_motion_machine.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    private int remainingEnergy = 5;

    public PerpetualMotionMachine() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + 7 + strings.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PerpetualMotionMachine();
    }

    @Override
    public void onEquip()
    {
        if (AbstractDungeon.player.energy.energyMaster > 0) {
            AbstractDungeon.player.energy.energyMaster -= 1;
        }
    }

    @Override
    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void atTurnStart()
    {
        this.counter = 0;
        remainingEnergy = 5;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(remainingEnergy > 0){
            this.counter++;
            if(this.counter >= 7){
                flash();
                this.counter = 0;
                remainingEnergy--;
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
        }
    }
}
