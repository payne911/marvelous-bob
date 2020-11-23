#!/usr/bin/env sh
echo "========== LAUNCHING SERVER APP ========="
apk add --no-cache libstdc++
java --enable-preview -XX:+ShowCodeDetailsInExceptionMessages -jar /app.jar
