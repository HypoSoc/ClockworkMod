package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SystemScan extends AbstractClockworkCard {
    private static final String ID = getID("SystemScan");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/system_scan.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 7;

    public SystemScan()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, deckSize/this.magicNumber));
        if(!this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
        }
    }

    public AbstractCard makeCopy()
    {
        return new SystemScan();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
