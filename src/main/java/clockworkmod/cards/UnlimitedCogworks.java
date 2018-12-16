package clockworkmod.cards;

import clockworkmod.powers.UnlimitedCogworksDamagePower;
import clockworkmod.powers.UnlimitedCogworksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnlimitedCogworks extends AbstractClockworkCard {
    private static final String ID = getID("UnlimitedCogworks");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/unlimited_cog_works.png";

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int BOOM = 50;

    public UnlimitedCogworks()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new UnlimitedCogworksPower(p, this.magicNumber), this.magicNumber));
        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new UnlimitedCogworksDamagePower(p, BOOM), BOOM));
        }
    }

    public AbstractCard makeCopy()
    {
        return new UnlimitedCogworks();
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
