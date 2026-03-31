# Portals

A straightforward portal engine for Paper/Spigot that allows you to link any world or dimension on your server. Unlike vanilla portals, these are fully customizable via config files, allowing you to define the frame material, size, and ignition item.

## Core Functionality

* **World Linking:** Connect any worlds managed by your server, including **Multiverse-Core** worlds, resource worlds, or custom dimensions.
* **Player-Built Frames:** Players can build their own portal frames out of any block you specify in the config (Stone Bricks, Quartz, Cobblestone, etc.).
* **Config-Driven:** Everything is handled in `plugins/Portals/portals.yml`. You define the "rules" for a portal type, and players simply build the shape to activate it.
* **Flexible Sizing:** Supports custom dimensions from 1x2 "doors" up to massive 5x5 gates.
* **Custom Ignition:** Set specific items (like a Fire Charge or a custom key) to open the portal.

## Admin Features

* **In-Game Browser:** A simple GUI for players to see which portals are available to them.
* **Entity Support:** Handles mobs and entities passing through, with optional transformation settings.
* **No Restarts:** Use `/portals reload` to update your portal configurations on the fly.
* **Auto-Generation:** Option to automatically create a "return" portal at the destination coordinate.

## Compatibility

Works alongside the standard server stack:
* **Management:** Multiverse-Core, BentoBox, Iridium/SuperiorSkyblock.
* **Tools:** WorldGuard, WorldEdit, Vault.
* **Custom Items:** Oraxen, ItemsAdder, ModelEngine.

## Technical Requirements

* **Minecraft:** 1.21+
* **Java:** 21+
* **Dependency:** [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)

## Setup

1. Drop `Portals.jar` into your `/plugins` folder.
2. Restart the server to generate the default configuration.
3. Modify `plugins/Portals/portals.yml` to set your frame materials and destination worlds.
4. Build the frame in-game and ignite it.