import ale7canna.randomhost.Host
import ale7canna.randomhost.HostList
import ale7canna.randomhost.IRandomize
import ale7canna.randomhost.Meeting
import io.kotlintest.matchers.collections.shouldContain
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
        val slotList = slot<List<Host>>()
        every { random.draw(capture(slotList)) } answers { slotList.captured.first() }

        val hostList = HostList(random, defaultHostList())
        val sut = Meeting(hostList)

        "Meeting can appoint a random Host" {
            val result = sut.appointHost()

            result should {
                it.name shouldNotBe null
                it.surname shouldNotBe null
            }
        }

        "Can't make a Meeting if no host is available" {
            val localSut = Meeting(HostList(random, emptyList()))

            shouldThrow<Exception> { localSut.appointHost() }
        }

        "Can add a participant to a Meeting" {
            val hostToAdd = Host("new host name", "new host surname", true)
            val slotList = slot<List<Host>>()
            every { random.draw(capture(slotList)) } answers {slotList.captured.first()}

            val meetingWithNewHost = sut.addHost(hostToAdd)
            meetingWithNewHost.appointHost()

            slotList.captured shouldContain Host("new host name", "new host surname", true)
        }
    }
}