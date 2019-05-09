package ale7canna.randomhost.application.operations

import ale7canna.randomhost.application.Application

class EditHostList : IMeetingOperation {
    override val description: String
        get() = "Edit the current meeting host list"

    override fun exec(application: Application): Application =
        application.editHostList()
}
