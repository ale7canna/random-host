package ale7canna.randomhost.console.operations

import ale7canna.randomhost.application.Application

class SaveMeeting : IMeetingOperation {
    override val description: String
        get() = "Save the latest meeting"

    override fun exec(application: Application): Application =
        application.saveMeeting()
}
