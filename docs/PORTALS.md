# Portals Configuration Guide

This document explains all available configuration options for custom portals.

## Portal Configuration Files

Portal configurations are stored as YAML files in `plugins/Portals/Portals/` on your server.

## Configuration Reference

### Basic Portal Structure

```yaml
Portal:
  Frame:
    Material: OBSIDIAN
  InsideMaterial: NETHER_PORTAL
  
World:
  Name: world_the_end
```

### Full Configuration Options

| Setting | Type | Default | Description |
|---------|------|---------|-------------|
| `Portal.Frame.Material` | Material | OBSIDIAN | Material for portal frame |
| `Portal.InsideMaterial` | Material | NETHER_PORTAL | Material inside the portal |
| `Portal.LighterMaterial` | Material | FLINT_AND_STEEL | Item to ignite portal (or "null" for any) |
| `Portal.MinWidth` | Integer | 1 | Minimum portal width |
| `Portal.MaxWidth` | Integer | 4 | Maximum portal width |
| `Portal.MinHeight` | Integer | 3 | Minimum portal height |
| `Portal.MaxHeight` | Integer | 5 | Maximum portal height |
| `World.Name` | String | (required) | Destination world name |
| `World.SpawnOffset.X` | Integer | 0 | X offset from spawn point |
| `World.SpawnOffset.Y` | Integer | 0 | Y offset from spawn point |
| `World.SpawnOffset.Z` | Integer | 0 | Z offset from spawn point |
| `Options.AllowedWorlds` | List | [] | Worlds where this portal can be built |
| `Options.RequireExactSize` | Boolean | false | Require exact size match |
| `Options.CanCreateExitPortal` | Boolean | true | Generate exit portal at destination |
| `Options.ExitPortalMaterial` | Material | END_PORTAL | Material for exit portal |
| `Transform.EntityTransformation` | String | null | Entity transformation type |
| `Spawns` | List | [] | Entities to spawn in portal |

## Example Configurations

### End Portal

```yaml
Portal:
  Frame:
    Material: OBSIDIAN
  InsideMaterial: NETHER_PORTAL
  LighterMaterial: FLINT_AND_STEEL
  
World:
  Name: world_the_end
  
Options:
  AllowedWorlds:
    - world
    - world_nether
```

### Resource World Portal (Cobblestone)

```yaml
Portal:
  Frame:
    Material: COBBLESTONE
  InsideMaterial: NETHER_PORTAL
  LighterMaterial: FLINT_AND_STEEL
  
World:
  Name: resource_world
  
Options:
  AllowedWorlds:
    - world
  CanCreateExitPortal: true
  ExitPortalMaterial: END_PORTAL
```

### Nether Portal Alternative

```yaml
Portal:
  Frame:
    Material: GLOWSTONE
  InsideMaterial: NETHER_PORTAL
  LighterMaterial: null  # Any ignition source
  
World:
  Name: custom_nether
  
Options:
  RequireExactSize: false
  CanCreateExitPortal: false
```

## Commands

### Player Commands
- `/portals` - Open portal browser GUI
- `/portals help` - Show help information

### Admin Commands
- `/portals reload` - Reload all portal configurations
- `/portals worlds` - List all available world names
- `/portals portal [name]` - Show information about a portal
- `/portals clear <all/world/portal>` - Delete portal data

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `portals.use` | Use custom portals | All players |
| `portals.command` | Use portal commands | All players |
| `portals.admin` | Admin commands | Operators |
