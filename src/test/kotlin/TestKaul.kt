import com.github.lukasriedler.kaul.Kaul
import com.github.lukasriedler.kaul.KaulReturn
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object TestKaul : Spek({
    given("the Kaul class") {
        val url = "https://github.com/geofffranks/spruce/archive/v1.13.1.zip"
        val hash = "15dc77aceb11e42a0b55bdd22730efbe4a88e6042f91774cf0a807d1536c8e7f"
        val update = Kaul(url)

        on("checkForUpdate") {
            it("should return UPDATE_AVAILABLE") {
                update.checkForUpdate().should.equal(KaulReturn.UPDATE_AVAILABLE)
            }
        }

        on("loadJSON") {
            it("should be the same string") {
                update.loadJson("https://raw.githubusercontent.com/corysimmons/colors.json/master/colors.json").should.equal("{\n" +
                        "  \"aliceblue\": [240, 248, 255, 1],\n" +
                        "  \"antiquewhite\": [250, 235, 215, 1],\n" +
                        "  \"aqua\": [0, 255, 255, 1],\n" +
                        "  \"aquamarine\": [127, 255, 212, 1],\n" +
                        "  \"azure\": [240, 255, 255, 1],\n" +
                        "  \"beige\": [245, 245, 220, 1],\n" +
                        "  \"bisque\": [255, 228, 196, 1],\n" +
                        "  \"black\": [0, 0, 0, 1],\n" +
                        "  \"blanchedalmond\": [255, 235, 205, 1],\n" +
                        "  \"blue\": [0, 0, 255, 1],\n" +
                        "  \"blueviolet\": [138, 43, 226, 1],\n" +
                        "  \"brown\": [165, 42, 42, 1],\n" +
                        "  \"burlywood\": [222, 184, 135, 1],\n" +
                        "  \"cadetblue\": [95, 158, 160, 1],\n" +
                        "  \"chartreuse\": [127, 255, 0, 1],\n" +
                        "  \"chocolate\": [210, 105, 30, 1],\n" +
                        "  \"coral\": [255, 127, 80, 1],\n" +
                        "  \"cornflowerblue\": [100, 149, 237, 1],\n" +
                        "  \"cornsilk\": [255, 248, 220, 1],\n" +
                        "  \"crimson\": [220, 20, 60, 1],\n" +
                        "  \"cyan\": [0, 255, 255, 1],\n" +
                        "  \"darkblue\": [0, 0, 139, 1],\n" +
                        "  \"darkcyan\": [0, 139, 139, 1],\n" +
                        "  \"darkgoldenrod\": [184, 134, 11, 1],\n" +
                        "  \"darkgray\": [169, 169, 169, 1],\n" +
                        "  \"darkgreen\": [0, 100, 0, 1],\n" +
                        "  \"darkgrey\": [169, 169, 169, 1],\n" +
                        "  \"darkkhaki\": [189, 183, 107, 1],\n" +
                        "  \"darkmagenta\": [139, 0, 139, 1],\n" +
                        "  \"darkolivegreen\": [85, 107, 47, 1],\n" +
                        "  \"darkorange\": [255, 140, 0, 1],\n" +
                        "  \"darkorchid\": [153, 50, 204, 1],\n" +
                        "  \"darkred\": [139, 0, 0, 1],\n" +
                        "  \"darksalmon\": [233, 150, 122, 1],\n" +
                        "  \"darkseagreen\": [143, 188, 143, 1],\n" +
                        "  \"darkslateblue\": [72, 61, 139, 1],\n" +
                        "  \"darkslategray\": [47, 79, 79, 1],\n" +
                        "  \"darkslategrey\": [47, 79, 79, 1],\n" +
                        "  \"darkturquoise\": [0, 206, 209, 1],\n" +
                        "  \"darkviolet\": [148, 0, 211, 1],\n" +
                        "  \"deeppink\": [255, 20, 147, 1],\n" +
                        "  \"deepskyblue\": [0, 191, 255, 1],\n" +
                        "  \"dimgray\": [105, 105, 105, 1],\n" +
                        "  \"dimgrey\": [105, 105, 105, 1],\n" +
                        "  \"dodgerblue\": [30, 144, 255, 1],\n" +
                        "  \"firebrick\": [178, 34, 34, 1],\n" +
                        "  \"floralwhite\": [255, 250, 240, 1],\n" +
                        "  \"forestgreen\": [34, 139, 34, 1],\n" +
                        "  \"fuchsia\": [255, 0, 255, 1],\n" +
                        "  \"gainsboro\": [220, 220, 220, 1],\n" +
                        "  \"ghostwhite\": [248, 248, 255, 1],\n" +
                        "  \"gold\": [255, 215, 0, 1],\n" +
                        "  \"goldenrod\": [218, 165, 32, 1],\n" +
                        "  \"gray\": [128, 128, 128, 1],\n" +
                        "  \"green\": [0, 128, 0, 1],\n" +
                        "  \"greenyellow\": [173, 255, 47, 1],\n" +
                        "  \"grey\": [128, 128, 128, 1],\n" +
                        "  \"honeydew\": [240, 255, 240, 1],\n" +
                        "  \"hotpink\": [255, 105, 180, 1],\n" +
                        "  \"indianred\": [205, 92, 92, 1],\n" +
                        "  \"indigo\": [75, 0, 130, 1],\n" +
                        "  \"ivory\": [255, 255, 240, 1],\n" +
                        "  \"khaki\": [240, 230, 140, 1],\n" +
                        "  \"lavender\": [230, 230, 250, 1],\n" +
                        "  \"lavenderblush\": [255, 240, 245, 1],\n" +
                        "  \"lawngreen\": [124, 252, 0, 1],\n" +
                        "  \"lemonchiffon\": [255, 250, 205, 1],\n" +
                        "  \"lightblue\": [173, 216, 230, 1],\n" +
                        "  \"lightcoral\": [240, 128, 128, 1],\n" +
                        "  \"lightcyan\": [224, 255, 255, 1],\n" +
                        "  \"lightgoldenrodyellow\": [250, 250, 210, 1],\n" +
                        "  \"lightgray\": [211, 211, 211, 1],\n" +
                        "  \"lightgreen\": [144, 238, 144, 1],\n" +
                        "  \"lightgrey\": [211, 211, 211, 1],\n" +
                        "  \"lightpink\": [255, 182, 193, 1],\n" +
                        "  \"lightsalmon\": [255, 160, 122, 1],\n" +
                        "  \"lightseagreen\": [32, 178, 170, 1],\n" +
                        "  \"lightskyblue\": [135, 206, 250, 1],\n" +
                        "  \"lightslategray\": [119, 136, 153, 1],\n" +
                        "  \"lightslategrey\": [119, 136, 153, 1],\n" +
                        "  \"lightsteelblue\": [176, 196, 222, 1],\n" +
                        "  \"lightyellow\": [255, 255, 224, 1],\n" +
                        "  \"lime\": [0, 255, 0, 1],\n" +
                        "  \"limegreen\": [50, 205, 50, 1],\n" +
                        "  \"linen\": [250, 240, 230, 1],\n" +
                        "  \"magenta\": [255, 0, 255, 1],\n" +
                        "  \"maroon\": [128, 0, 0, 1],\n" +
                        "  \"mediumaquamarine\": [102, 205, 170, 1],\n" +
                        "  \"mediumblue\": [0, 0, 205, 1],\n" +
                        "  \"mediumorchid\": [186, 85, 211, 1],\n" +
                        "  \"mediumpurple\": [147, 112, 219, 1],\n" +
                        "  \"mediumseagreen\": [60, 179, 113, 1],\n" +
                        "  \"mediumslateblue\": [123, 104, 238, 1],\n" +
                        "  \"mediumspringgreen\": [0, 250, 154, 1],\n" +
                        "  \"mediumturquoise\": [72, 209, 204, 1],\n" +
                        "  \"mediumvioletred\": [199, 21, 133, 1],\n" +
                        "  \"midnightblue\": [25, 25, 112, 1],\n" +
                        "  \"mintcream\": [245, 255, 250, 1],\n" +
                        "  \"mistyrose\": [255, 228, 225, 1],\n" +
                        "  \"moccasin\": [255, 228, 181, 1],\n" +
                        "  \"navajowhite\": [255, 222, 173, 1],\n" +
                        "  \"navy\": [0, 0, 128, 1],\n" +
                        "  \"oldlace\": [253, 245, 230, 1],\n" +
                        "  \"olive\": [128, 128, 0, 1],\n" +
                        "  \"olivedrab\": [107, 142, 35, 1],\n" +
                        "  \"orange\": [255, 165, 0, 1],\n" +
                        "  \"orangered\": [255, 69, 0, 1],\n" +
                        "  \"orchid\": [218, 112, 214, 1],\n" +
                        "  \"palegoldenrod\": [238, 232, 170, 1],\n" +
                        "  \"palegreen\": [152, 251, 152, 1],\n" +
                        "  \"paleturquoise\": [175, 238, 238, 1],\n" +
                        "  \"palevioletred\": [219, 112, 147, 1],\n" +
                        "  \"papayawhip\": [255, 239, 213, 1],\n" +
                        "  \"peachpuff\": [255, 218, 185, 1],\n" +
                        "  \"peru\": [205, 133, 63, 1],\n" +
                        "  \"pink\": [255, 192, 203, 1],\n" +
                        "  \"plum\": [221, 160, 221, 1],\n" +
                        "  \"powderblue\": [176, 224, 230, 1],\n" +
                        "  \"purple\": [128, 0, 128, 1],\n" +
                        "  \"red\": [255, 0, 0, 1],\n" +
                        "  \"rosybrown\": [188, 143, 143, 1],\n" +
                        "  \"royalblue\": [65, 105, 225, 1],\n" +
                        "  \"saddlebrown\": [139, 69, 19, 1],\n" +
                        "  \"salmon\": [250, 128, 114, 1],\n" +
                        "  \"sandybrown\": [244, 164, 96, 1],\n" +
                        "  \"seagreen\": [46, 139, 87, 1],\n" +
                        "  \"seashell\": [255, 245, 238, 1],\n" +
                        "  \"sienna\": [160, 82, 45, 1],\n" +
                        "  \"silver\": [192, 192, 192, 1],\n" +
                        "  \"skyblue\": [135, 206, 235, 1],\n" +
                        "  \"slateblue\": [106, 90, 205, 1],\n" +
                        "  \"slategray\": [112, 128, 144, 1],\n" +
                        "  \"slategrey\": [112, 128, 144, 1],\n" +
                        "  \"snow\": [255, 250, 250, 1],\n" +
                        "  \"springgreen\": [0, 255, 127, 1],\n" +
                        "  \"steelblue\": [70, 130, 180, 1],\n" +
                        "  \"tan\": [210, 180, 140, 1],\n" +
                        "  \"teal\": [0, 128, 128, 1],\n" +
                        "  \"thistle\": [216, 191, 216, 1],\n" +
                        "  \"tomato\": [255, 99, 71, 1],\n" +
                        "  \"transparent\": [0, 0, 0, 0],\n" +
                        "  \"turquoise\": [64, 224, 208, 1],\n" +
                        "  \"violet\": [238, 130, 238, 1],\n" +
                        "  \"wheat\": [245, 222, 179, 1],\n" +
                        "  \"white\": [255, 255, 255, 1],\n" +
                        "  \"whitesmoke\": [245, 245, 245, 1],\n" +
                        "  \"yellow\": [255, 255, 0, 1],\n" +
                        "  \"yellowgreen\": [154, 205, 50, 1],\n" +
                        "  \"rebeccapurple\": [102, 51, 153, 1]\n" +
                        "}\n")
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
            bool.should.equal(KaulReturn.JSON_DOWNLOAD_ERROR)
        }

        on("downloadUpdateFailureBadSSL") {
            val badSSL = Kaul("https://expired.badssl.com/")
            val bool = badSSL.downloadUpdate()
            bool.should.equal(KaulReturn.JSON_DOWNLOAD_ERROR)
        }

        on("calculateHash") {
            val bool = update.checkSHA256Hash(System.getProperty("user.dir") + "\\temp\\" + url.substringAfterLast('/'), hash)
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

        //val tempFolder = File(System.getProperty("user.dir") + "\\temp\\")
        //tempFolder.deleteOnExit()
    }
})