# MakeFile

PROJECT_NAME = Ruangku
MAIN_SRC = ${PROJECT_NAME}.java
PRC_SRC = $(wildcard program/process/*.java)
GUI_SRC = program/gui/WindowProgram.java
GUI_UTIL_SRC = $(wildcard program/gui/util/*.java)
OUTPUT_CLASS_DIR = bin/JavaClass
OUTPUT_JAR_DIR = bin/JavaJar
OUTPUT_EXE_DIR = bin

# --- METADATA APPLICATION ---

APP_VERSION = 1.0.0.0
APP_VENDOR = Zachwere Technology
APP_COPYRIGHT = Copyright (C) 2026 Zachwere Technology.
APP_DESCRIPTION = Interactive Room Management and Reservation desktop application.

ifeq ($(OS),Windows_NT)
    RUN_EXE_CMD = ${OUTPUT_EXE_DIR}/${PROJECT_NAME}/${PROJECT_NAME}.exe
    COPY_IMG_CMD = cp -r img ${OUTPUT_EXE_DIR}/${PROJECT_NAME}/
    ICON_FLAG = img/room_reservation.ico
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Darwin)
        RUN_EXE_CMD = ${OUTPUT_EXE_DIR}/${PROJECT_NAME}.app/Contents/MacOS/${PROJECT_NAME}
        COPY_IMG_CMD = cp -r img ${OUTPUT_EXE_DIR}/${PROJECT_NAME}.app/Contents/MacOS/
        ICON_FLAG = img/room_reservation.icns
    else
        RUN_EXE_CMD = ${OUTPUT_EXE_DIR}/${PROJECT_NAME}/${PROJECT_NAME}
        COPY_IMG_CMD = cp -r img ${OUTPUT_EXE_DIR}/${PROJECT_NAME}/
        ICON_FLAG = img/room_reservation.png
    endif
endif

all:
	@echo "[JAVA] Note: For now only works with flag 'debug'!"
	@echo "[JAVA] Example: make debug"

run:
	@echo "[JAVA] Running release project executable."
	@${RUN_EXE_CMD}
	@echo "[JAVA] Complete run project."

release:
	@echo "[JAVA] Ready build release project"
	@rm -rf bin
	@mkdir -p ${OUTPUT_CLASS_DIR}
	@mkdir -p ${OUTPUT_JAR_DIR}
	@javac -d ${OUTPUT_CLASS_DIR} ${MAIN_SRC} ${PRC_SRC} ${GUI_SRC} ${GUI_UTIL_SRC}
	@echo "Main-Class: ${PROJECT_NAME}" > manifest.txt
	@echo "" >> manifest.txt
	@jar cfm ${OUTPUT_JAR_DIR}/${PROJECT_NAME}.jar manifest.txt -C ${OUTPUT_CLASS_DIR} .
	@rm manifest.txt
	@echo "[JAVA] Complete build release JAR."
	@echo "[JAVA] Generating native executable package using jpackage..."
	@jpackage --type app-image --dest ${OUTPUT_EXE_DIR} --name ${PROJECT_NAME} --input ${OUTPUT_JAR_DIR} --main-jar ${PROJECT_NAME}.jar --main-class ${PROJECT_NAME} --icon ${ICON_FLAG} --app-version "${APP_VERSION}" --vendor "${APP_VENDOR}" --description "${APP_DESCRIPTION}" --copyright "${APP_COPYRIGHT}"
	@echo "[JAVA] Complete build native executable."
	@echo "[JAVA] Copying assets to native package..."
	@${COPY_IMG_CMD}
	@echo "[JAVA] Run native executable..."
	@${RUN_EXE_CMD}
	@echo "[JAVA] Complete run release project."

debug:
	@echo "[JAVA] Ready build debug project"
	@rm -rf bin
	@mkdir -p ${OUTPUT_CLASS_DIR}
	@mkdir -p ${OUTPUT_JAR_DIR}
	@javac -d ${OUTPUT_CLASS_DIR} ${MAIN_SRC} ${PRC_SRC} ${GUI_SRC} ${GUI_UTIL_SRC}
	@echo "[JAVA] Complete build debug project."
	@echo "[JAVA] Run project."
	@java -cp ${OUTPUT_CLASS_DIR} ${PROJECT_NAME}
	@echo "[JAVA] Complete run debug project."

clean:
	@echo "[JAVA] Ready clean project output."
	@rm -rf bin
	@mkdir -p ${OUTPUT_CLASS_DIR}
	@mkdir -p ${OUTPUT_JAR_DIR}
	@echo "[JAVA] Complete clean project build."