# AGENTS.md - Portals Plugin

## Project Overview

**Portals** is a Minecraft Paper/Spigot server plugin that allows server administrators to create custom portal types that link to any world or dimension. Players can build custom portal frames using configurable materials (obsidian, cobblestone, etc.) that activate and teleport them to configured destination worlds.

## Technology Stack

### Build System
- **Maven** (3.9.12)
- **Maven Shade Plugin** - Creates uber-jar with all dependencies bundled

### Language & Runtime
- **Java** 
  - `pom.xml` specifies: Java 17
  - GitHub Actions CI uses: Java 25 (Temurin)
  - Note: Version mismatch between local config and CI

### Server Platform
- **Paper** (1.21.11-R0.1-SNAPSHOT) - Pre-release/snapshot version
- **Spigot** - Also supported (API version 1.20)
- **Minecraft Version**: ~1.21.11 (inferred from Paper version)

### Dependencies
| Dependency | Version | Purpose |
|------------|---------|---------|
| Paper API | 1.21.11-R0.1-SNAPSHOT | Minecraft server API |
| ProtocolLib | 5.4.0 | Packet manipulation for portal rendering |
| Lombok | 1.18.40 | Code generation (annotations) |
| Metrics (bStats) | Built-in | Anonymous usage statistics |

### Project Structure
```
src/main/java/me/japherwocky/portals/
├── Portals.java              # Main plugin class
├── PortalsDebbuger.java       # Debug logging utility (typo in name)
├── PortalsUtils.java          # Utility methods
├── AxisOrFace.java            # Portal axis/face enumeration
├── addons/                    # Addon system for extensibility
│   ├── PortalsAddon.java
│   ├── PortalsAddonManager.java
│   └── ...
├── builder/                   # Portal creation GUI system
├── commands/                  # Command handlers
├── completePortal/            # Runtime portal instances
├── customportal/              # Portal type configurations
├── events/                    # Custom Bukkit events
├── gui/                       # GUI components
├── listener/                  # Event listeners
└── settings/                  # Configuration management

src/main/resources/
├── plugin.yml                 # Spigot plugin metadata
├── paper-plugin.yml           # Paper plugin metadata
└── Portals/                   # Example portal configs
    ├── example.yml
    ├── end_portal.yml
    └── resource_overworld.yml
```

## Configuration

### Plugin Metadata
- **Plugin ID**: `Portals`
- **Main Class**: `me.japherwocky.portals.Portals`
- **Version**: 1.0.0

### Dependencies (from paper-plugin.yml)
- **Required**: ProtocolLib
- **Optional**: Multiverse-Core, WorldEdit, WorldGuard, Vault, BentoBox, IridiumSkyblock, SuperiorSkyblock2, Oraxen, ItemsAdder, CustomItems, ModelEngine, LightAPI, BetterPortals

### Portal Configuration
Portal types are defined as YAML files in `./plugins/Portals/Portals/` with options for:
- Frame material, inside material, ignition item
- Destination world
- Portal dimensions (min/max height/width)
- Exit portal generation
- Entity transformations
- Entity spawning within portals

## Quirks & Irregularities

### Code Issues
1. **Typo in class name**: `PortalsDebbuger` (double 'b') - used extensively throughout 53+ locations in the codebase
2. **Debug print statement**: In `WrapperPlayServerEntityMetadata.java` line 132, there's a debug print `PortalsDebbuger.DEBUG.print("tttt");` that appears to be leftover debug code

### Version Mismatches
1. **Java Version**: `pom.xml` specifies Java 17, but `.github/workflows/build.yml` uses Java 25
2. **Paper Snapshot**: Uses `1.21.11-R0.1-SNAPSHOT` which is a pre-release version - may break without notice

### Architectural Decisions
1. **Bundled Packet Wrappers**: ProtocolLib packet wrapper classes are included directly in source (`com.comphenix.packetwrapper.*`) rather than depending on the full ProtocolLib library
2. **Uber-Jar Build**: Uses Maven Shade to bundle all dependencies into a single JAR
3. **Reflection-based Config**: `PortalsSettings.java` uses Java reflection to auto-generate config fields from class fields
4. **Deferred Portal Loading**: Portals load 1 tick after plugin enable to ensure all worlds are loaded first
5. **Static Instance Pattern**: Uses classic static singleton pattern (`Portals.getInstance()`)

### Unused/Orphaned Files
- `.gitignore` references `dimensions/target/` and `test/` directories that don't exist in the repository

## Building

```bash
# Using Maven directly
mvn clean package

# The built JAR will be in target/portals-1.0.0.jar
```

## CI/CD

- GitHub Actions workflow: `.github/workflows/build.yml`
- Runs on push to main and pull requests
- Uses Maven 3.9.12 and Java 25 (Temurin)
- Uploads built JAR as artifact on pull requests
