import ale7canna.randomhost.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.time.Month

class ApplicationTest: StringSpec() {
    init {
        val communication: ICommunication = mockk()
        val storage: IStorage<Meeting> = mockk()

        every { communication.askInput<List<Host>>("Insert hosts")
        } answers { defaultHostList() }
        every { communication.askInput<String>("Insert name")
        } answers { "some name" }
        every { communication.askInput<String>("Insert location")
        } answers { "some location" }
        every { communication.askInput<LocalDateTime>("Insert time")
        } answers { LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0) }

        val sut = Application(communication, storage)

        "Application creates a meeting with input from the communication port" {
            val result = sut.createMeeting()

            result shouldBe Meeting(
                defaultHostList(),
                "some name",
                "some location",
                LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0))
        }

        "Application can create meeting using participants from the last meeting" {
            every { storage.restoreLatest() } answers { Meeting (
                hosts = listOf(
                    Host("name1", "surname1", true),
                    Host("name2", "surname2", false),
                    Host("name3", "surname3", false),
                    Host("name4", "surname4", true)
                )
            )}

            val result = sut.createMeetingUsingLatestParticipants()

            result shouldBe Meeting(
                listOf(
                    Host("name1", "surname1", true),
                    Host("name2", "surname2", false),
                    Host("name3", "surname3", false),
                    Host("name4", "surname4", true)
                    ),
                "some name",
                "some location",
                LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0))
        }
    }
}