package net.kaffssist.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.BeesComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.*;

@Mixin(BundleContentsComponent.class)
public class BCC {
    @Final @Shadow
    private static Fraction NESTED_BUNDLE_OCCUPANCY;

    @Overwrite
    public static Fraction getOccupancy(ItemStack stack) {
        var nested = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (nested != null) return NESTED_BUNDLE_OCCUPANCY.add(nested.getOccupancy());

        var bees = stack.getOrDefault(DataComponentTypes.BEES, BeesComponent.DEFAULT);
        if (!bees.bees().isEmpty()) return Fraction.ONE;
        
        if (stack.getMaxCount() == 1) return Fraction.getFraction(1, 6);

        return Fraction.getFraction(1, stack.getMaxCount());
    }
}