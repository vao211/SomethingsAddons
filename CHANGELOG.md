## [Current Version] - 2026-07-04
### Added
* **Damage & Healing Control System:**
    * **Minimum Damage Enforcer:** Completely fixes the late-game "Godmode" issue caused by armor stacking or Resistance 255. All valid attacks will now deal a guaranteed minimum amount of damage (configurable).
    * **Boss & Magic Whitelist:** Min Damage works flawlessly against armor-bypassing boss skills like Dragon Breath, Wither, Witch's Magic, and Warden's Sonic Boom.
    * **Shield Compatibility:** Fixed an issue where blocked attacks or harmless projectiles (like snowballs) would trigger min damage. Shields work as intended!
    * **Dynamic Min Damage Scaling:** The minimum damage taken by the player dynamically scales up (up to 5x) if the attacker is an elite mob or boss with 100+ Max HP.
    * **Boss Anti-Heal Debuff:** Taking damage from mobs with 100+ Max HP applies a debuff that reduces all healing and absorption gained by 50% for 5 seconds. Includes an Action Bar warning.
    * **Max Healing Cap:** Set a hard limit on how much HP can be recovered in a single healing instance (prevents excessive healing from other mods).
    * **Added full toggle options in the Config Menu for every individual mechanic.**
    * 