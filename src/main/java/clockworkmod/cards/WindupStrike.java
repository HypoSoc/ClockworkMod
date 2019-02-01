package clockworkmod.cards;

import clockworkmod.actions.IncreaseTinkerAction;
import clockworkmod.actions.MakeRealCardInDiscardAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WindupStrike extends AbstractClockworkCard {
    private static final String ID = getID("WindupStrike");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/windup_strike.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int MAGIC = 1;
    private static final int LIMIT = 20;
    private static final int UPGRADED_LIMIT = 25;

    public WindupStrike()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractCard.CardTags.STRIKE);
        AlwaysRetainField.alwaysRetain.set(this, true);
        FleetingField.fleeting.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int limit = LIMIT;
        if(upgraded){
            limit = UPGRADED_LIMIT;
        }
        if(this.baseDamage >= limit){
            AbstractDungeon.actionManager.addToBottom(
                    new TalkAction(true,strings.EXTENDED_DESCRIPTION[0], 2.0F, 2.0F));
            AbstractCard card  = new OverlyWoundupStrike();
            AbstractDungeon.actionManager.addToBottom(new MakeRealCardInDiscardAction(card));
        } else {
            AbstractCard card  = new WoundupStrike();
            card.baseDamage = this.baseDamage;
            ((WoundupStrike) card).tinkerIncrementor = this.baseDamage - 1;
            AbstractDungeon.actionManager.addToBottom(new MakeRealCardInDiscardAction(card));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        AbstractDungeon.actionManager.addToBottom(new IncreaseTinkerAction(this, this.magicNumber));
        super.triggerOnEndOfPlayerTurn();
    }

    public AbstractCard makeCopy()
    {
        return new WindupStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
