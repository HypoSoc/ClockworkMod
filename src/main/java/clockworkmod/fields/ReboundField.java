package clockworkmod.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method=SpirePatch.CLASS
)
public class ReboundField {
    public static final int NO_REBOUND = -3;
    public static final int ALWAYS_REBOUND = -2;
    public static final int REBOUND_EXACTLY_ONCE = -1;
    public static final int REBOUND_ONCE_PER_TURN = 0;

    public static SpireField<Integer> rebound = new SpireField<>(() -> NO_REBOUND);
}
