package com.github.lukasriedler.kaul

enum class KaulReturn {
    NO_UPDATE,
    UPDATE_AVAILABLE,
    UPDATE_DOWNLOADED,
    UPDATE_PREPARED,
    UPDATE_STARTED,
    ERROR_TEMP_FOLDER,
    ERROR_JSON_DOWNLOAD,
    ERROR_ZIP_DOWNLOAD,
    ERROR_ZIP_EXTRACT,
    ERROR_ZIP_HASH,
    ERROR_ZIP_SIGNATURE,
    ERROR_PREPARE_UPGRADE,
    ERROR_UPGRADE
}