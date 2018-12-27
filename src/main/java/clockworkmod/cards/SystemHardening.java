package clockworkmod.cards;

import clockworkmod.powers.SystemHardeningPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SystemHardening extends AbstractClockworkCard {
    private static final String ID = getID("SystemHardening");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/system_hardening.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int MAGIC = 2;

    public SystemHardening()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new SystemHardeningPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy()
    {
        return new SystemHardening();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            AlwaysRetainField.alwaysRetain.set(this, true);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
