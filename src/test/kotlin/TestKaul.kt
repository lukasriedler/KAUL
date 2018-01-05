import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.lukasriedler.kaul.Kaul
import com.github.lukasriedler.kaul.KaulManifest
import com.github.lukasriedler.kaul.KaulManifestPlatform
import com.github.lukasriedler.kaul.KaulReturn
import com.winterbe.expekt.should
import org.apache.commons.io.FileUtils
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.io.File

object TestKaul : Spek({
    given("the Kaul class") {
        val url = "https://github.com/lukasriedler/kaul-example/releases/download/0.1/application-manifest.json"
        val update = Kaul(url)

        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        val manifest = KaulManifest(listOf(KaulManifestPlatform("jvm", "app-name", "1.5.8",
                "application-1.5.8.zip",
                "https://github.com/lukasriedler/kaul-example/releases/download/0.1/",
                "Changes since the last version",
                "8526b413004d90f54602b74a14cdd2ab5a1c7fada438aba7bb13449d7efcef18",
                "")))
        mapper.writeValue(File("D:\\Projects\\kaul\\application-manifest.json"), manifest)

        on("checkForUpdate") {
            it("should return UPDATE_AVAILABLE") {
                update.checkForUpdate().should.equal(KaulReturn.UPDATE_AVAILABLE)
            }
        }

        on("loadJSON") {
            it("should be the same string") {
                update.loadJsonFromUrl("https://github.com/lukasriedler/kaul-example/releases/download/0.1/application-manifest.json").should.equal(FileUtils.readFileToString(File("D:\\Projects\\kaul\\application-manifest.json"), "UTF-8"))
            }
        }

        on("downloadUpdateNormal") {
            it("should return UPDATE_DOWNLOADED") {
                update.downloadUpdate().should.equal(KaulReturn.UPDATE_DOWNLOADED)
            }
        }

        on("downloadUpdateFailureNoFile") {
            val noFile = Kaul("https://github.com/geofffranks/spruce/archive/v1.13.1.zip2")
            val bool = noFile.downloadUpdate()
            bool.should.equal(KaulReturn.ERROR_JSON_DOWNLOAD)
        }

        on("downloadUpdateFailureBadSSL") {
            val badSSL = Kaul("https://expired.badssl.com/")
            val bool = badSSL.downloadUpdate()
            bool.should.equal(KaulReturn.ERROR_JSON_DOWNLOAD)
        }

        on("calculateHash") {
            val bool = update.checkSHA256HashOfFile(File("D:\\Projects\\kaul\\application-1.5.8.zip"), "8526b413004d90f54602b74a14cdd2ab5a1c7fada438aba7bb13449d7efcef18")
            it("should return true") {
                bool.should.be.`true`
            }
        }

        on("prepareUpgrade") {
            it("should return UPDATE_PREPARED") {
                update.prepareUpgrade().should.equal(KaulReturn.UPDATE_PREPARED)
            }
        }

        on("deleteUnneededFiles") {
            it("should return true") {
                update.deleteUnneededFiles("D:\\Projects\\kaul\\temp\\spruce-1.13.1", "D:\\Projects\\kaul\\spruce-1.13.1").should.be.`true`
            }
        }

        // val tempFolder = File(".\\temp\\")
        //tempFolder.deleteOnExit()
    }
})