#!/bin/sh
set -e
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -f "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ]; then
  exec java -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
fi
echo "Gradle wrapper JAR belum tersedia. Buka project di Android Studio untuk membuat/mengunduh wrapper, atau instal Gradle 9.7 lalu jalankan: gradle $*"
exit 1
