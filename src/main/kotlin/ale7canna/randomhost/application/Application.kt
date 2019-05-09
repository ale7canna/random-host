package ale7canna.randomhost.application

import ale7canna.randomhost.application.operations.*

open class Application(
    private val communication: ICommunication,
    private val storage: IStorage<Meeting?>,
    private val randomize: IRandomize,
    val currentMeeting: Meeting
) {
    private val availableOperations = hashMapOf(
        Pair(0, Exit()),
        Pair(1, CreateMeeting()),
        Pair(2, SaveMeeting()),
        Pair(3, CreateMeetingUsingParticipantFromLatest()),
        Pair(4, ExtractHost()),
        Pair(5, DeleteMeeting()),
        Pair(6, EditMeeting())
    )

    fun createMeeting(): Application =
        newFromMeeting(Meeting(
            communication.askForHosts(currentMeeting.hosts),
            communication.askForName(currentMeeting.meetingName),
            communication.askForLocation(currentMeeting.location),
            communication.askForDateTime(currentMeeting.startTime)
        ))

    fun createMeetingUsingLatestParticipants(): Application {
        return when (val latest = storage.restoreLatest())
        {
            null -> {
                communication.show("Can't find any meeting to restore the participants from")
                return empty()
            }
            else -> newFromMeeting(Meeting(
                latest.hosts,
                communication.askForName(currentMeeting.meetingName),
                communication.askForLocation(currentMeeting.location),
                communication.askForDateTime(currentMeeting.startTime)
            ))
        }
    }

    open fun saveMeeting(): Application =
        this.apply {
            storage.store(currentMeeting)
        }

    open fun extractHost(): Application =
        this.apply {
            when (val host = currentMeeting.extractHost(randomize))
            {
                is Host -> communication.showHost(host)
                else -> communication.show("No host is available for the extraction")
            }
        }

    fun run() {
        when (val operation = askForOperation()) {
            is Exit -> return
            else -> {
                perform(operation as IMeetingOperation).run()
            }
        }
    }

    fun delete(): EmptyApplication = empty()

    fun editCurrentMeeting(): Application =
        newFromMeeting(Meeting(
            communication.askForHosts(currentMeeting.hosts),
            communication.askForName(currentMeeting.meetingName),
            communication.askForLocation(currentMeeting.location),
            communication.askForDateTime(currentMeeting.startTime)
        ))

    private fun askForOperation(): IOperation {
        communication.show("Select available operation:")
        availableOperations.forEach { println("${it.key} - ${it.value.description}") }

        return availableOperations[readLine()!!.toInt()]!!
    }

    private fun perform(operation: IMeetingOperation): Application =
        operation.exec(this)

    private fun newFromMeeting(latestMeeting: Meeting): Application =
        Application(
            communication,
            storage,
            randomize,
            latestMeeting
        )

    private fun empty(): EmptyApplication =
        EmptyApplication(communication, storage, randomize)
}
