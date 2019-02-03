package clockworkmod.relics;

import clockworkmod.patches.TagEnum;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class BootleggersToolkit extends AbstractClockworkRelic {
    private static final String ID = getID("BootleggersToolkit");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/bootleggers_toolkit.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    public BootleggersToolkit() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BootleggersToolkit();
    }

    private AbstractCard getCraftCard(){
        ArrayList<AbstractCard> uncommonList = new ArrayList();
        ArrayList<AbstractCard> rareList = new ArrayList();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(TagEnum.CRAFT)) {
                uncommonList.add(c);
                rareList.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(TagEnum.CRAFT)) {
                uncommonList.add(c);
                rareList.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(TagEnum.CRAFT)) {
                rareList.add(c);
            }
        }
        if(AbstractDungeon.cardRandomRng.randomBoolean(0.3f)) {
            return rareList.get(AbstractDungeon.cardRandomRng.random(rareList.size() - 1)).makeCopy();
        } else{
            return uncommonList.get(AbstractDungeon.cardRandomRng.random(uncommonList.size() - 1)).makeCopy();
        }
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(getCraftCard(), 1, true, true));
    }
}
