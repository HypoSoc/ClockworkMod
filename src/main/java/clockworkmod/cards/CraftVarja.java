package clockworkmod.cards;

import clockworkmod.actions.GainRelicAction;
import clockworkmod.patches.TagEnum;
import clockworkmod.relics.Varja;
import clockworkmod.variables.DepletingVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CraftVarja extends AbstractClockworkCard {
    private static final String ID = getID("CraftVarja");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/craft_varja.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_COST = 1;

    public CraftVarja()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.CRAFT);
        this.tags.add(CardTags.HEALING);
        this.isEthereal = true;
        DepletingVariable.setBaseValue(this,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        if(DepletingVariable.isDepleted(this)) {
            AbstractDungeon.actionManager.addToBottom(new GainRelicAction(new Varja()));
        }
    }

    public AbstractCard makeCopy()
    {
        return new CraftVarja();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }
}
