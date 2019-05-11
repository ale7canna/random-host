package ale7canna.randomhost.console.operations

import ale7canna.randomhost.application.Application

interface IMeetingOperation: IOperation {
    fun exec(application: Application): Application
}