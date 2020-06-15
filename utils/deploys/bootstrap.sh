#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
#apk add --no-cache libstdc++  # get a missing library
#java --enable-preview -jar /app.jar
cd / && ls -la *
echo "1"
cd server && ls -la *
echo "2"
cd /server/bin
chmod +x server
./server
