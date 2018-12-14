package clockworkmod.actions;

import basemod.BaseMod;
import clockworkmod.ClockworkMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class CreateCogInHandAction extends AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.35F;
    private static final float PADDING = 25.0F * Settings.scale;

    private boolean upgrade;

    public CreateCogInHandAction()
    {
        this(1, false);
    }

    public CreateCogInHandAction(int amount)
    {
        this(amount, false);
    }

    public CreateCogInHandAction(int amount, boolean upgrade)
    {
        this.amount = amount;
        this.upgrade = upgrade;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = DURATION_PER_CARD;
    }

    public void update()
    {
        if (this.amount == 0)
        {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            discardAmount = this.amount + AbstractDungeon.player.hand.size() - BaseMod.MAX_HAND_SIZE;
            handAmount -= discardAmount;
        }
        addToHand(handAmount);
        addToDiscard(discardAmount);
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
        }
        this.isDone = true;
    }

    private void addToHand(int handAmt)
    {
        switch (this.amount)
        {
            case 0:
                break;
            case 1:
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade)));
                }
                break;
            case 2:
                if (handAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                break;
            case 3:
                if (handAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 3)
                {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade)));
                    }
                }
                break;
            default:
                for (int i = 0; i < handAmt; i++) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(ClockworkMod.cog(this.upgrade),
                            MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
                            MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
                }
        }
    }

    private void addToDiscard(int discardAmt)
    {
        switch (this.amount)
        {
            case 0:
                break;
            case 1:
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                break;
            case 2:
                if (discardAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));
                }
                break;
            case 3:
                if (discardAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 3)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                break;
            default:
                for (int i = 0; i < discardAmt; i++) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(ClockworkMod.cog(this.upgrade),
                            MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
                            MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
                }
        }
    }
}
