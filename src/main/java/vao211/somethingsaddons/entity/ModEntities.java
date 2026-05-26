package vao211.somethingsaddons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vao211.somethingsaddons.Somethingsaddons;

public class ModEntities {
    public static final EntityType<CustomDragonFireballEntity> CUSTOM_DRAGON_FIREBALL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Somethingsaddons.MOD_ID, "custom_dragon_fireball"),
            EntityType.Builder.<CustomDragonFireballEntity>create(CustomDragonFireballEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0F, 1.0F)
                    .build()
    );
    public static void register() {
    }
}