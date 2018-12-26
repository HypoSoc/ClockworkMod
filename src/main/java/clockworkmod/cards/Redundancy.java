package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Redundancy extends AbstractClockworkCard {
    private static final String ID = getID("Redundancy");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/redundancy.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 5;
    private static final int POWER = 15;
    private static final int MAGIC = 7;
    private static final int UPGRADE_BONUS = 5;

    public Redundancy()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    private void updateCostAlt(int amt) {
        int tmpCost = this.cost;
        int diff = this.cost - this.costForTurn;

        tmpCost += amt;
        if (tmpCost < 0) {
            tmpCost = 0;
        }
        if (tmpCost != this.cost)
        {
            this.cost = tmpCost;
            this.costForTurn = (this.cost - diff);
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
        }
    }

    public void atTurnStart() {
        if(!this.isCostModified && !this.isCostModifiedForTurn) {
            int current = this.cost;
            int deckSize = AbstractDungeon.player.drawPile.size()
                    + AbstractDungeon.player.discardPile.size()
                    + AbstractDungeon.player.hand.size();
            int desired = COST - (deckSize / this.magicNumber);
            this.updateCostAlt(desired - current);
        }
    }

    public void applyPowers() {
        if(!this.isCostModified && !this.isCostModifiedForTurn) {
            int current = this.cost;
            int deckSize = AbstractDungeon.player.drawPile.size()
                    + AbstractDungeon.player.discardPile.size()
                    + AbstractDungeon.player.hand.size();
            int desired = COST - (deckSize / this.magicNumber);
            this.updateCostAlt(desired - current);
        }
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public AbstractCard makeCopy()
    {
        return new Redundancy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }
}
