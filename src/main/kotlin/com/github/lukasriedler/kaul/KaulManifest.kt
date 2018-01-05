package com.github.lukasriedler.kaul

data class KaulManifest(val platforms: List<KaulManifestPlatform>)

data class KaulManifestPlatform(val platform: String, val application_name: String, val latest_version: String, val archive: String, val location: String,
                                val changelog: String, val hash: String, val signature: String)