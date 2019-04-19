import ale7canna.randomhost.Host
import ale7canna.randomhost.HostList
import ale7canna.randomhost.NoHost
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class HostListTest : StringSpec() {
    init {
        "Empty list should return no host" {
            val hostList = HostList(emptyList())

            val result = hostList.drawHost()

            result.shouldBeTypeOf<NoHost>()
        }

        "List should return an host" {
            val hostList = HostList(listOf(Host("name", "surname")))

            val result = hostList.drawHost()

            result shouldBe Host("name", "surname")
        }
    }
}