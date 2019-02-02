package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class QuicksilverHourglass extends AbstractStackableClockworkRelic {
    private static final String ID = getID("QuicksilverHourglass");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/quicksilver_hourglass.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public QuicksilverHourglass() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 1)
            return strings.DESCRIPTIONS[0] + 3 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter*3 + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new QuicksilverHourglass();
    }

    @Override
    public void atTurnStart()
    {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        amount *= 3;
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(amount, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}

