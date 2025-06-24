#!/bin/sh

source venv/bin/activate
OK=0
for file in tests/*; do
    python3 "$file"
    CURR_CODE=$?
    if [ "$OK" = 0 ]; then
        OK="$CURR_CODE"
    fi
done
exit $OK
