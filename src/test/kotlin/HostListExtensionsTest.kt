import ale7canna.randomhost.application.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.*

class HostListExtensionsTest : StringSpec() {
    init {
        val slot = slot<List<IHost>>()
        val random = mockk<IRandomize>()
        every { random.draw(list = capture(slot)) } answers { slot.captured.first() }

        val sut = defaultHostList()

        "Empty list should return no host" {
            val localSut = emptyList<Host>()

            val result = localSut.drawHost(random)

            result.shouldBeTypeOf<NoHost>()
        }

        "List should return an host" {
            val localSut = listOf(Host("name", "surname"))

            val result = localSut.drawHost(random)

            result shouldBe Host("name", "surname")
        }

        "Draw operation should be randomized" {
            sut.drawHost(random)

            verify { random.draw(any()) }
            confirmVerified(random)
        }

        "Absent host can't be extracted" {
            val localSut = defaultHostList() + Host("name", "surname", false)
            val calledList = slot<List<Host>>()
            every { random.draw(capture(calledList)) } answers { calledList.captured.first() }

            localSut.drawHost(random)

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
