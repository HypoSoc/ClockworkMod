package clockworkmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BeatingHeart extends AbstractStackableClockworkRelic {
    private static final String ID = getID("BeatingHeart");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/beating_heart.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public BeatingHeart() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        if(this.counter <= 0)
            return strings.DESCRIPTIONS[0] + 1 + strings.DESCRIPTIONS[1];
        return strings.DESCRIPTIONS[0] + this.counter + strings.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new BeatingHeart();
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        int amount = this.counter;
        if(amount <= 0){
            amount = 1;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(amount, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}

