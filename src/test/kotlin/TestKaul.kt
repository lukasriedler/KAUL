import com.github.lukasriedler.kaul.Kaul
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object TestKaul : Spek({
    given("the Kaul class") {
        val url = "https://api.github.com/repos/geofffranks/spruce/releases/latest"
        val hash = "d5290c63b2cb5e020218fc14336cca05079b9fd5b22663efa25577d0a22acd02"
        val update = Kaul(url)

        on("checkForUpdate"){
            val bool = update.checkForUpdate()
            it("should return true") {
                bool.should.be.`true`
            }
        }

        on("downloadUpdate") {
            val bool = update.downloadUpdate()
            it("should return true") {
                bool.should.be.`true`
            }
        }

        on("calculateHash") {
            val bool = update.calculateHash(System.getProperty("user.dir") + url.substringAfterLast('/'), hash, "SHA-256")
            it("should return true") {
                bool.should.be.`true`
            }
        }
    }
})