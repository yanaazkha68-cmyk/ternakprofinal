@echo off
if exist "%~dp0gradle\wrapper\gradle-wrapper.jar" (
  java -classpath "%~dp0gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
) else (
  echo Gradle wrapper JAR belum tersedia. Buka project di Android Studio atau instal Gradle 9.7.
  exit /b 1
)
