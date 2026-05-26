package vao211.somethingsaddons.config;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public class SomethingsAddonsConfig extends MidnightConfig {
    @Entry(category = "protection")
    public static boolean protectCreativePlayer = true;
    @Entry(category = "protection")
    public static boolean globalBedrockProtection = true;
    @Entry(category = "protection")
    public static boolean enableCustomBlockProtection = true;
    @Entry(category = "protection")
    public static List<String> protectedBlocks = List.of();


    @Entry(category = "gating")
    public static boolean applyDmgGating = true;
    @Entry(category = "gating", min = 1.0)
    public static double hpToApplyDmgGating = 200.0D;
    @Entry(category = "gating", min = 0.01, max = 1.0)
    public static double maxDamagePercentPerHit = 0.1D; // 0.1 = 10%

    @Entry(category = "gating")
    public static boolean applyHealGating = false;
    @Entry(category = "gating", min = 0.01, max = 1.0)
    public static double maxHealPercentPerSecond = 0.05D;
    @Entry(category = "gating")
    public static boolean applyBossKnockbackImmunity = true;
    @Entry(category = "gating", min = 1.0)
    public static double hpToApplyKnockbackImmunity = 150.0D;

    @Entry(category = "limiter")
    public static boolean enableEnchantmentLimit = false;

    @Entry(category = "limiter", min = 1)
    public static int maxEnchantmentsPerItem = 10;

    @Entry(category = "wither")
    public static boolean enableDangerBoss = true;
    @Entry(category = "wither")
    public static boolean witherNoBlockDrops = true;
    // Phase 1
    @Entry(category = "wither", min = 1.0)
    public static double witherBaseHealth = 300.0D;
    @Entry(category = "wither", min = 0.0)
    public static double witherBaseArmor = 10.0D;
    @Entry(category = "wither", min = 0.0)
    public static double witherBaseDamage = 10.0D;
    // Phase 2
    @Entry(category = "wither", min = 1.0)
    public static double witherPhase2Threshold = 150.0D;
    @Entry(category = "wither", min = 0.0)
    public static double witherPhase2Armor = 16.0D;
    @Entry(category = "wither", min = 0.0)
    public static double witherPhase2Damage = 14.0D;
    @Entry(category = "wither", min = 0.0, max = 1.0)
    public static double witherArmorPiercing = 0.3D;
    // Wither Skull
    @Entry(category = "wither", min = 1.0)
    public static double witherSkullDirectDamage = 9.0D;
    @Entry(category = "wither", min = 0.0)
    public static double witherSkullExplosionPower = 2.0D;
}