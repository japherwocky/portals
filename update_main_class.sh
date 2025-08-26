#!/bin/bash

# Find all Java files in the new package structure
find src/main/java/me/japherwocky/portals -type f -name "*.java" | while read file; do
  # Skip the main class files themselves
  if [[ "$file" == "src/main/java/me/japherwocky/portals/Dimensions.java" || "$file" == "src/main/java/me/japherwocky/portals/Portals.java" ]]; then
    continue
  fi
  
  echo "Processing $file"
  
  # Replace static method calls
  sed -i 's/Dimensions\.getInstance()/Portals.getInstance()/g' "$file"
  sed -i 's/Dimensions\.getCompletePortalManager()/Portals.getCompletePortalManager()/g' "$file"
  sed -i 's/Dimensions\.getCustomPortalManager()/Portals.getCustomPortalManager()/g' "$file"
  sed -i 's/Dimensions\.getAddonManager()/Portals.getAddonManager()/g' "$file"
  sed -i 's/Dimensions\.getCommandManager()/Portals.getCommandManager()/g' "$file"
  sed -i 's/Dimensions\.getCreatePortalManager()/Portals.getCreatePortalManager()/g' "$file"
  
  # Replace import statements
  sed -i 's/import me.japherwocky.portals.Dimensions;/import me.japherwocky.portals.Portals;/g' "$file"
  
  # Replace variable type declarations
  sed -i 's/Dimensions /Portals /g' "$file"
  sed -i 's/Dimensions$/Portals/g' "$file"
  sed -i 's/(Dimensions /(Portals /g' "$file"
  sed -i 's/, Dimensions /, Portals /g' "$file"
done

echo "Main class references updated!"

