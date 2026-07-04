*Read this in other languages:* [🇻🇳 Tiếng Việt](README-vi.md) | [🇬🇧 English](README.md)

# SomethingsAddons

A Minecraft Fabric mod adding various enhancements and utilities to your game.

## Features
- **Damage & Healing Control:** Adds damage gating and custom healing for mobs (highly recommended for bosses).
- **Wither DangerBoss Mode:** Introduces a special configurable DangerBoss mode for the Wither.
- **Ender Dragon DangerBoss Mode:** Enhanced health, armor, and custom fireball attack mechanics.
- **Block Protection:** Configurable protection to prevent blocks from being destroyed.
- **Bug Fixes & Compatibility:** Patches issues with bosses from other mods and fixes bedrock-breaking exploits.
- **Pickup Lock:** Integrated button in inventory GUI to toggle automatic item pickup.
- **Enchantment Limiter:** Limit the maximum number of enchantments allowed per item.
- **Anti-Godmode:** Prevents players from exploiting *Resistance 255* or extreme armor stacking. Every physical hit or boss skill (Warden, Wither, Ender Dragon) guarantees a **Minimum Damage** threshold.
- **Boss Scaling:** Late-game encounters are now genuinely threatening. The higher the attacker's Max HP (100+), the higher the minimum damage they inflict (scaling up to 5x).
- **Anti-Heal Debuff:** Taking a hit from a Boss (>= 100 HP) reduces your healing and absorption effectiveness by 50% for 5 seconds. No more spamming Golden Apples to out-heal boss damage!
- **Vanilla Safe:** 100% compatible with Shield blocking mechanics and environmental damage (Fire, Drowning, Void).

## Requirements
To play with this mod, you need the following installed:
* **Minecraft:** `1.21.1`
* **Fabric Loader:** `>=0.19.2`
* **Fabric API:** Required

## Installation
1. Ensure you have installed [Fabric Loader](https://fabricmc.net/) for Minecraft 1.21.1.
2. Download the appropriate version of [Fabric API](https://modrinth.com/mod/fabric-api).
3. Download the latest release of SomethingsAddons.
4. Place both the Fabric API and SomethingsAddons `.jar` files into your `.minecraft/mods` folder.
5. Launch the game using the Fabric profile.

## For Developers
If you wish to contribute or build the mod from the source code:

1. Clone this repository:
   ```bash
   git clone https://github.com/vao211/SomethingsAddons.git
   cd SomethingsAddons
   ```
2. Build the mod using Gradle:
   ```bash
   ./gradlew build
   ```
   The compiled `.jar` files will be located in the `build/libs/` directory.
## Notes
Recommend using with Better Combat and Marium's Soulike Weaponry
## License
This project is licensed under the [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/) License.
