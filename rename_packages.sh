#!/bin/bash

# Create the new directory structure
mkdir -p src/main/java/me/japherwocky/portals

# Copy all files from the old package to the new one
cp -r src/main/java/me/xxastaspastaxx/dimensions/* src/main/java/me/japherwocky/portals/

# Replace package declarations in all Java files
find src/main/java/me/japherwocky/portals -type f -name "*.java" -exec sed -i 's/package me.xxastaspastaxx.dimensions/package me.japherwocky.portals/g' {} \;

# Replace import statements in all Java files
find src/main/java/me/japherwocky/portals -type f -name "*.java" -exec sed -i 's/import me.xxastaspastaxx.dimensions/import me.japherwocky.portals/g' {} \;

# Add the new files to git
git add src/main/java/me/japherwocky/portals

# We'll keep the old files for now to avoid compilation errors
# They can be removed after everything is working

