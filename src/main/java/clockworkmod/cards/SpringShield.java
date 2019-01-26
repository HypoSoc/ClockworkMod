package clockworkmod.cards;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.IncreaseTinkerAction;
import clockworkmod.patches.TagEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpringShield extends AbstractClockworkCard {
    private static final String ID = getID("SpringShield");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/spring_shield.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE = 1;

    public SpringShield()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(TagEnum.SHIELD);
        AlwaysRetainField.alwaysRetain.set(this, true);

        if(this.baseBlock >= 40){
            FleetingField.fleeting.set(this, true);
            ClockworkMod.logger.debug("!!!");
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        if(this.baseBlock >= 40){
            AbstractDungeon.actionManager.addToBottom(
                    new TalkAction(true,strings.EXTENDED_DESCRIPTION[0], 2.0F, 2.0F));
            FleetingField.fleeting.set(this, true);
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new IncreaseTinkerAction(this,-1*tinkerIncrementor));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        AbstractDungeon.actionManager.addToBottom(new IncreaseTinkerAction(this, this.magicNumber));
        super.triggerOnEndOfPlayerTurn();
    }

    public AbstractCard makeCopy()
    {
        return new SpringShield();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE);
            this.isInnate = true;
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
