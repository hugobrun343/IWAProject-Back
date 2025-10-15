#!/bin/sh
set -e

echo "🔍 Discovering Keycloakify themes..."

# Find all keycloakify-theme-* directories
for theme_dir in keycloakify-theme-*; do
    if [ -d "$theme_dir" ] && [ -f "$theme_dir/package.json" ]; then
        echo "📦 Building theme: $theme_dir"
        cd "$theme_dir"
        
        # Install dependencies
        if [ -f "yarn.lock" ]; then
            echo "  📥 Installing dependencies with yarn..."
            yarn install --frozen-lockfile
        elif [ -f "package-lock.json" ]; then
            echo "  📥 Installing dependencies with npm ci..."
            npm ci
        else
            echo "  📥 Installing dependencies with npm install..."
            npm install
        fi
        
        # Build the theme
        echo "  🔨 Building theme..."
        npm run build-keycloak-theme
        
        # Move the built JAR to a common location with theme-specific names
        if [ -d "dist_keycloak" ]; then
            mkdir -p ../built-themes
            for jar_file in dist_keycloak/*.jar; do
                if [ -f "$jar_file" ]; then
                    # Extract the filename without path
                    filename=$(basename "$jar_file")
                    # Create theme-specific filename
                    theme_specific_name="${theme_dir}-${filename}"
                    cp "$jar_file" "../built-themes/${theme_specific_name}"
                fi
            done
            echo "  ✅ Theme $theme_dir built successfully"
        else
            echo "  ⚠️  Warning: No dist_keycloak directory found for $theme_dir"
        fi
        
        cd ..
    fi
done

echo "🎉 All themes built successfully!"
echo "📁 Built themes are available in ./built-themes/"