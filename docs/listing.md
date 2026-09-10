# Portals

Custom portals for Paper servers. Build a frame out of whatever block you
want, light it, and step through to any world on your server: the nether, the
end, a resource world, or a dimension the plugin creates for you.

Vanilla portals take obsidian and go where vanilla sends them. Portals lets
you pick the frame material, the size limits, the ignition item, and the
destination. Each portal type is one small YAML file.

## How it works

You describe a portal type in a config file: the frame block, the inside
material, the ignition item, the destination world. Players build the frame in
game and light it with the item you chose. The config file's name is the
portal's id.

## What it does

- Any frame material: cobblestone, stone bricks, quartz, whatever fits the build
- Any size, with minimum and maximum limits you set
- Any ignition item: flint and steel, a fire charge, or a custom key
- Any destination: nether, end, resource worlds, or a world the plugin creates on the spot (`World.Create: true`)
- Return portals built automatically at the destination
- Mobs and dropped items travel through too, with optional transformation (cows stepping into a nether portal becoming zombified piglins, that kind of thing)
- `/portals reload` applies config changes without a restart
- A GUI browser so players can see which portals exist

## Getting along with other plugins

Portals reads the world list from the server, so it works alongside whatever
manages your worlds, or nothing at all. Since 1.1.0 a portal can create the
world it points at, with its own seed and gamerules, so you don't need a
world manager just to have somewhere for the portal to go.

It also plays fine with WorldGuard, WorldEdit, Vault, BentoBox, skyblock
plugins, and custom item plugins.

## Requirements

- Paper (or Spigot) 26.2 or newer
- Java 25 or newer
- [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)

## Setup

1. Drop the jar in your `plugins` folder and restart the server.
2. Edit the portal configs under `plugins/Portals/Portals/`.
3. Build a frame and light it.
