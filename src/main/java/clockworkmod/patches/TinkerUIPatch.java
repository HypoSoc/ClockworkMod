package clockworkmod.patches;

import clockworkmod.cards.AbstractClockworkCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

@SpirePatch(
        clz= AbstractCard.class,
        method="renderTitle"
)
public class TinkerUIPatch
{
    @SpireInsertPatch(
            rloc=64,
            localvars={"font"}
    )
    public static SpireReturn infix(AbstractCard card, SpriteBatch sb, BitmapFont font)
    {
        if(card instanceof AbstractClockworkCard &&
                ((AbstractClockworkCard)card).tinkerIncrementor > 0) {
            Color color = Settings.BLUE_TEXT_COLOR.cpy();
            color.a = Color.WHITE.cpy().a;
            FontHelper.renderRotatedText(sb, font, card.name, card.current_x, card.current_y, 0.0F, 175.0F * card.drawScale * Settings.scale, card.angle, false, color);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
