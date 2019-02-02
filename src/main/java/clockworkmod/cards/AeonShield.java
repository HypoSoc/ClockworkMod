package clockworkmod.cards;

import clockworkmod.fields.ReboundField;
import clockworkmod.patches.TagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AeonShield extends AbstractClockworkCard {
    private static final String ID = getID("AeonShield");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/aeon_shield.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int BLOCK = 3;

    public AeonShield()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.tags.add(TagEnum.SHIELD);
        ReboundField.rebound.set(this, ReboundField.REBOUND_ONCE_PER_TURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
    }

    @Override
    public void triggerWhenDrawn(){
        applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public AbstractCard makeCopy()
    {
        return new AeonShield();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            ReboundField.rebound.set(this, ReboundField.ALWAYS_REBOUND);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
