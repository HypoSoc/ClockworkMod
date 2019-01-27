package clockworkmod.cards;

import clockworkmod.actions.InertiaAction;
import clockworkmod.patches.TagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InertialShield extends AbstractClockworkCard {
    private static final String ID = getID("InertialShield");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/inertial_shield.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    private static final int MAGIC = 10;
    private static final int UPGRADE_MAGIC = -2;

    public InertialShield()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new InertiaAction(this.magicNumber));
    }

    public void applyPowers() {
        int estimatedBlock = AbstractDungeon.player.currentBlock + this.block;
        int drawAmount = estimatedBlock/this.magicNumber;
        super.applyPowers();
        String desc = strings.DESCRIPTION;
        if(drawAmount == 1){
            this.rawDescription = desc + strings.EXTENDED_DESCRIPTION[0] +
                    strings.EXTENDED_DESCRIPTION[1];
        }
        else{
            this.rawDescription = desc + strings.EXTENDED_DESCRIPTION[0] +
                    + drawAmount + strings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public AbstractCard makeCopy()
    {
        return new InertialShield();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            if(this.magicNumber >= 5) {
                upgradeMagicNumber(UPGRADE_MAGIC);
            }
        }
    }
}
