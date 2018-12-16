package clockworkmod.powers;

import clockworkmod.ClockworkMod;
import clockworkmod.actions.DelayedPowerTriggerAction;
import clockworkmod.cards.GoldenCog;
import clockworkmod.patches.TagEnum;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;

public class UnlimitedCogworksDamagePower extends AbstractClockworkPower {
    public static final String POWER_ID = getID("UnlimitedCogworksDamage");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(ClockworkMod.getResourcePath("powers/unlimited_cog_works.png"));

    private static int BOOM = 7;

    public UnlimitedCogworksDamagePower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;

        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    @Override
    public void onSpecificTrigger() {
        ArrayList<AbstractCard> cogs = new ArrayList();
        for(AbstractCard c : AbstractDungeon.player.hand.group){
            if(c.hasTag(TagEnum.COG)){
                cogs.add(c);
            }
        }
        if(cogs.size() >= BOOM){
            for(AbstractCard c : cogs){
                if(! (c instanceof GoldenCog)) {    // It would be cruel to burn gold
                    AbstractDungeon.actionManager.addToBottom(
                            new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(this.amount, true),
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void onDrawOrDiscard() {
        this.onSpecificTrigger();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        AbstractDungeon.actionManager.addToBottom(new DelayedPowerTriggerAction(this));
    }
}
