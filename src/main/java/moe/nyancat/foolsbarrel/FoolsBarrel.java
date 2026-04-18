package moe.nyancat.foolsbarrel;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoolsBarrel implements ModInitializer {
    public static final String MOD_ID = "foolsbarrel";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.BARREL, builder -> {
                builder.set(DataComponents.EQUIPPABLE,
                        Equippable.builder(EquipmentSlot.HEAD)
                                .setEquipSound(net.minecraft.core.Holder.direct(SoundEvents.BARREL_OPEN))
                                .build());
            });
        });

        LOGGER.info("FoolsBarrel loaded!");
    }
}
