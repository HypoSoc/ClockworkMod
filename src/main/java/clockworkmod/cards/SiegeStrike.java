package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class SiegeStrike extends AbstractClockworkCard {
    private static final String ID = getID("SiegeStrike");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/siege_strike.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_BONUS = 1;

    public SiegeStrike()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    private int getArmor(){
        int armor = 0;
        if(AbstractDungeon.player.hasPower("Plated Armor")){
            armor += AbstractDungeon.player.getPower("Plated Armor").amount;
        }
        if(AbstractDungeon.player.hasPower("Metallicize")){
            armor += AbstractDungeon.player.getPower("Metallicize").amount;
        }
        return armor;
    }

    @Override
    public void applyPowers(){
        this.baseDamage = POWER + AbstractDungeon.player.currentBlock + getArmor() +
                momentumIncrementor + tinkerIncrementor;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy()
    {
        return new SiegeStrike();
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
