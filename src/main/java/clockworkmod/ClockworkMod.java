package clockworkmod;

import basemod.BaseMod;
import basemod.helpers.BaseModCardTags;
import basemod.interfaces.*;
import clockworkmod.cards.Shield;
import clockworkmod.cards.Strike;
import clockworkmod.cards.WarmUp;
import clockworkmod.characters.ClockworkCharacter;
import clockworkmod.patches.AbstractCardEnum;
import clockworkmod.patches.ClockworkEnum;
import clockworkmod.relics.MomentumEngine;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.audio.Sfx;
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
    }

    @Override
    public void receiveEditCards() {
        //Basic
        BaseMod.addCard(new Shield());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new WarmUp());
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
        BaseMod.addKeyword("Momentum", new String[]{"momentum", "Momentum"},
                "After you play a card, increase its damage and block for the rest of combat.");
    }
}