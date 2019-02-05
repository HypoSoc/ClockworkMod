package clockworkmod;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import clockworkmod.cards.*;
import clockworkmod.characters.ClockworkCharacter;
import clockworkmod.fields.DepletingSave;
import clockworkmod.patches.AbstractCardEnum;
import clockworkmod.patches.ClockworkEnum;
import clockworkmod.powers.OnCardDrawEnemyPower;
import clockworkmod.relics.*;
import clockworkmod.variables.DepletingVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

import static basemod.BaseMod.addRelicToCustomPool;

@SpireInitializer
public class ClockworkMod implements EditCharactersSubscriber, EditStringsSubscriber,
        EditKeywordsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, PostDrawSubscriber,
        PostInitializeSubscriber, OnStartBattleSubscriber {

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

    public static boolean tick_tock = true;
    public static final String PROP_TICK_TOCK = "tick_tock";
    public static Properties clockworkDefaults = new Properties();

    public static String getResourcePath(String resource) {
        return ASSETS_FOLDER + "/" + resource;
    }

    public static final Logger logger = LogManager.getLogger(ClockworkMod.class);

    public ClockworkMod() {
        BaseMod.subscribe(this);

        BaseMod.addSaveField("depletion", new DepletingSave());

        BaseMod.addColor(AbstractCardEnum.CLOCKWORK,
                CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR, CLOCKWORK_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD), getResourcePath(POWER_CARD),
                getResourcePath(ENERGY_ORB), getResourcePath(ATTACK_CARD_PORTRAIT),
                getResourcePath(SKILL_CARD_PORTRAIT), getResourcePath(POWER_CARD_PORTRAIT),
                getResourcePath(ENERGY_ORB_PORTRAIT));

        clockworkDefaults.setProperty(PROP_TICK_TOCK, "TRUE");
        loadConfigData();
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

        // Rare
        addRelicToCustomPool(new BreathingRoom(), AbstractCardEnum.CLOCKWORK);

        // Boss
        addRelicToCustomPool(new BootleggersToolkit(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new PerpetualMotionMachine(), AbstractCardEnum.CLOCKWORK);

        // Shop
        addRelicToCustomPool(new RubberBall(), AbstractCardEnum.CLOCKWORK);

        // Special
        addRelicToCustomPool(new BeatingHeart(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new BloodPhial(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new CopperScales(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new GoldenCogRelic(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new QuicksilverHourglass(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new SomewhatSmoothStone(), AbstractCardEnum.CLOCKWORK);
        addRelicToCustomPool(new Varja(), AbstractCardEnum.CLOCKWORK);
    }

    @Override
    public void receiveEditCards() {
        //DynamicVariable
        BaseMod.addDynamicVariable(new DepletingVariable());

        //Basic
        BaseMod.addCard(new Shield());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Tinker());
        BaseMod.addCard(new WarmUp());

        //Common Attacks
        BaseMod.addCard(new AdaptiveCannon());
        BaseMod.addCard(new ChargingBlade());
        BaseMod.addCard(new CogToss());
        BaseMod.addCard(new ForgeStrike());
        BaseMod.addCard(new Friction());
        BaseMod.addCard(new Pluck());
        BaseMod.addCard(new SnapKeep());
        BaseMod.addCard(new Redundancy());
        BaseMod.addCard(new Roundhouse());

        //Common Skills
        BaseMod.addCard(new AdaptiveShield());
        BaseMod.addCard(new ChargingShield());
        BaseMod.addCard(new CogBarrier());
        BaseMod.addCard(new CogShield());
        BaseMod.addCard(new Enhance());
        BaseMod.addCard(new Improve());
        BaseMod.addCard(new Minimalism());
        BaseMod.addCard(new ReserveSystems());
        BaseMod.addCard(new SystemHardening());
        BaseMod.addCard(new SystemScan());

        //Uncommon Attacks
        BaseMod.addCard(new Efficiency());
        BaseMod.addCard(new EnthusiasticStrike());
        BaseMod.addCard(new FalseStart());
        BaseMod.addCard(new HeadStart());
        BaseMod.addCard(new HoardingStrike());
        BaseMod.addCard(new MechanicalMass());
        BaseMod.addCard(new PerfectedStrike_Clockwork());
        BaseMod.addCard(new QuantumStrike());
        BaseMod.addCard(new RitualDaggerMk0());
        BaseMod.addCard(new SearchingStrike());
        BaseMod.addCard(new SearingStrike());
        BaseMod.addCard(new SmithsMallet());
        BaseMod.addCard(new SpurOn());
        BaseMod.addCard(new StasisBreak());
        BaseMod.addCard(new TiltAWhirl());

        //Uncommon Skills
        BaseMod.addCard(new Clone());
        BaseMod.addCard(new CraftBloodPhial());
        BaseMod.addCard(new CraftCopperScales());
        BaseMod.addCard(new CraftSomewhatSmoothStone());
        BaseMod.addCard(new CraftVarja());
        BaseMod.addCard(new Downsize());
        BaseMod.addCard(new InertialShield());
        BaseMod.addCard(new MechanicalBulk());
        BaseMod.addCard(new PerfectedShield());
        BaseMod.addCard(new RampUp());
        BaseMod.addCard(new Scour());
        BaseMod.addCard(new Selectivity());
        BaseMod.addCard(new SpringShield());
        BaseMod.addCard(new TestingEnvironment());
        BaseMod.addCard(new TimeOut());

        //Uncommon Powers
        BaseMod.addCard(new AssemblyLine());
        BaseMod.addCard(new ElasticNanites());
        BaseMod.addCard(new LiteraryArmor());
        BaseMod.addCard(new LogisticRegression());
        BaseMod.addCard(new OccamsRazor());
        BaseMod.addCard(new RevUp());

        //Rare Attacks
        BaseMod.addCard(new CraftQuicksilverHourglass());
        BaseMod.addCard(new KaliMa());
        BaseMod.addCard(new Randomization());
        BaseMod.addCard(new SiegeStrike());
        BaseMod.addCard(new Strangle());
        BaseMod.addCard(new WindupStrike());

        //Rare Skills
        BaseMod.addCard(new AeonShield());
        BaseMod.addCard(new CraftMomentumEngine());
        BaseMod.addCard(new Halt());
        BaseMod.addCard(new Inspiration());
        BaseMod.addCard(new TimeWalk());

        //Rare Powers
        BaseMod.addCard(new DefectiveParts());
        BaseMod.addCard(new PerfectForm());
        BaseMod.addCard(new ReactiveNanites());
        BaseMod.addCard(new UnlimitedCogworks());
        BaseMod.addCard(new ZeroPointEnergy());

        //Special
        BaseMod.addCard(new DefectiveCog());
        BaseMod.addCard(new GoldenCog());
        BaseMod.addCard(new HelicalCog());
        BaseMod.addCard(new OverlyWoundupStrike());
        BaseMod.addCard(new SpurCog());
        BaseMod.addCard(new WoundupStrike());
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
        BaseMod.addKeyword(new String[]{"cog", "cogs"},
                "Cogs are 0 cost cards with minor effects that draw you cards.");

        BaseMod.addKeyword("Momentum", new String[]{"momentum"},
                "After you play a card, increase its damage and Block for the rest of combat.");

        BaseMod.addKeyword("Rebound", new String[]{"rebound"},
                "The first time you play this card each turn, put it on top your draw pile instead of in your discard pile.");

        BaseMod.addKeyword("Stasis", new String[]{"stasis"},
                "Prevent all damage that would dealt to or by you while you are in Stasis.");

        BaseMod.addKeyword(new String[]{"depleting"},
                "Permanently removes itself from your deck after a certain number of uses.");

        BaseMod.addKeyword(new String[]{"entangled"},
                "Entangled cards are treated as multiple instances of the same card. Effects that modify one will modify all.");

        BaseMod.addKeyword(new String[]{"windup"},
                "Gets permanently stronger by a certain amount at the end of your turn, instead of being discarded. WARNING: Do not wind up too tight.");
    }

    public static AbstractCard cog(boolean upgrade) {
        AbstractCard cog = (new SpurCog()).makeCopy();
        if(AbstractDungeon.player.hasPower("Clockwork:DefectiveParts")){
            cog = (new DefectiveCog()).makeCopy();
            if (upgrade || (AbstractDungeon.cardRandomRng.random(200)%5 == 0)) {
                cog.upgrade();
            }
        }
        else {
            int v = AbstractDungeon.cardRandomRng.random(200);
            logger.debug("Cog: " + v);
            // LUCKY SPIN
            if (v == 77) {
                cog = (new GoldenCog()).makeCopy();
            } else {
                v %= 5;
                if (v == 0 || v == 3) {
                    cog = (new SpurCog()).makeCopy();
                } else if (v == 1 || v == 4) {
                    cog = (new HelicalCog()).makeCopy();
                } else if (v == 2) {
                    cog = (new DefectiveCog()).makeCopy();
                }
                if (upgrade) {
                    cog.upgrade();
                }
            }
        }
        return cog;
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
        //custom callback for card draw on powers
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower p : m.powers) {
                if (p instanceof OnCardDrawEnemyPower) {
                    ((OnCardDrawEnemyPower) p).onCardDraw(c);
                }
            }
        }
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = new Texture(Gdx.files.internal("clockworkmod/images/badge.png"));

        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton tickTockBtn = new ModLabeledToggleButton("Enable Clock Ticking Sound Effect",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                tick_tock, settingsPanel, (label) -> {}, (button) -> {
            tick_tock = button.enabled;
            saveData();
        });
        settingsPanel.addUIElement(tickTockBtn);
        BaseMod.registerModBadge(badgeTexture,
                "ClockworkMod", "HypoSoc", "New Character: The Clockwork", settingsPanel);
    }

    public static void saveData() {
        try {
            SpireConfig config = new SpireConfig("ClockworkMod", "ClockworkSaveData", clockworkDefaults);
            config.setBool(PROP_TICK_TOCK, tick_tock);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigData() {
        try {
            logger.info("ClockworkMod | Loading Config Preferences...");
            SpireConfig config = new SpireConfig("ClockworkMod", "ClockworkSaveData", clockworkDefaults);
            config.load();
            tick_tock = config.getBool(PROP_TICK_TOCK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        CardGroup[] cardGroups = new CardGroup[]{
                AbstractDungeon.player.drawPile,
                AbstractDungeon.player.hand,
                AbstractDungeon.player.discardPile,
                AbstractDungeon.player.exhaustPile
        };

        CardGroup startupCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (CardGroup cardGroup : cardGroups) {
            for (AbstractCard c : cardGroup.group) {
                if (c instanceof SafeStartupCard) {
                    startupCards.addToBottom(c);
                }
            }
        }

        for (AbstractCard c : startupCards.group) {
            if (((SafeStartupCard) c).atBattleStartPreDraw()) {
                AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }
        }
    }
}