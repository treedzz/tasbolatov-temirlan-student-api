#!/bin/bash
# System report script - Tasbolatov Temirlan
# Usage: ./scripts/Tasbolatov_Temirlan_system_report.sh

STUDENT_NAME="Tasbolatov Temirlan"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOG_FILE="$SCRIPT_DIR/../logs/Tasbolatov_Temirlan_system_report.log"

mkdir -p "$(dirname "$LOG_FILE")"

{
    echo "=========================================="
    echo " SYSTEM REPORT"
    echo "=========================================="
    echo "Student:        $STUDENT_NAME"
    echo "Date and time:  $(date '+%Y-%m-%d %H:%M:%S')"
    echo "Username:       $(whoami)"
    echo
    echo "--- Operating system ---"
    if [ -f /etc/os-release ]; then
        grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '"'
    else
        uname -o
    fi
    echo "Kernel:         $(uname -r)"
    echo
    echo "--- CPU ---"
    lscpu | grep -E 'Model name|^CPU\(s\)|Architecture'
    echo
    echo "--- Memory ---"
    free -h
    echo
    echo "--- Disk usage ---"
    df -h
    echo
    echo "--- Available disk space on / ---"
    df -h / | awk 'NR==2 {print "Available: " $4 " of " $2 " (" $5 " used)"}'
    echo
    echo "Report by $STUDENT_NAME finished."
    echo
} | tee -a "$LOG_FILE"

echo "Saved to: $LOG_FILE"
