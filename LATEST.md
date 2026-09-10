# Portals v1.1.0 Release Notes

## Features

### Portals create their own worlds
A portal can now bring its target dimension into existence. In the portal
config:

```yaml
World:
  Name: funworld
  Create: true
  Environment: NORMAL
  GenerateStructures: true
  Seed: ""            # optional; empty = random
  Generator: ""       # optional; custom generator id
  Gamerules:
    minecraft:keep_inventory: true
```

- `Create: false` (the default) keeps the old behaviour: the world must
  already exist.
- Gamerules are (re-)applied on **every** boot, so a world always matches its
  config — vanilla only persists gamerules in the world's `level.dat`, which
  a fresh install does not have.
- Keys may use the namespaced form (`minecraft:keep_inventory`) or the bare
  name.

### Per-world gamerules in the main config
The main `config.yml` gained the same power for worlds that are not portal
targets:

```yaml
Worlds:
  world:
    Gamerules:
      minecraft:keep_inventory: true
```

## Removed

- The optional Multiverse-Core dependency. Portals never called it — it
  resolves worlds through the Bukkit API — and servers that used Multiverse
  only to create a portal's target world no longer need it.

## Compatibility

- **Paper 26.2+** and **Java 25+**
- Still requires ProtocolLib
- No config changes required; new behaviour is opt-in via `World.Create`

## Installation

1. Download `portals-1.1.0.jar`
2. Place in your server's `plugins/` folder
3. Restart your server

---

For full configuration documentation, see [docs/PORTALS.md](docs/PORTALS.md)
