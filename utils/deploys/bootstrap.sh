#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
cd /server/bin
chmod +x server
ldd /server
sudo ./server
