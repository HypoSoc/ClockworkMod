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

public class PerfectedStrike_Clockwork extends AbstractClockworkCard {
    private static final String ID = getID("PerfectedStrike");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/perfected_strike.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 6;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 1;

    public PerfectedStrike_Clockwork()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    public static int countCards()
    {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isStrike(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isStrike(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isStrike(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isStrike(AbstractCard c)
    {
        return c.hasTag(AbstractCard.CardTags.STRIKE);
    }

    @Override
    public void applyPowers(){
        this.baseDamage = POWER + countCards()*this.magicNumber + momentumIncrementor;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public AbstractCard makeCopy()
    {
        return new PerfectedStrike_Clockwork();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}
