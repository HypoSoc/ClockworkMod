package clockworkmod.relics;

import clockworkmod.cards.WarmUp;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

public class BreathingRoom extends AbstractClockworkRelic implements ClickableRelic {
    private static final String ID = getID("BreathingRoom");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/breathing_room.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL;

    private static final int START_CHARGE = 2;
    private static final int PER_REST = 1;

    public BreathingRoom() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription()
    {
        return CLICKABLE_DESCRIPTIONS()[0] + DESCRIPTIONS[0] + PER_REST + DESCRIPTIONS[1] + START_CHARGE + DESCRIPTIONS[2];
    }

    @Override
    public void onEquip()
    {
        startingCharges();
    }

    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        if (room instanceof RestRoom) {
            flash();
            gainCharges();
        }
    }

    private void startingCharges()
    {
        setCounter(START_CHARGE);
    }

    private void gainCharges()
    {
        if (counter < 0) {
            counter = 0;
        }
        setCounter(counter + PER_REST);
    }

    @Override
    public void onRightClick()
    {
        if (!isObtained || counter <= 0) {
            return;
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            flash();
            setCounter(counter - 1);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((new WarmUp()).makeCopy()));
        }
    }
}
