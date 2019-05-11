package ale7canna.randomhost.console.operations

import ale7canna.randomhost.application.Application

class ExtractHost : IMeetingOperation {
    override fun exec(application: Application): Application {
        return application.extractHost()
    }

    override val description: String
        get() = "Extract host among the list of present hosts"
}
