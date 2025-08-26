#!/bin/bash

# Find all Java files in the new package structure
find src/main/java/me/japherwocky/portals -type f -name "*.java" | while read file; do
  echo "Processing $file"
  
  # Check if the file has imports from the old package
  if grep -q "import me.xxastaspastaxx.dimensions" "$file"; then
    # Replace old package imports with new package imports
    sed -i 's/import me.xxastaspastaxx.dimensions/import me.japherwocky.portals/g' "$file"
    echo "  Updated imports in $file"
  fi
done

echo "Import fixes completed!"

