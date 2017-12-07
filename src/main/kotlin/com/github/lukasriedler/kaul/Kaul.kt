package com.github.lukasriedler.kaul

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


class Kaul constructor(private var url: String) {
    private var checkedUpdate = false
    private var downloadedUpdate = false
    private var preparedUpgrade = false

    private lateinit var latestVersion: String
    private lateinit var downloadFile: String
    private lateinit var downloadFolder: String
    private lateinit var changelog: String
    private lateinit var signature: String

    fun checkForUpdate(): KaulReturn {
        val json = loadJson(url)

        if (json == "")
            return KaulReturn.JSON_DOWNLOAD_ERROR

        latestVersion = getVersionFromManifest(json)
        downloadFile = getFileFromManifest(json)
        downloadFolder = System.getProperty("user.dir") + "\\temp\\"
        changelog = getChangelogFromManifest()
        signature = getSignatureFromManifest()

        //TODO
        checkedUpdate = true
        return KaulReturn.UPDATE_AVAILABLE
    }

    fun loadJson(url: String): String {
        return try {
            IOUtils.toString(URL(url), "UTF-8")
        } catch (e: Exception) {
            ""
        }
    }

    private fun getVersionFromManifest(json: String): String {
        return "0.5"
    }

    private fun getFileFromManifest(json: String): String {
        return url.substringAfterLast('/')
    }

    private fun getChangelogFromManifest(): String {
        return "changelog"
    }

    private fun getSignatureFromManifest(): String {
        return "signature"
    }

    fun getChangelog(): String {
        return changelog
    }

    fun downloadUpdate(): KaulReturn {
        if (!checkedUpdate) {
            val check = checkForUpdate()
            if (check != KaulReturn.UPDATE_AVAILABLE)
                return check
        }

        try {
            FileUtils.copyURLToFile(URL(url), File(downloadFolder + downloadFile))
        } catch (e: Exception) {
            return KaulReturn.ZIP_DOWNLOAD_ERROR
        }
        val hash = "15dc77aceb11e42a0b55bdd22730efbe4a88e6042f91774cf0a807d1536c8e7f"
        downloadedUpdate = checkSHA256Hash(downloadFolder + downloadFile, hash)

        return if (downloadedUpdate) {
            downloadedUpdate = true
            KaulReturn.UPDATE_DOWNLOADED
        } else {
            KaulReturn.HASH_ERROR
        }
    }

    fun checkSHA256Hash(file: String, hash: String): Boolean {
        val digest = MessageDigest.getInstance("SHA-256")

        DigestInputStream(FileInputStream(file), digest).use {
            while (it.read() != -1)
            ;
        }
        val ret = String.format("%064x", BigInteger(1, digest.digest()))
        return ret == hash
    }

    private fun compareFileSHA256Hash(firstFile: File, secondFile: File): Boolean {
        if (!firstFile.isFile || !secondFile.isFile)
            return false

        val digestFirstFile = MessageDigest.getInstance("SHA-256")
        DigestInputStream(FileInputStream(firstFile), digestFirstFile).use {
            while (it.read() != -1)
            ;
        }

        val digestSecondFile = MessageDigest.getInstance("SHA-256")
        DigestInputStream(FileInputStream(secondFile), digestSecondFile).use {
            while (it.read() != -1)
            ;
        }

        return digestFirstFile.digest().contentEquals(digestSecondFile.digest())
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

        if (!unzip(downloadFolder + downloadFile, downloadFolder))
            return KaulReturn.ZIP_EXTRACT_ERROR

        //TODO deleteUnneededFiles()

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
            sourceFolder.listFiles(FileFilter { file -> compareFileSHA256Hash(file, File(destination + file.absolutePath.removePrefix(source))) }).forEach { println(it.toString());it.delete() }
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

        return KaulReturn.UPDATE_STARTED
    }
}