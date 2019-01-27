package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MechanicalBulk extends AbstractClockworkCard {
    private static final String ID = getID("MechanicalBulk");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/mechanical_bulk.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;
    private static final int BLOCK = 3;
    private static final int MAGIC = 8;
    private static final int UPGRADE_BONUS = -2;

    public MechanicalBulk()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        int timesApply = deckSize/this.magicNumber;

        for(int i=0; i<timesApply; i++){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }

    public void applyPowers() {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        int timesApply = deckSize/this.magicNumber;
        super.applyPowers();
        if(timesApply <= 1){
            this.rawDescription = strings.DESCRIPTION + strings.EXTENDED_DESCRIPTION[0] +
                    strings.EXTENDED_DESCRIPTION[1];
        }
        else{
            this.rawDescription = strings.DESCRIPTION + strings.EXTENDED_DESCRIPTION[0] +
                    + timesApply + strings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public AbstractCard makeCopy()
    {
        return new MechanicalBulk();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            if(this.magicNumber >= 5) {
                upgradeMagicNumber(UPGRADE_BONUS);
            }
        }
    }
}
