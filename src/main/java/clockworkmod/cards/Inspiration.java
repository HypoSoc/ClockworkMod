package clockworkmod.cards;

import clockworkmod.patches.TagEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Inspiration extends AbstractClockworkCard {
    private static final String ID = getID("Inspiration");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/inspiration.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int MAGIC = 2;


    public Inspiration()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    private boolean hasCraftInDrawPile(){
        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c.hasTag(TagEnum.CRAFT)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(hasCraftInDrawPile()) {
            Predicate<AbstractCard> predicate = card -> card.hasTag(TagEnum.CRAFT);
            Consumer<List<AbstractCard>> callback = cardList -> {
                for(AbstractCard c:cardList){
                    c.setCostForTurn(0);
                }
            };
            AbstractDungeon.actionManager.addToBottom(
                    new FetchAction(p.drawPile, predicate, callback));
        }
        else if(upgraded){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        }
    }

    public AbstractCard makeCopy()
    {
        return new Inspiration();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
