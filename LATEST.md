# Portals v1.0.0 Release Notes

## Features

### Custom Portal Frames
Create portals with any frame material - obsidian, cobblestone, stone bricks, or any block you choose.

### Link to Any World
Configure portals to teleport players to any world, including:
- The End
- Nether dimensions
- Resource worlds
- Custom multiplayer worlds

### Configurable Portal Sizes
Set minimum and maximum dimensions for your portals to create unique designs.

### Custom Ignition Items
Choose which item ignites each portal type - flint and steel, fire charges, or allow any ignition source.

### Entity Transformation
Automatically transform entities when they pass through portals (e.g., mobs entering a nether portal).

### Exit Portal Generation
Automatically generate return portals at the destination world.

### GUI Portal Browser
Players can browse available portals through an in-game GUI.

### Full Reload Support
Reload portal configurations without restarting the server.

## Commands

- `/portals` - Open portal browser GUI
- `/portals help` - List all commands
- `/portals info` - Show plugin version
- `/portals reload` - Reload portal configurations
- `/portals worlds` - List available world names
- `/portals portal [name]` - Show portal info
- `/portals clear <all/world/portal>` - Delete saved portals

## Permissions

- `portals.use` - Use custom portals
- `portals.command` - Use portal commands
- `portals.admin` - Admin commands and configuration

## Server Requirements

* **Minecraft Version**: 1.21+
* **Server Software**: Paper or Spigot
* **Java Version**: 21+
* **Dependency**: ProtocolLib (required)

---

For full configuration documentation, see [docs/PORTALS.md](docs/PORTALS.md)
