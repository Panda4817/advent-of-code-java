#!/bin/bash

# Ensure the script is invoked with the necessary arguments
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <session_cookie> <year> <day>"
    exit 1
fi

COOKIE=$1
YEAR=$2
DAY=$3

FILE_PATH="src/main/resources/input.txt"

# URL for the input data
URL="https://adventofcode.com/${YEAR}/day/${DAY}/input"

# Fetch the input data using curl (overwrite the file if it exists)
curl -s -b "session=$COOKIE" $URL -o "$FILE_PATH"

# Check if the file was successfully created
if [ -f "$FILE_PATH" ]; then
  # Post-process the file: remove the trailing newline if it exists
  # Use the `truncate` command to remove the newline at the end of the file
  if [ -s "$FILE_PATH" ]; then
    # Strip the last newline if it exists
    truncate -s -1 "$FILE_PATH"
    echo " (Removed trailing newline)"
  fi
  echo " Input data saved to $FILE_PATH"
else
  echo " Failed to retrieve input data for year ${YEAR} day ${DAY}."
fi

# Use java 21 using sdkman
export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 21.0.8-tem # Make sure this aligns with which java version is installed on the laptop

# Run the Maven commands
echo "Running Maven clean install..."
~/.sdkman/candidates/maven/current/bin/mvn -s ~/.sdkman/candidates/maven/current/conf/settings.xml clean install -DskipTests=true

echo "Running Maven exec:java..."
~/.sdkman/candidates/maven/current/bin/mvn -s ~/.sdkman/candidates/maven/current/conf/settings.xml exec:java -Dexec.arguments="$YEAR","$DAY"

echo "Script completed successfully!"
