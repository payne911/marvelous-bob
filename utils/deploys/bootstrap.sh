#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
#apk add --no-cache libstdc++  # get a missing library
#java --enable-preview -jar /app.jar
cd /server && ls -la *
echo "1"
cd bin && ls -la *
echo "2"
cd /server/bin && ls -la *
echo "3"
chmod +x server
echo "4"
./server
