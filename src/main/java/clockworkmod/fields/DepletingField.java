package clockworkmod.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DepletingField
{
    @SpirePatch(
            clz=AbstractCard.class,
            method=SpirePatch.CLASS
    )
    public static class DepletingFields
    {
        public static SpireField<Integer> depleting = new SpireField<>(() -> -1);
        public static SpireField<Integer> baseDepleting = new SpireField<>(() -> -1);
        public static SpireField<Boolean> isDepletingUpgraded = new SpireField<>(() -> false);
    }
}
