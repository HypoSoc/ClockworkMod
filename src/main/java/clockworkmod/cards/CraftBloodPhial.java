package clockworkmod.cards;

import clockworkmod.actions.GainRelicAction;
import clockworkmod.patches.TagEnum;
import clockworkmod.relics.BloodPhial;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CraftBloodPhial extends AbstractClockworkCard {
    private static final String ID = getID("CraftBloodPhial");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/craft_blood_phial.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_COST = 1;

    public CraftBloodPhial()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.CRAFT);
        this.tags.add(CardTags.HEALING);
        this.isEthereal = true;
        FleetingField.fleeting.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player,
                AbstractDungeon.player, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainRelicAction(new BloodPhial()));
    }

    public AbstractCard makeCopy()
    {
        return new CraftBloodPhial();
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
