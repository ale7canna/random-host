import ale7canna.randomhost.Application
import ale7canna.randomhost.Host
import ale7canna.randomhost.ICommunication
import ale7canna.randomhost.Meeting
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.time.Month

class ApplicationTest: StringSpec() {
    init {
        val communication: ICommunication = mockk()
        every { communication.askInput<List<Host>>("Insert hosts")
        } answers { defaultHostList() }
        every { communication.askInput<String>("Insert name")
        } answers { "some name" }
        every { communication.askInput<String>("Insert location")
        } answers { "some location" }
        every { communication.askInput<LocalDateTime>("Insert time")
        } answers { LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0) }


        "Application creates a meeting with input from the communication port" {
            val sut = Application(communication)

            val result = sut.createMeeting()

            result shouldBe Meeting(
                defaultHostList(),
                "some name",
                "some location",
                LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0))
        }

    }
}