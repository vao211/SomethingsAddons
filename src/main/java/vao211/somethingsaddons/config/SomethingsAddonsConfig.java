package vao211.somethingsaddons.config;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class SomethingsAddonsConfig extends MidnightConfig {
    @Entry(category = "protection")
    public static boolean protectCreativePlayer = true;
    @Entry(category = "protection")
    public static boolean globalBedrockProtection = true;
    @Entry(category = "protection")
    public static boolean enableCustomBlockProtection = true;
    @Entry(category = "protection")
    public static List<String> protectedBlocksFromBoss = new ArrayList<>();


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


    //ENDER DRAGON
    @Entry(category = "ender_dragon")
    public static boolean enableDangerDragon = true;
    @Entry(category = "ender_dragon", min = 1.0)
    public static double enderDragonBaseHealth = 500.0D;
    @Entry(category = "ender_dragon", min = 0.0)
    public static double enderDragonBaseArmor = 15.0D;
    @Entry(category = "ender_dragon", min = 1)
    public static int dragonFireballCooldownTicks = 120;
    @Entry(category = "ender_dragon", min = 0.0, max = 1.0)
    public static double dragonFireballChance = 0.5D;
    @Entry(category = "ender_dragon", min = 0.0)
    public static double dragonFireballDirectDamage = 12.0D;
    @Entry(category = "ender_dragon", min = 0.0)
    public static double dragonFireballExplosionPower = 3.0D;
    @Entry(category = "ender_dragon", min = 0.0)
    public static double dragonBreathDamage = 4.0D;

    //Warden
    @Entry(category = "warden")
    public static boolean enableWardenBuff = true;

    @Entry(category = "warden", min = 1)
    public static int wardenMaxBuffTimes = 3;

    @Entry(category = "warden", min = 1)
    public static int wardenBuffTimeThreshold = 30; //Seconds

    @Entry(category = "warden")
    public static boolean limitWardenDropsPerDay = true;

    //Config Min DMG and Max Healing
    @Entry(category = "config_dmg_and_healing")
    public static boolean enableDmgAndHealingControl = false;
    @Entry(category = "config_dmg_and_healing", min = 0.0)
    public static double minDmgTakenForPlayer = 0.0D;
    @Entry(category = "config_dmg_and_healing", min = 0.0)
    public static double minDmgTakenForMob = 0.0D;
    @Entry(category = "config_dmg_and_healing", min = 0.0)
    public static double maxHealingForPlayer = 20.0D;
    @Entry(category = "config_dmg_and_healing", min = 0.0, max = 1000000.0)
    public static double maxHealingForMob = 1000000.0D;
    @Entry(category = "config_dmg_and_healing")
    public static boolean enableAntiHealDebuff = true;
    @Entry(category = "config_dmg_and_healing")
    public static boolean enableMinDmgScalingWithBossHp = true;
}