import ale7canna.randomhost.HostList
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class HostListTest : StringSpec() {
    init {
        "Empty list should return no host" {
            val hostList = HostList()
            val result = hostList.drawHost()

            result shouldBe "no host"
        }
    }
}