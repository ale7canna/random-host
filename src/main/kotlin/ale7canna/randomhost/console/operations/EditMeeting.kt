package ale7canna.randomhost.console.operations

import ale7canna.randomhost.application.Application

class EditMeeting : IMeetingOperation {
    override val description: String
        get() = "Edit the current meeting"

    override fun exec(application: Application): Application =
        application.editCurrentMeeting()
}
