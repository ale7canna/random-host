import ale7canna.randomhost.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.*

class HostListTest : StringSpec() {
    init {
        val random = mockk<IRandomize>()
        val slot = slot<List<IHost>>()
        every { random.draw(list = capture(slot)) } answers { slot.captured.first() }

        val sut = HostList(random, defaultHostList())

        "Empty list should return no host" {
            val hostList = HostList(random, emptyList())

            val result = hostList.drawHost()

            result.shouldBeTypeOf<NoHost>()
        }

        "List should return an host" {
            val hostList = HostList(random, listOf(Host("name", "surname")))

            val result = hostList.drawHost()

            result shouldBe Host("name", "surname")
        }

        "Draw operation should be randomized" {
            sut.drawHost()

            verify { random.draw(any()) }
            confirmVerified(random)
        }
    }

    private fun defaultHostList(): List<IHost> {
        return listOf(
            Host("name1", "surname1"),
            Host("name2", "surname2"),
            Host("name3", "surname3"),
            Host("name4", "surname4")
        )
    }
}