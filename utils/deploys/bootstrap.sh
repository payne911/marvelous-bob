#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
#apk add --no-cache libstdc++ libc6-compat
#apk add --no-cache libstdc++  # get a  missing library
#java --enable-preview -jar /app.jar
cd /server/bin
chmod +x server
./server
