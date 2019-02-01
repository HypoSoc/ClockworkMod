package clockworkmod.actions;

import clockworkmod.cards.AbstractClockworkCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class MakeRealCardInDiscardAction
        extends AbstractGameAction
{
    private AbstractCard cardToMake;

    public MakeRealCardInDiscardAction(AbstractCard card)
    {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardToMake = card;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.effectList.add(
                    new ShowCardAndObtainEffect(this.cardToMake, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractCard c = this.cardToMake.makeSameInstanceOf();
            if(c instanceof AbstractClockworkCard){
                ((AbstractClockworkCard) c).tinkerIncrementor =
                        ((AbstractClockworkCard) this.cardToMake).tinkerIncrementor;
            }
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));

            this.duration -= Gdx.graphics.getDeltaTime();
        }
        tickDuration();
    }
}
