package ale7canna.randomhost.console.operations

import ale7canna.randomhost.application.Application

class CreateMeeting : IMeetingOperation {
    override val description: String
        get() = "Create a new meeting"

    override fun exec(application: Application): Application =
        application.createMeeting()
}
