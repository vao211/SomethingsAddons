*Read this in other languages:* [🇻🇳 Tiếng Việt](README-vi.md) | [🇬🇧 English](README.md)

# SomethingsAddons

A Minecraft Fabric mod adding various enhancements, brutal RPG mechanics, and utilities to your game.

## Features
- **Damage & Healing Control:** Adds damage gating and custom healing for mobs (highly recommended for bosses).
- **Anti-Godmode:** Prevents players from exploiting *Resistance 255* or extreme armor stacking. Every physical hit or boss skill guarantees a **Minimum Damage** threshold.
- **Boss Scaling:** Late-game encounters are now genuinely threatening. The higher the attacker's Max HP (100+), the higher the minimum damage they inflict (scaling up to 5x).
- **Anti-Heal Debuff:** Taking a hit from a Boss (>= 100 HP) reduces your healing and absorption effectiveness by 50% for 5 seconds. No more spamming Golden Apples!
- **Wither DangerBoss Mode:** Introduces a special configurable DangerBoss mode for the Wither.
- **Ender Dragon DangerBoss Mode:** Enhanced health, armor, and custom fireball attack mechanics.
- **Warden Overhaul:** The Warden now progressively enrages during combat, increasing in size and stats, while blasting players away with AoE Sonic Booms. Loot is strictly limited to 1 kill per in-game day.
- **Campfire Safezone (Bonfire):** Lit campfires act as healing stations, slowly restoring health to nearby players who are safely out of combat.
- **RPG Survival Mechanics:** Introduces immersive physical mechanics including **Armor Weight** (heavy armor slows you down but increases knockback resistance), **Sprained Ankles** (severe fall damage causes temporary disorientation), and **Overeating** (eat past full hunger to store saturation).
- **Pet Recall:** Never lose a dog again! Pets left too far behind (100+ blocks) will automatically teleport back to your bed or world spawn after 2 minutes (ignores sitting pets).
- **Block Protection:** Configurable protection to prevent blocks from being destroyed.
- **Enchantment Limiter:** Limit the maximum number of enchantments allowed per item.
- **Pickup Lock:** Integrated button in inventory GUI to toggle automatic item pickup.
- **Bug Fixes & Compatibility:** Patches issues with bosses from other mods, fixes bedrock-breaking exploits, and is 100% compatible with Shield blocking mechanics.

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

1. Clone this repository: `git clone https://github.com/vao211/SomethingsAddons.git`
2. Navigate to the folder: `cd SomethingsAddons`
3. Build the mod using Gradle: `./gradlew build`
4. The compiled `.jar` files will be located in the `build/libs/` directory.

## Notes
Highly recommended to play alongside **Better Combat** and **Marium's Soulike Weaponry** for the ultimate RPG experience!

## License
This project is licensed under the [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/) License.