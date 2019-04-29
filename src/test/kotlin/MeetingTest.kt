import ale7canna.randomhost.Host
import ale7canna.randomhost.IRandomize
import ale7canna.randomhost.Meeting
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldNotContain
import io.kotlintest.should
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class MeetingTest : StringSpec() {

    init {
        val random: IRandomize = mockk()
        val extractionList = slot<List<Host>>()
        every { random.draw(capture(extractionList)) } answers { extractionList.captured.first() }

        val sut = Meeting(defaultHostList())

        "Meeting can appoint a random Host" {
            val result = sut.extractHost(random)

            result should {
                it.name shouldNotBe null
                it.surname shouldNotBe null
            }
        }

        "Can't make a Meeting if no host is available" {
            val localSut = Meeting(emptyList())

            shouldThrow<Exception> { localSut.extractHost(random) }
        }

        "Can add a participant to a Meeting" {
            val hostToAdd = Host("new host name", "new host surname", true)

            val meetingWithNewHost = sut.addHost(hostToAdd)
            meetingWithNewHost.extractHost(random)

            extractionList.captured shouldContain hostToAdd
        }

        "Meeting can have an absent participant" {
            val absentHost = Host("absent", "host", present = false)
            val meetingWithAbsentHost = sut.addHost(absentHost)

            meetingWithAbsentHost.extractHost(random)

            extractionList.captured shouldNotContain absentHost
        }
    }
}