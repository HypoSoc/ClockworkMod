package clockworkmod;

import basemod.BaseMod;
import basemod.interfaces.*;
import clockworkmod.cards.*;
import clockworkmod.characters.ClockworkCharacter;
import clockworkmod.patches.AbstractCardEnum;
import clockworkmod.patches.ClockworkEnum;
import clockworkmod.relics.GoldenCogRelic;
import clockworkmod.relics.MomentumEngine;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static basemod.BaseMod.addRelicToCustomPool;

@SpireInitializer
public class ClockworkMod implements EditCharactersSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditRelicsSubscriber, EditCardsSubscriber {

    private static final Color CLOCKWORK_COLOR = CardHelper.getColor(33.0f, 144.0f, 255.0f);
    private static final String ASSETS_FOLDER = "clockworkmod/images";

    private static final String ATTACK_CARD = "512/bg_attack_clockwork.png";
    private static final String SKILL_CARD = "512/bg_skill_clockwork.png";
    private static final String POWER_CARD = "512/bg_power_clockwork.png";
    private static final String ENERGY_ORB = "512/card_clockwork_orb.png";

    private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_clockwork.png";
    private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_clockwork.png";
    private static final String POWER_CARD_PORTRAIT = "1024/bg_power_clockwork.png";
    private static final String ENERGY_ORB_PORTRAIT = "1024/card_clockwork_orb.png";

    private static final String CHAR_BUTTON = "charSelect/button.png";
    private static final String CHAR_PORTRAIT = "charSelect/portrait.png";

    public static Sfx CRAFT = new Sfx("clockworkmod/audio/craft.ogg");
    public static Sfx TICK = new Sfx("clockworkmod/audio/tick.ogg");
    public static Sfx TOCK = new Sfx("clockworkmod/audio/tock.ogg");

    public static String getResourcePath(String resource) {
        return ASSETS_FOLDER + "/" + resource;
    }

    public static final Logger logger = LogManager.getLogger(ClockworkMod.class);

    public ClockworkMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(AbstractCardEnum.CLOCKWORK,
                CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD), getResourcePath(POWER_CARD),
                getResourcePath(ENERGY_ORB), getResourcePath(ATTACK_CARD_PORTRAIT),
                getResourcePath(SKILL_CARD_PORTRAIT), getResourcePath(POWER_CARD_PORTRAIT),
                getResourcePath(ENERGY_ORB_PORTRAIT));
    }

    public static void initialize() {
        new ClockworkMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new ClockworkCharacter("The Clockwork"),
                getResourcePath(CHAR_BUTTON),
                getResourcePath(CHAR_PORTRAIT),
                ClockworkEnum.CLOCKWORK);
    }

    @Override
    public void receiveEditRelics() {
        // Starter
        addRelicToCustomPool(new MomentumEngine(), AbstractCardEnum.CLOCKWORK);

        // Special
        addRelicToCustomPool(new GoldenCogRelic(), AbstractCardEnum.CLOCKWORK);
    }

    @Override
    public void receiveEditCards() {
        //Basic
        BaseMod.addCard(new Shield());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Tinker());
        BaseMod.addCard(new WarmUp());

        //Common Attacks
        BaseMod.addCard(new AdaptiveCannon());
        BaseMod.addCard(new ChargingBlade());
        BaseMod.addCard(new CogToss());
        BaseMod.addCard(new Pluck());

        //Common Skills
        BaseMod.addCard(new AdaptiveShield());
        BaseMod.addCard(new ChargingShield());
        BaseMod.addCard(new Enhance());

        //Uncommon Attacks
        BaseMod.addCard(new FalseStart());
        BaseMod.addCard(new PerfectedStrike_Clockwork());
        BaseMod.addCard(new StasisBreak());

        //Uncommon Skills
        BaseMod.addCard(new CraftCopperScales());
        BaseMod.addCard(new RampUp());
        BaseMod.addCard(new Selectivity());

        //Uncommon Powers
        BaseMod.addCard(new AssemblyLine());
        BaseMod.addCard(new RevUp());

        //Rare Attacks

        //Rare Skills
        BaseMod.addCard(new CraftMomentumEngine());

        //Rare Powers

        //Special
        BaseMod.addCard(new DefectiveCog());
        BaseMod.addCard(new GoldenCog());
        BaseMod.addCard(new HelicalCog());
        BaseMod.addCard(new SpurCog());
    }

    @Override
    public void receiveEditStrings() {
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "clockworkmod/localization/ClockworkMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "clockworkmod/localization/ClockworkMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "clockworkmod/localization/ClockworkMod-Relic-Strings.json");

    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(new String[]{"cog", "Cog", "cogs", "Cogs"},
                "Cogs are 0 cost cards with minor effects that draw you cards.");

        BaseMod.addKeyword("Momentum", new String[]{"momentum", "Momentum"},
                "After you play a card, increase its damage and Block for the rest of combat.");

        BaseMod.addKeyword("Rebound", new String[]{"rebound", "Rebound"},
                "The first time you play this card each turn, put it on top your draw pile instead of in your discard pile.");

        BaseMod.addKeyword("Stasis", new String[]{"stasis", "Stasis"},
                "Prevent all damage that would dealt to or by you while you are in Stasis.");
    }

    public static AbstractCard cog(boolean upgrade) {
        AbstractCard cog = (new SpurCog()).makeCopy();
        int v = AbstractDungeon.cardRandomRng.random(100);
        // LUCKY SPIN
        if(v == 77){
            cog = (new GoldenCog()).makeCopy();
        }
        v %= 5;
        if(v == 0 || v == 3) {
            cog = (new SpurCog()).makeCopy();
        }
        else if (v == 1 || v == 4){
            cog = (new HelicalCog()).makeCopy();
        }
        else if (v == 2) {
            cog = (new DefectiveCog()).makeCopy();
        }
        if(upgrade){
            cog.upgrade();
        }
        return cog;
    }
}