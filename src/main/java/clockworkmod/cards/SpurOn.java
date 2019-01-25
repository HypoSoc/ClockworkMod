package clockworkmod.cards;

import clockworkmod.actions.CreateCogInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpurOn extends AbstractClockworkCard {
    private static final String ID = getID("SpurOn");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/spur_on.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 3;
    private static final int MAGIC = 10;

    public SpurOn()
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(
                new CreateCogInHandAction(deckSize/this.magicNumber, upgraded, new SpurCog()));
    }

    public void applyPowers() {
        int deckSize = AbstractDungeon.player.drawPile.size()
                + AbstractDungeon.player.discardPile.size()
                + AbstractDungeon.player.hand.size();
        int amount = deckSize/this.magicNumber;
        super.applyPowers();
        String desc = strings.DESCRIPTION;
        if(upgraded){
            desc = strings.UPGRADE_DESCRIPTION;
        }
        if(amount == 1){
            this.rawDescription = desc + strings.EXTENDED_DESCRIPTION[0] +
                    strings.EXTENDED_DESCRIPTION[1];
        }
        else{
            this.rawDescription = desc + strings.EXTENDED_DESCRIPTION[0] +
                    + amount + strings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    public AbstractCard makeCopy()
    {
        return new SpurOn();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
