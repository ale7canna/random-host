import ale7canna.randomhost.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.*

class HostListTest : StringSpec() {
    init {
        val slot = slot<List<IHost>>()
        val random = mockk<IRandomize>()
        every { random.draw(list = capture(slot)) } answers { slot.captured.first() }

        val sut = HostList(random, defaultHostList())

        "Empty list should return no host" {
            val localSut = HostList(random, emptyList())

            val result = localSut.drawHost()

            result.shouldBeTypeOf<NoHost>()
        }

        "List should return an host" {
            val localSut = HostList(random, listOf(Host("name", "surname")))

            val result = localSut.drawHost()

            result shouldBe Host("name", "surname")
        }

        "Draw operation should be randomized" {
            sut.drawHost()

            verify { random.draw(any()) }
            confirmVerified(random)
        }

        "Absent host can't be extracted" {
            val list = defaultHostList() + Host("name", "surname", false)
            val localSut = HostList(random, list)
            val calledList = slot<List<Host>>()
            every { random.draw(capture(calledList)) } answers { calledList.captured.first() }

            localSut.drawHost()

            calledList.captured.filter { !it.present } shouldBe emptyList()
        }
    }
}

internal fun defaultHostList(): List<Host> {
    return listOf(
        Host("name1", "surname1"),
        Host("name2", "surname2"),
        Host("name3", "surname3"),
        Host("name4", "surname4")
    )
}
