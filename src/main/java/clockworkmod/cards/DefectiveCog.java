package clockworkmod.cards;

import clockworkmod.patches.TagEnum;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefectiveCog extends AbstractClockworkCard {
    private static final String ID = getID("DefectiveCog");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/defectivecog.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int POWER = 15;
    private static final int MAGIC = 1;
    private static final int UPGRADE_BONUS = 1;

    public DefectiveCog()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.COG);
        this.isMultiDamage = true;
        ExhaustiveVariable.setBaseValue(this, 3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    private int getDefectivePartsBonus(){
        if(AbstractDungeon.player.hasPower("Clockwork:DefectiveParts")){
            return AbstractDungeon.player.getPower("Clockwork:DefectiveParts").amount;
        }
        return 0;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = POWER + getDefectivePartsBonus() + momentumIncrementor + tinkerIncrementor;
        super.applyPowers();
    }

    @Override
    public void triggerOnExhaust(){
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE));
    }

    public AbstractCard makeCopy()
    {
        return new DefectiveCog();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
