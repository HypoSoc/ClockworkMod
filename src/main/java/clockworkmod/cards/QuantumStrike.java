package clockworkmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class QuantumStrike extends AbstractClockworkCard implements SafeStartupCard {
    private static final String ID = getID("QuantumStrike");
    public static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/quantum_strike.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 4;
    private static final int MAGIC = 2;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int UPGRADE_MAGIC = 1;

    private boolean alreadyHandled = false;

    public QuantumStrike()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;

        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public AbstractCard makeCopy()
    {
        return new QuantumStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC);
            if(AbstractDungeon.player != null && !alreadyHandled) {
                alreadyHandled = true;
                for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                    ((QuantumStrike)c).alreadyHandled = true;
                }
                for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                    c.upgrade();
                }
            }
        }
        alreadyHandled = false;
    }

    private CardGroup getGroup(AbstractCard entangledCopy){
        CardGroup[] groups = {AbstractDungeon.player.drawPile,
                AbstractDungeon.player.discardPile,
                AbstractDungeon.player.hand,
                AbstractDungeon.player.exhaustPile
        };
        for(CardGroup group : groups){
            if(group.contains(entangledCopy)){
                return group;
            }
        }
        return AbstractDungeon.player.limbo;
    }

    @Override
    public void triggerOnExhaust() {
        if(AbstractDungeon.player != null && !alreadyHandled) {
            alreadyHandled = true;
            for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                ((QuantumStrike)c).alreadyHandled = true;
            }
            for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, getGroup(c)));
            }
        }
        alreadyHandled = false;
    }

    @Override
    public boolean atBattleStartPreDraw() {
        this.rawDescription = strings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
        for (int i = 0; i < this.magicNumber - 1; i++) {
            AbstractCard c = this.makeSameInstanceOf();
            c.rawDescription = strings.EXTENDED_DESCRIPTION[0];
            c.initializeDescription();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
        }
        return true;
    }
}
