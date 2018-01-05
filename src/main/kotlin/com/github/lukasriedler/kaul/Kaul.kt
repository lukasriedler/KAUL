package com.github.lukasriedler.kaul

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.security.DigestInputStream
import java.security.MessageDigest
import java.math.BigInteger
import net.lingala.zip4j.core.ZipFile
import java.io.FileFilter
import java.io.InputStream


class Kaul constructor(private var url: String) {
    private var checkedUpdate = false
    private var downloadedUpdate = false
    private var preparedUpgrade = false

    private lateinit var applicationName: String
    private lateinit var latestVersion: String
    private lateinit var downloadFile: String
    private lateinit var downloadLocation: String
    private lateinit var changelog: String
    private lateinit var hash: String
    private lateinit var signature: String

    private lateinit var tempFolder: String
    private lateinit var targetFolder: String

    fun checkForUpdate(): KaulReturn {
        val json = loadJsonFromUrl(url)

        if (json == "") {
            return KaulReturn.ERROR_JSON_DOWNLOAD
        }

        val manifest = mapJson(json)

        var supported = false
        for (platform in manifest.platforms) {
            if (platform.platform == "jvm") {
                supported = true
                applicationName = platform.application_name
                latestVersion = platform.latest_version
                downloadFile = platform.archive
                downloadLocation = platform.location
                changelog = platform.changelog
                hash = platform.hash
                signature = platform.signature
            }
        }

        if (!supported)
            return KaulReturn.ERROR_JSON_DOWNLOAD

        // create temporary folder for the update
        tempFolder = FileUtils.getTempDirectoryPath() + applicationName + "\\"

        // check if temp folder exists, if not create folder, if unable to create return error
        if (!File(tempFolder).isDirectory && !File(tempFolder).mkdirs())
            return KaulReturn.ERROR_TEMP_FOLDER

        // get the installation folder of the application
        targetFolder = System.getProperty("user.dir")

        //TODO check if everything worked
        checkedUpdate = true

        return KaulReturn.UPDATE_AVAILABLE
    }

    fun loadJsonFromUrl(url: String): String {
        return try {
            IOUtils.toString(URL(url), "UTF-8")
        } catch (e: Exception) {
            ""
        }
    }

    private fun mapJson(json: String): KaulManifest {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }

    fun downloadUpdate(): KaulReturn {
        if (!checkedUpdate) {
            val check = checkForUpdate()
            if (check != KaulReturn.UPDATE_AVAILABLE)
                return check
        }

        // download archive to temporary folder
        try {
            FileUtils.copyURLToFile(URL(downloadLocation + downloadFile), File(tempFolder + downloadFile))
        } catch (e: Exception) {
            return KaulReturn.ERROR_ZIP_DOWNLOAD
        }

        // check archive hash
        if (!checkSHA256HashOfFile(File(tempFolder + downloadFile), hash))
            return KaulReturn.ERROR_ZIP_HASH

        // TODO check archive signature

        downloadedUpdate = true
        return KaulReturn.UPDATE_DOWNLOADED
    }

    fun checkSHA256HashOfFile(file: File, hash: String): Boolean {
        if (!file.isFile)
            return false

        return getSHA256HashFromStream(FileInputStream(file)) == hash
    }

    fun compareFileSHA256Hash(firstFile: File, secondFile: File): Boolean {
        if (!firstFile.isFile || !secondFile.isFile)
            return false

        return getSHA256HashFromStream(FileInputStream(firstFile)) == getSHA256HashFromStream(FileInputStream(secondFile))
    }

    private fun getSHA256HashFromStream(stream: InputStream): String {
        val digest = MessageDigest.getInstance("SHA-256")

        DigestInputStream(stream, digest).use {
            while (it.read() != -1)
            ;
        }
        return String.format("%064x", BigInteger(1, digest.digest()))
    }

    fun checkSignature(): Boolean {
        return false
    }

    fun prepareUpgrade(): KaulReturn {
        if (!checkedUpdate) {
            val check = checkForUpdate()
            if (check != KaulReturn.UPDATE_AVAILABLE)
                return check
        }

        if (!downloadedUpdate) {
            val check = downloadUpdate()
            if (check != KaulReturn.UPDATE_DOWNLOADED)
                return check
        }

        if (!unzip(tempFolder + downloadFile, tempFolder))
            return KaulReturn.ERROR_ZIP_EXTRACT

        if (!deleteUnneededFiles(tempFolder, targetFolder))
            return KaulReturn.ERROR_PREPARE_UPGRADE

        preparedUpgrade = true
        return KaulReturn.UPDATE_PREPARED
    }

    private fun unzip(file: String, destination: String): Boolean {
        try {
            val zip = ZipFile(file)
            zip.extractAll(destination)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun deleteUnneededFiles(source: String, destination: String): Boolean {
        val sourceFolder = File(source)

        try {
            sourceFolder.listFiles(FileFilter { file -> compareFileSHA256Hash(file, File(destination + file.absolutePath.removePrefix(source))) }).forEach { it.delete() }
        } catch (e: Exception) {
            return false
        }

        return true
    }

    fun upgrade(restart: Boolean): KaulReturn {
        if (!checkedUpdate) {
            val check = checkForUpdate()
            if (check != KaulReturn.UPDATE_AVAILABLE)
                return check
        }

        if (!downloadedUpdate) {
            val check = downloadUpdate()
            if (check != KaulReturn.UPDATE_DOWNLOADED)
                return check
        }

        if (!preparedUpgrade) {
            val check = prepareUpgrade()
            if (check != KaulReturn.UPDATE_PREPARED)
                return check
        }

        // TODO start upgrade executable

        return KaulReturn.UPDATE_STARTED
    }
}