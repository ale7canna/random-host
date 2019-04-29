import ale7canna.randomhost.*
import io.kotlintest.specs.StringSpec
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class MeetingStorageTest : StringSpec() {
    private val persistence: IPersistence = mockk(relaxed = true)
    val sut = MeetingStorage(persistence)

    init {
        "Meeting can be stored" {
            val meeting = Meeting(
                HostList(mockk(), listOf(Host("name", "surname", present = true))),
                "meeting name",
                "meeting location",
                LocalDateTime.of(2019, 4, 29, 19, 0)
            )
            val expectedJson = """
                {
                  "hosts": [
                    {
                      "name": "name",
                      "surname": "surname",
                      "present": true
                    }
                  ],
                  "hostList": {
                    "randomize": {},
                    "hostList": [
                      {
                        "name": "name",
                        "surname": "surname",
                        "present": true
                      }
                    ]
                  },
                  "meetingName": "meeting name",
                  "location": "meeting location",
                  "startTime": {
                    "date": {
                      "year": 2019,
                      "month": 4,
                      "day": 29
                    },
                    "time": {
                      "hour": 19,
                      "minute": 0,
                      "second": 0,
                      "nano": 0
                    }
                  }
                }
            """.trimIndent()

            sut.store(meeting)

            verify { persistence.save(expectedJson) }
            confirmVerified(persistence)
        }
    }
}