package com.github.lukasriedler.kaul

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.security.DigestInputStream
import java.security.MessageDigest
import java.math.BigInteger

class Kaul constructor(var url: String) {
    var checkedUpdate = false
    var downloadedUpdate = false
    var preparedUpgrade = false

    lateinit var latestVersion: String

    fun checkForUpdate(): Boolean {
        loadJson(url)
        return false
    }

    fun loadJson(url: String): Boolean {
        val jsonString = IOUtils.toString(URL(url), "UTF-8")
        latestVersion = "s"
        return false
    }

    fun getChangelog(): String {
        return "changelog"
    }

    fun downloadUpdate(): Boolean {
        var downloadFile = System.getProperty("user.dir") + "temp" + url.substringAfterLast('/')
        FileUtils.copyURLToFile(URL(url), File(downloadFile))
        var hash = "d5290c63b2cb5e020218fc14336cca05079b9fd5b22663efa25577d0a22acd02"
        return calculateHash(downloadFile, hash, "SHA-256")
    }

    fun calculateHash(file: String, hash: String, algorithm: String): Boolean {
        var digest = MessageDigest.getInstance(algorithm)
        DigestInputStream(FileInputStream(file), digest).use {
            while (it.read() != -1)
            ;
        }
        var ret = String.format("%064x", BigInteger(1, digest.digest()))
        return ret == hash
    }

    fun checkSignature(): Boolean {
        return false
    }

    fun prepareUpgrade(): Boolean {
        return false
    }

    fun upgrade(): Boolean {
        return false
    }
}