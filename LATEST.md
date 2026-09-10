# Portals v1.0.1 Release Notes

## Fixes

- **Fresh install crash** — loading portals on a fresh install no longer throws a
  `NullPointerException` when `savedPortals.json` is empty. The loader now treats
  an empty or missing file as "no portals" and logs cleanly.

## Compatibility

- **Paper 26.2+** and **Java 25+** (was: Paper 1.20+ / Java 21)
- Built against the Paper 26.2 API; still requires ProtocolLib

## Installation

1. Download `portals-1.0.1.jar`
2. Place in your server's `plugins/` folder
3. Restart your server

---

For full configuration documentation, see [docs/PORTALS.md](docs/PORTALS.md)
