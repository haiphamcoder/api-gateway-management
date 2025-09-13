#!/usr/bin/env bash
# shellcheck disable=SC2046
BASE_DIR=$(dirname $(readlink -e $0))/..

LOG_LEVEL=info

JAVA_HEAP_MAX=-Xmx16384m

MAIN_JAR=$(find $BASE_DIR/target -maxdepth 1 -name "*.jar" -not -name "*sources.jar" | head -1)

while (true); do
    java --add-opens java.base/java.nio=ALL-UNNAMED \
        -Dlogging.level.root=$LOG_LEVEL \
        -Dlog4j2.formatMsgNoLookups=true \
        -XX:ParallelGCThreads=20 \
        -XX:-UseGCOverheadLimit \
        -XX:GCTimeRatio=5 \
        -jar $MAIN_JAR
    echo "Sleeping 2 seconds before restarting..."
    sleep 2
done
