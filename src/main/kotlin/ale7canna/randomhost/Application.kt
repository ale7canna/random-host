package ale7canna.randomhost

class Application(
    private val communication: ICommunication,
    private val storage: IStorage<Meeting>
) {

    fun createMeeting(): Meeting =
        Meeting(
            communication.askInput("Insert hosts"),
            communication.askInput("Insert name"),
            communication.askInput("Insert location"),
            communication.askInput("Insert time")
        )

    fun createMeetingUsingLatestParticipants(): Meeting =
        Meeting(
            storage.restoreLatest().hosts,
            communication.askInput("Insert name"),
            communication.askInput("Insert location"),
            communication.askInput("Insert time")
        )
}
