package clockworkmod.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

public abstract class AbstractStackableClockworkRelic extends AbstractClockworkRelic {
    AbstractStackableClockworkRelic(String id, String img, RelicTier tier, LandingSound sfx) {
        super(id, img, tier, sfx);
        setCounter(-1);
    }

    @Override
    public void setCounter(int c)
    {
        super.setCounter(c);
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    void increment() {
        if(counter <= 0){
            counter = 1;
        }
        setCounter(counter + 1);
    }

    @Override
    public void instantObtain()
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId)) {
            AbstractStackableClockworkRelic relic =
                    (AbstractStackableClockworkRelic) AbstractDungeon.player.getRelic(this.relicId);
            relic.increment();
            relic.flash();
        } else {
            super.instantObtain();
        }
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip)
    {
        if (p != null && p.hasRelic(this.relicId)) {
            AbstractStackableClockworkRelic relic =
                    (AbstractStackableClockworkRelic) p.getRelic(this.relicId);
            relic.increment();
            relic.flash();

            isDone = true;
            isObtained = true;
            discarded = true;
        } else {
            super.instantObtain(p, slot, callOnEquip);
        }
    }

    @Override
    public void obtain()
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(this.relicId)) {
            AbstractStackableClockworkRelic relic =
                    (AbstractStackableClockworkRelic) AbstractDungeon.player.getRelic(this.relicId);
            relic.increment();
            relic.flash();
        } else {
            super.obtain();
        }
    }
}
