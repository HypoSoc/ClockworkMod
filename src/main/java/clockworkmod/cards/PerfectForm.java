package clockworkmod.cards;

import basemod.helpers.BaseModCardTags;
import clockworkmod.powers.ElasticNanitesPower;
import clockworkmod.powers.PerfectFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PerfectForm extends AbstractClockworkCard {
    private static final String ID = getID("PerfectForm");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/perfect_form.png";

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 3;
    private static final int MAGIC = 2;

    public PerfectForm()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        tags.add(BaseModCardTags.FORM);
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PerfectFormPower(p, 1, 1, this.magicNumber), 1));
    }

    public AbstractCard makeCopy()
    {
        return new PerfectForm();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
