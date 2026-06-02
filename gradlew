#!/usr/bin/env sh
set -e
if [ -d "$HOME/.local/share/mise/installs/java/17.0.2" ]; then
  export JAVA_HOME="$HOME/.local/share/mise/installs/java/17.0.2"
  export PATH="$JAVA_HOME/bin:$PATH"
fi
exec gradle "$@"
