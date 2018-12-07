package clockworkmod.cards;

import basemod.helpers.BaseModCardTags;
import clockworkmod.patches.TagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shield extends AbstractClockworkCard {
    private static final String ID = getID("Shield");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/shield.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BONUS = 2;

    public Shield()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(TagEnum.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    public AbstractCard makeCopy()
    {
        return new Shield();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_BONUS);
        }
    }
}
