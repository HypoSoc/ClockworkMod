package clockworkmod.cards;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TestingEnvironment extends AbstractClockworkCard implements ModalChoice.Callback{
    private static final String ID = getID("TestingEnvironment");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/testing_environment.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;

    private ModalChoice modal;

    public TestingEnvironment()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.RED) // Sets color of any following cards to red
                .addOption("Attack","Choose 1 of 3 random Attack cards to add to your hand, it costs 0 this turn.", CardTarget.NONE)
                .setColor(CardColor.GREEN) // Sets color of any following cards to green
                .addOption("Skill", "Choose 1 of 3 random Skill cards to add to your hand, it costs 0 this turn.", CardTarget.NONE)
                .create();

        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    public AbstractCard makeCopy()
    {
        return new TestingEnvironment();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();

            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.RED) // Sets color of any following cards to red
                    .addOption("Attack","Choose 1 of 3 random Attack cards to add to your hand, it costs 0 this turn.", CardTarget.NONE)
                    .setColor(CardColor.GREEN) // Sets color of any following cards to green
                    .addOption("Skill","Choose 1 of 3 random Skill cards to add to your hand, it costs 0 this turn.", CardTarget.NONE)
                    .setColor(CardColor.BLUE) // Sets color of any following cards to green
                    .addOption("Power","Choose 1 of 3 random Power cards to add to your hand, it costs 0 this turn.", CardTarget.NONE)
                    .create();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {
        CardType type;
        switch (i) {
            case 0:
                type = CardType.ATTACK;
                break;
            case 1:
                type = CardType.SKILL;
                break;
            case 2:
                type = CardType.POWER;
                break;
            default:
                return;
        }
        AbstractDungeon.actionManager.addToBottom(new DiscoveryAction(type));
    }
}
