package ale7canna.randomhost.application.operations

import ale7canna.randomhost.application.Application

interface IOperation {
    val description: String
}

interface IMeetingOperation: IOperation {
    fun exec(application: Application): Application
}