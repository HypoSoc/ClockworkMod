package clockworkmod.cards;

import clockworkmod.actions.TiltAWhirlAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class TiltAWhirl extends AbstractClockworkCard {
    private static final String ID = getID("TiltAWhirl");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/tilt_a_whirl.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = -1;
    private static final int POWER = 1;

    public TiltAWhirl()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new TiltAWhirlAction(p, this.multiDamage, this.damageTypeForTurn,
                this.upgraded, this.freeToPlayOnce, this.energyOnUse));

    }

    public AbstractCard makeCopy()
    {
        return new TiltAWhirl();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

