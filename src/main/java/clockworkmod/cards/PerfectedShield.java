package clockworkmod.cards;

import clockworkmod.patches.TagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PerfectedShield extends AbstractClockworkCard {
    private static final String ID = getID("PerfectedShield");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/perfected_shield.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;
    private static final int BLOCK = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 1;

    public PerfectedShield()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.SHIELD);
    }

    public static int countCards()
    {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isShield(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isShield(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isShield(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isShield(AbstractCard c)
    {
        return c.hasTag(TagEnum.SHIELD);
    }

    @Override
    public void applyPowers(){
        this.baseBlock = BLOCK + countCards()*this.magicNumber + momentumIncrementor + tinkerIncrementor;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    public AbstractCard makeCopy()
    {
        return new PerfectedShield();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}
