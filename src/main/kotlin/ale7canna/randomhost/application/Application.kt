package ale7canna.randomhost.application

class Application(
    private val communication: ICommunication,
    private val storage: IStorage<Meeting>,
    private val randomize: IRandomize
) {
    var latestMeeting = Meeting(emptyList())

    fun createMeeting(): Meeting =
        Meeting(
            communication.askForHosts(),
            communication.askForName(),
            communication.askForLocation(),
            communication.askForDateTime()
        )

    fun createMeetingUsingLatestParticipants(): Meeting =
        Meeting(
            storage.restoreLatest().hosts,
            communication.askForName(),
            communication.askForLocation(),
            communication.askForDateTime()
        )

    fun saveMeeting(meeting: Meeting) {
        storage.store(meeting)
    }

    fun extractHost(meeting: Meeting): Unit =
        communication.showExtractedHost(meeting.extractHost(randomize))
}
