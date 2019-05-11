import ale7canna.randomhost.application.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.*
import java.time.LocalDateTime
import java.time.Month

class ApplicationTest : StringSpec() {
    init {
        val communication: ICommunication = mockk()
        every { communication.askForHosts(emptyList()) } answers { defaultHostList() }
        every { communication.askForName(any()) } answers { "some name" }
        every { communication.askForLocation(any()) } answers { "some location" }
        every { communication.askForDateTime(any()) } answers { LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0) }
        every { communication.showHost(any()) } answers { }
        every { communication.show(any()) } answers { }

        val storage: IStorage<Meeting?> = mockk()
        every { storage.store(any()) } answers { }

        val randomize: IRandomize = mockk()
        val capturingSlot = slot<List<Host>>()
        every { randomize.draw(capture(capturingSlot)) } answers { capturingSlot.captured.first() }

        val sut = Application(communication, storage, randomize, Meeting(
            defaultHostList(),
            "first meeting",
            "first location",
            LocalDateTime.of(2019, Month.MAY, 3, 19, 5, 0)
        ))

        "Application creates a meeting with input from the communication port" {
            val localSut = EmptyApplication(communication, storage, randomize)

            val result = localSut.createMeeting()

            result.currentMeeting shouldBe Meeting(
                defaultHostList(),
                "some name",
                "some location",
                LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0)
            )
        }

        "Application can create meeting using participants from the last meeting" {
            every { storage.restoreLatest() } answers {
                Meeting(
                    hosts = listOf(
                        Host("name1", "surname1", true),
                        Host("name2", "surname2", false),
                        Host("name3", "surname3", false),
                        Host("name4", "surname4", true)
                    )
                )
            }

            val result = sut.createMeetingUsingLatestParticipants()

            result.currentMeeting shouldBe Meeting(
                listOf(
                    Host("name1", "surname1", true),
                    Host("name2", "surname2", false),
                    Host("name3", "surname3", false),
                    Host("name4", "surname4", true)
                ),
                "some name",
                "some location",
                LocalDateTime.of(2019, Month.MAY, 5, 19, 5, 0)
            )
        }

        "Application can store Meetings" {
            sut.saveMeeting()

            verify { storage.store(any()) }
        }

        "Application can extract an host for the currentMeeting meeting" {
            sut.extractHost()

            verify { communication.showHost(any()) }
        }

        "Application can delete current Meeting" {
            val result = sut.delete()

            result.shouldBeTypeOf<EmptyApplication>()
        }

        "Application can edit the meeting properties" {
            val localSut = Application(
                communication,
                storage,
                randomize,
                Meeting(
                    listOf(Host("host1", "host1", false)),
                    "meeting",
                    "location",
                    LocalDateTime.of(2019, 5, 9, 19, 30, 0)
                ))
            every { communication.askForHosts(listOf(Host("host1", "host1", false))) } answers {
                listOf(
                    Host("host1", "host1", false),
                    Host("added1", "added1", true),
                    Host("added2", "added2", false))
            }
            every { communication.askForName("meeting") } answers { "new meeting name" }
            every { communication.askForLocation("location") } answers { "new location" }
            every { communication.askForDateTime(LocalDateTime.of(2019, 5, 9, 19, 30, 0)) } answers {
                LocalDateTime.of(2019, 5, 9, 19, 45, 0) }

            val result = localSut.editCurrentMeeting()

            result.currentMeeting shouldBe Meeting(
                    listOf(
                        Host("host1", "host1", false),
                        Host("added1", "added1", true),
                        Host("added2", "added2", false)),
                    "new meeting name",
                    "new location",
                    LocalDateTime.of(2019, 5, 9, 19, 45, 0))
        }

        "Application can't perform extraction if no host is available" {
            val localSut = Application(
                communication,
                storage,
                randomize,
                Meeting(
                    listOf(Host("name", "surname", false))
            ))

            localSut.extractHost()

            verify { communication.show("No host is available for the extraction") }
        }

        "Application can't restore hosts if no previous meeting is available" {
            every { storage.restoreLatest() } answers { null }

            sut.createMeetingUsingLatestParticipants()

            verify { communication.show("Can't find any meeting to restore the participants from") }
        }
    }
}