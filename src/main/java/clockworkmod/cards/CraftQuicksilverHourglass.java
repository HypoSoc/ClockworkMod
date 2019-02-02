package clockworkmod.cards;

import clockworkmod.actions.GainRelicAction;
import clockworkmod.patches.TagEnum;
import clockworkmod.relics.QuicksilverHourglass;
import clockworkmod.variables.DepletingVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CraftQuicksilverHourglass extends AbstractClockworkCard {
    private static final String ID = getID("CraftQuicksilverHourglass");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/craft_quicksilver_hourglass.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 3;
    private static final int POWER = 3;
    private static final int UPGRADE_COST = 1;

    public CraftQuicksilverHourglass()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.isMultiDamage = true;
        this.tags.add(TagEnum.CRAFT);
        this.tags.add(CardTags.HEALING);
        this.isEthereal = true;
        DepletingVariable.setBaseValue(this,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(DepletingVariable.isDepleted(this)) {
            AbstractDungeon.actionManager.addToBottom(new GainRelicAction(new QuicksilverHourglass()));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public AbstractCard makeCopy()
    {
        return new CraftQuicksilverHourglass();
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
