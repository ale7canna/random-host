import ale7canna.randomhost.application.*
import io.kotlintest.specs.StringSpec
import io.mockk.mockk
import io.mockk.verify

class EmptyApplicationTest: StringSpec() {
    init {
        val communication: ICommunication = mockk(relaxed = true)
        val storage: IStorage<Meeting?> = mockk()
        val randomize: IRandomize = mockk()

        val sut = EmptyApplication(communication, storage, randomize)

        "EmptyApplication cannot save meeting" {
            sut.saveMeeting()

            verify { communication.show("No meeting to save.") }
        }

        "EmptyApplication cannot extract host" {
            sut.extractHost()

            verify { communication.show("No meeting to extract the host from.") }
        }

        "EmptyApplication cannot edit meeting" {
            sut.editCurrentMeeting()

            verify { communication.show("No meeting to edit.") }
        }
    }

}