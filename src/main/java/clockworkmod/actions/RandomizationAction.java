package clockworkmod.actions;

import clockworkmod.cards.Randomization;
import clockworkmod.cards.WindupStrike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static clockworkmod.patches.TagEnum.RANDOMIZATION_WORKAROUND;

public class RandomizationAction extends AbstractGameAction{

    private float x;
    private float y;
    private int amount;
    private boolean upgrade;
    private AbstractMonster target;

    public RandomizationAction(float x, float y, int amount, AbstractMonster target, boolean upgrade){
        this.x = x;
        this.y = y;
        this.amount = amount;
        this.target = target;
        this.upgrade = upgrade;
    }

    @Override
    public void update() {
        AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK);
        while(tmp instanceof Randomization || tmp instanceof WindupStrike){
            tmp = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeStatEquivalentCopy();
        }
        if(upgrade){
            tmp.upgrade();
        }
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = x;
        tmp.current_y = y;
        tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
        tmp.target_y = (Settings.HEIGHT / 2.0F);
        tmp.freeToPlayOnce = true;
        if(!tmp.canUse(AbstractDungeon.player, target)){
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(tmp));
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(tmp, AbstractDungeon.player.limbo));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        } else {
            tmp.applyPowers();
            if(tmp.target == AbstractCard.CardTarget.ENEMY){
                tmp.target = RANDOMIZATION_WORKAROUND;
            }
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(tmp, this.target));
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(tmp));
        }
        if (amount > 1) {
            AbstractDungeon.actionManager.addToBottom(new RandomizationAction(x, y, amount - 1, target, upgrade));
        }
        this.isDone = true;
    }
}
