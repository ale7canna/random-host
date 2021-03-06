import ale7canna.randomhost.application.Host
import ale7canna.randomhost.application.IRandomize
import ale7canna.randomhost.application.Meeting
import ale7canna.randomhost.application.NoHost
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldNotContain
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.matchers.types.shouldNotBeTypeOf
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

            result.shouldNotBeTypeOf<NoHost>()
        }

        "Can't make a Meeting if no host is available" {
            val localSut = Meeting(emptyList())

            val result = localSut.extractHost(random)

            result.shouldBeTypeOf<NoHost>()
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