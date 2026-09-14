## [Current Version] - 2026-09-14
### Added
* **Damage & Healing Control System:** Completely fixes the late-game "Godmode" issue caused by armor stacking or Resistance 255. All valid attacks will now deal a guaranteed minimum amount of damage (configurable).
* **Boss & Magic Whitelist:** Min Damage works flawlessly against armor-bypassing boss skills like Dragon Breath, Wither, Witch's Magic, and Warden's Sonic Boom.
* **Shield Compatibility:** Fixed an issue where blocked attacks or harmless projectiles (like snowballs) would trigger min damage. Shields work as intended!
* **Dynamic Min Damage Scaling:** The minimum damage taken by the player dynamically scales up (up to 5x) if the attacker is an elite mob or boss with 100+ Max HP.
* **Boss Anti-Heal Debuff:** Taking damage from mobs with 100+ Max HP applies a debuff that reduces all healing and absorption gained by 50% for 5 seconds. Includes an Action Bar warning.
* **Max Healing Cap:** Set a hard limit on how much HP can be recovered in a single healing instance. 
* **2026-9-14:**
* **Warden DangerBoss (Enrage):** Warden now features configurable base stats and an Enrage mechanic, gaining size, armor, damage, and emitting massive AoE knockback every 30 seconds.
* **Warden Loot Limiter:** Prevents infinite farming by limiting Warden drops to 1 per in-game Minecraft day.
* **Campfire Safezone:** Lit campfires will now slowly heal nearby players who have been out of combat for a configurable time.
* **Pet Recall System:** Tamed pets (not sitting) that are left behind (100+ blocks) will automatically teleport to the owner's bed or world spawn after 2 minutes.
* **Armor Weight Penalty:** Wearing heavy armor (20+ points) reduces movement speed by 2% per extra point, but grants equivalent knockback resistance.
* **Sprained Ankle:** Taking more than 4 hearts of fall damage inflicts severe Slowness and Nausea for 5 seconds.
* **Overeating (Gluttony):** Players can now eat food even with a full hunger bar to build up hidden saturation, preparing for tough boss fights.
* **Configurable:** Added full toggle options in the Config Menu for every individual mechanic.