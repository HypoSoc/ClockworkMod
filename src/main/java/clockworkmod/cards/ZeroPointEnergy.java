package clockworkmod.cards;

import clockworkmod.powers.ZeroPointEnergyPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ZeroPointEnergy extends AbstractClockworkCard {
    private static final String ID = getID("ZeroPointEnergy");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/zero_point_energy.png";

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int ENERGY = 1;
    private static final int UPGRADED_ENERGY = 2;

    public ZeroPointEnergy()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.isInnate = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int energy = ENERGY;
        if(this.upgraded){
            energy = UPGRADED_ENERGY;
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ZeroPointEnergyPower(p, energy), energy));
    }

    public AbstractCard makeCopy()
    {
        return new ZeroPointEnergy();
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
