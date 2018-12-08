package clockworkmod.characters;

import basemod.abstracts.CustomPlayer;
import clockworkmod.ClockworkMod;
import clockworkmod.patches.AbstractCardEnum;
import clockworkmod.patches.ClockworkEnum;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class ClockworkCharacter extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;

    private static final String SHOULDER_2 = "char/shoulder2.png"; // campfire pose
    private static final String SHOULDER_1 = "char/shoulder.png"; // another campfire pose
    private static final String CORPSE = "char/corpse.png"; // dead corpse
    private static final String SKELETON_ATLAS = "char/skeleton.atlas"; // spine animation atlas
    private static final String SKELETON_JSON = "char/skeleton.json"; // spine animation json
    private static final String[] orbTextures = {
            "clockworkmod/images/char/orb/layer1.png",
            "clockworkmod/images/char/orb/layer2.png",
            "clockworkmod/images/char/orb/layer3.png",
            "clockworkmod/images/char/orb/layer4.png",
            "clockworkmod/images/char/orb/layer5.png",
            "clockworkmod/images/char/orb/layer6.png",
            "clockworkmod/images/char/orb/layer1d.png",
            "clockworkmod/images/char/orb/layer2d.png",
            "clockworkmod/images/char/orb/layer3d.png",
            "clockworkmod/images/char/orb/layer4d.png",
            "clockworkmod/images/char/orb/layer5d.png"
    };

    public ClockworkCharacter(String name) {
        super(name, ClockworkEnum.CLOCKWORK, orbTextures,
                "clockworkmod/images/char/orb/vfx.png", (String)null, null);

        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 170.0F * Settings.scale);

        initializeClass(null, ClockworkMod.getResourcePath(SHOULDER_2),
                ClockworkMod.getResourcePath(SHOULDER_1),
                ClockworkMod.getResourcePath(CORPSE),
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        this.loadAnimation(ClockworkMod.getResourcePath(SKELETON_ATLAS), ClockworkMod.getResourcePath(SKELETON_JSON), 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Clockwork:Strike");
        retVal.add("Clockwork:Strike");
        retVal.add("Clockwork:Strike");
        retVal.add("Clockwork:Strike");
        retVal.add("Clockwork:Shield");
        retVal.add("Clockwork:Shield");
        retVal.add("Clockwork:Shield");
        retVal.add("Clockwork:Shield");
        retVal.add("Clockwork:WarmUp");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Clockwork:MomentumEngine");
        UnlockTracker.markRelicAsSeen("Clockwork:MomentumEngine");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Clockwork", "A curious creation, seeking to improve itself. NL Improves itself over the course of combat.",
                70, 70, 0, 149,
                5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "The Clockwork";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.CLOCKWORK;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.SKY;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return null;
    } // TODO

    @Override
    public Color getCardTrailColor() {
        return Color.SKY.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("POWER_METALLICIZE", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "POWER_METALLICIZE";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Clockwork";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new ClockworkCharacter(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return "NL Such a beautiful organ. NL You prepare to acquire it ...";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.SKY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[5];
    }
}
