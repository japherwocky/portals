# Portals

A Minecraft Paper/Spigot plugin for creating custom portals that link to any world or dimension.

## Features

- Create portals with custom frame materials (obsidian, cobblestone, etc.)
- Link portals to any world
- Configurable portal sizes
- Custom ignition items
- Entity transformation support
- Exit portal generation

## Building

```bash
./apache-maven-3.9.12/bin/mvn package
```

The built JAR will be in `target/portals-*.jar`

## Installation

1. Drop `portals-*.jar` into your server's `plugins/` folder
2. Restart the server
3. Configure your custom portals in `plugins/Portals/Portals/`

## Creating Custom Portals

Portal configurations are stored as YAML files in `./plugins/Portals/Portals/` on your server.

### Example Configs

This repository includes example portal configs:

- **[example.yml](https://github.com/japherwocky/portals/blob/main/src/main/resources/Portals/example.yml)** - A comprehensive reference showing all available options with comments
- **[end_portal.yml](https://github.com/japherwocky/portals/blob/main/src/main/resources/Portals/end_portal.yml)** - A simple End Portal config linking to `world_the_end`
- **[resource_overworld.yml](https://github.com/japherwocky/portals/blob/main/src/main/resources/Portals/resource_overworld.yml)** - Cobblestone portal to a resource world

You can find these files in `src/main/resources/Portals/` in the source code.

To use these:
1. Copy the desired YAML file(s) to `./plugins/Portals/Portals/` on your server
2. Rename the file (the filename becomes the portal ID)
3. Edit the settings as needed
4. Run `/portals reload` or restart the server

### Quick Start

**1. Create a new world (using Multiverse-Core as an example):**
```bash
/mv create resource_world normal -g void
/mv setspawn resource_world
```

**2. Copy the example config:**
Copy `resource_overworld.yml` to `plugins/Portals/Portals/resource_overworld.yml`

**3. Build your portal:**
- Use cobblestone for the frame
- Ignite with flint and steel
- Step through to reach your resource world!

### Portal Options

| Setting | Description |
|---------|-------------|
| `Portal.Frame.Material` | Material for portal frame (e.g., OBSIDIAN, COBBLESTONE) |
| `Portal.InsideMaterial` | Material inside the portal (e.g., NETHER_PORTAL, END_PORTAL) |
| `Portal.LighterMaterial` | Item to ignite the portal (e.g., FLINT_AND_STEEL, or "null" for any) |
| `World.Name` | Destination world name |
| `Options.AllowedWorlds` | Worlds where this portal can be built |

## Commands

- `/portals` - Main portal commands
- `/portals reload` - Reload all portal configurations
- `/portals list` - List all configured portals
- `/portals create` - Open the portal creation GUI
- `/portals info <portal>` - Show portal information

## Configuration

Main config file: `plugins/Portals/config.yml`

Key settings:
- `searchRadius` - How far to search for portal frames
- `fallbackWorld` - Default destination for unknown portals
- `consumeItems` - Whether lighter items are consumed on use

## Addons

The plugin supports optional addons placed in `plugins/Portals/Addons/`. Addons are JAR files that extend the `PortalsAddon` class.

Commands for managing addons:
- `/portalsaddon list` - List loaded addons
- `/portalsaddon info <addon>` - Show addon details
