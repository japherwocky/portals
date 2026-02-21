# Portals

This is a fork of the [Dimensions3](https://www.spigotmc.org/resources/dimensions-custom-portals.83982/) plugin (which appears to be abandoned), restructured and maintained by japherwocky.

## Original Resources
- [Spigot page](https://www.spigotmc.org/resources/dimensions-custom-portals.83982/)
- [Wiki](https://astaspastagam.gitbook.io/first-steps/)
- [Javadocs](https://astaspasta.alwaysdata.net/javadocs/)
- [Free Addons](https://github.com/xXastaspastaXx/Dimensions-3-Free-Addons)

## Changes in this Fork
- Restructured to standard Maven project layout
- Updated to use Paper plugin format
- Renamed from "Dimensions3" to "Portals"
- Maintained compatibility with original plugin configuration

## Creating Custom Portals

Portal configurations are stored as YAML files in `./plugins/Portals/Portals/` on your server.

### Example Configs

This repository includes example portal configs in `src/main/resources/Portals/`:

- **`example.yml`** - A comprehensive reference showing all available options with comments
- **`end_portal.yml`** - A simple End Portal config linking to `world_the_end`

To use these:
1. Copy the desired YAML file(s) to `./plugins/Portals/Portals/` on your server
2. Rename the file (the filename becomes the portal ID)
3. Edit the settings as needed
4. Run `/portals reload` or restart the server

### Portal Options

Key configuration options:

| Setting | Description |
|---------|-------------|
| `Portal.Frame.Material` | Material for portal frame (e.g., OBSIDIAN, DIAMOND_BLOCK) |
| `Portal.InsideMaterial` | Material inside the portal (e.g., NETHER_PORTAL) |
| `Portal.LighterMaterial` | Item to ignite the portal (e.g., FLINT_AND_STEEL) |
| `World.Name` | Destination world name |
| `Options.AllowedWorlds` | Worlds where this portal can be built |
