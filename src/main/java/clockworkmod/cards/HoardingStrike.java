package clockworkmod.cards;

import clockworkmod.relics.AbstractStackableClockworkRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HoardingStrike extends AbstractClockworkCard {
    private static final String ID = getID("HoardingStrike");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/hoarding_strike.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_BONUS = 1;

    public HoardingStrike()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    public static int countRelics()
    {
        AbstractPlayer p = AbstractDungeon.player;
        int counter = p.relics.size();
        if (p.hasRelic("Circlet")) {
            counter += p.getRelic("Circlet").counter - 1;
        }
        if (p.hasRelic("Red Circlet")) {
            counter += p.getRelic("Red Circlet").counter - 1;
        }
        //Might as well let the Spice Flow
        if (p.hasRelic("hubris:Spice")) {
            counter += p.getRelic("hubris:Spice").counter - 1;
        }
        for(AbstractRelic r : p.relics){
            if(r instanceof AbstractStackableClockworkRelic){
                if(r.counter >= 1) {
                    counter += r.counter - 1;
                }
            }
        }
        return counter;
    }

    @Override
    public void applyPowers(){
        this.baseDamage = POWER + countRelics()*this.magicNumber + momentumIncrementor + tinkerIncrementor;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public AbstractCard makeCopy()
    {
        return new HoardingStrike();
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
