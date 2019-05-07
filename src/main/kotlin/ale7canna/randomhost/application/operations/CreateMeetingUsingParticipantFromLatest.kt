package ale7canna.randomhost.application.operations

import ale7canna.randomhost.application.Application

class CreateMeetingUsingParticipantFromLatest : IMeetingOperation {
    override val description: String
        get() = "Create meeting using participants from latest one"

    override fun exec(application: Application): Application =
        application.createMeetingUsingLatestParticipants()
}
