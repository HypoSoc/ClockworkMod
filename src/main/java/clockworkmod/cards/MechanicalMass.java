package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MechanicalMass extends AbstractClockworkCard {
    private static final String ID = getID("MechanicalMass");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/mechanical_mass.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 3;
    private static final int MAGIC = 7;
    private static final int UPGRADE_BONUS = -2;

    public MechanicalMass()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        int timesHit = deckSize/this.magicNumber;

        for(int i=0; i<timesHit-1; i++){
            AbstractDungeon.actionManager.addToBottom(
                    new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    public void applyPowers() {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        int timesHit = deckSize/this.magicNumber;
        super.applyPowers();
        if(timesHit <= 1){
            this.rawDescription = strings.DESCRIPTION + strings.EXTENDED_DESCRIPTION[0] +
                    strings.EXTENDED_DESCRIPTION[1];
        }
        else{
            this.rawDescription = strings.DESCRIPTION + strings.EXTENDED_DESCRIPTION[0] +
                    + timesHit + strings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public AbstractCard makeCopy()
    {
        return new MechanicalMass();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            if(this.magicNumber >= 5) {
                upgradeMagicNumber(UPGRADE_BONUS);
            }
        }
    }
}
