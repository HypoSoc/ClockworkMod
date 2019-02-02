package clockworkmod.cards;

import clockworkmod.variables.DepletingVariable;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Halt extends AbstractClockworkCard {
    private static final String ID = getID("Halt");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/halt.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    public Halt()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.tags.add(CardTags.HEALING);
        DepletingVariable.setBaseValue(this,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying))
                {
                    AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, p));
                }
            }
        }
    }

    public AbstractCard makeCopy()
    {
        return new Halt();
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

