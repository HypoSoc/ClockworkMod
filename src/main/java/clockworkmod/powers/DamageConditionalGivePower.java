package clockworkmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface DamageConditionalGivePower {
    float atDamageConditionalGive(float damage, AbstractMonster mo, DamageInfo.DamageType type);

    float atFinalDamageConditionalGive(float damage, AbstractMonster mo, DamageInfo.DamageType type);
}
