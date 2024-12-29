#!/bin/bash

# Путь к Java 17
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export JAVA_HOME

# Путь к JAR-файлу приложения
APP_JAR=/opt/botapp/excel-1.jar

# Директория для логов
LOG_DIR=/opt/botapp/logs/excel-app
mkdir -p $LOG_DIR

# Имя лог-файла
LOG_FILE=$LOG_DIR/excel-log.log

# Запуск приложения и запись логов в файл
$JAVA_HOME/bin/java -jar $APP_JAR >> $LOG_FILE 2>&1

# Проверка статуса завершения
if [ $? -eq 0 ]; then
    echo "Приложение успешно запущено. Логи записаны в $LOG_FILE."
else
    echo "Ошибка при запуске приложения. Проверьте логи в $LOG_FILE."
fi