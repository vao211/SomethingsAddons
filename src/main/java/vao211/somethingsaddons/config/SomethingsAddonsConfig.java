package vao211.somethingsaddons.config;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public class SomethingsAddonsConfig extends MidnightConfig {
    // --- PROTECT CREATIVE PLAYER ---
    @Entry
    public static boolean protectCreativePlayer = true;

    // --- DAMAGE GATING ---
    @Entry
    public static boolean applyDmgGating = true;

    @Entry(min = 1.0)
    public static double hpToApplyDmgGating = 200.0D;

    @Entry(min = 0.01, max = 1.0)
    public static double maxDamagePercentPerHit = 0.1D; // 0.1 = 10%

    // --- HEAL GATING ---
    @Entry
    public static boolean applyHealGating = false;
    @Entry(min = 0.01, max = 1.0)
    public static double maxHealPercentPerSecond = 0.05D;

    // --- KNOCKBACK IMMUNITY ---
    @Entry
    public static boolean applyBossKnockbackImmunity = true;

    @Entry(min = 1.0)
    public static double hpToApplyKnockbackImmunity = 150.0D;

    // --- GLOBAL BLOCK PROTECTION ---
    @Entry
    public static boolean globalBedrockProtection = true;

    // --- CUSTOM BLOCK PROTECTION---
    @Entry public static boolean enableCustomBlockProtection = true; // Bật/Tắt bảo vệ khối tùy chỉnh

    @Entry
    public static List<String> protectedBlocks = List.of();
    //"minecraft:chest"


    // --- DANGER BOSS: WITHER ---
    @Entry public static boolean enableDangerBoss = true;
    @Entry public static boolean witherNoBlockDrops = true;
    // Phase 1
    @Entry(min = 1.0) public static double witherBaseHealth = 300.0D;
    @Entry(min = 0.0) public static double witherBaseArmor = 10.0D;
    @Entry(min = 0.0) public static double witherBaseDamage = 10.0D;
    // Phase 2
    @Entry(min = 1.0) public static double witherPhase2Threshold = 150.0D;
    @Entry(min = 0.0) public static double witherPhase2Armor = 16.0D;
    @Entry(min = 0.0) public static double witherPhase2Damage = 14.0D;
    @Entry(min = 0.0, max = 1.0) public static double witherArmorPiercing = 0.3D;

    // --- WITHER SKULL BUFF ---
    @Entry(min = 1.0) public static double witherSkullDirectDamage = 9.0D;
    @Entry(min = 0.0) public static double witherSkullExplosionPower = 2.0D;
}
