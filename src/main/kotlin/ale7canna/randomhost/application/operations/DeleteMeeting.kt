package ale7canna.randomhost.application.operations

import ale7canna.randomhost.application.Application

class DeleteMeeting : IMeetingOperation {
    override val description: String
        get() = "Delete the current Meeting"

    override fun exec(application: Application): Application =
        application.delete()
}
