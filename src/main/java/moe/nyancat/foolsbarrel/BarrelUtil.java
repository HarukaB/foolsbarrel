package moe.nyancat.foolsbarrel;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class BarrelUtil {
    private BarrelUtil() {}

    public static boolean isWearingBarrel(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
    }

    public static boolean isHiddenInBarrel(LivingEntity entity) {
        return isWearingBarrel(entity) && entity.isCrouching();
    }

    public static boolean isWearingBarrel(ItemStack headEquipment) {
        return headEquipment.is(Items.BARREL);
    }
}
