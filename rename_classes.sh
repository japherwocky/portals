#!/bin/bash

# Create a new PortalsDebbuger class
cp src/main/java/me/japherwocky/portals/DimensionsDebbuger.java src/main/java/me/japherwocky/portals/PortalsDebbuger.java
sed -i 's/DimensionsDebbuger/PortalsDebbuger/g' src/main/java/me/japherwocky/portals/PortalsDebbuger.java

# Create a new PortalsUtils class
cp src/main/java/me/japherwocky/portals/DimensionsUtils.java src/main/java/me/japherwocky/portals/PortalsUtils.java
sed -i 's/DimensionsUtils/PortalsUtils/g' src/main/java/me/japherwocky/portals/PortalsUtils.java

# Create a new PortalsSettings class
cp src/main/java/me/japherwocky/portals/settings/DimensionsSettings.java src/main/java/me/japherwocky/portals/settings/PortalsSettings.java
sed -i 's/DimensionsSettings/PortalsSettings/g' src/main/java/me/japherwocky/portals/settings/PortalsSettings.java

# Update references in the Portals class
sed -i 's/DimensionsDebbuger/PortalsDebbuger/g' src/main/java/me/japherwocky/portals/Portals.java
sed -i 's/DimensionsSettings/PortalsSettings/g' src/main/java/me/japherwocky/portals/Portals.java

echo "Classes renamed!"

