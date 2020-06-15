#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
#apk add --no-cache libstdc++  # get a missing library
#java --enable-preview -jar /app.jar
chmod +x /server/server
./server/server