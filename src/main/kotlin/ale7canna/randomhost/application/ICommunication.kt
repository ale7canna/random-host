package ale7canna.randomhost.application

import ale7canna.randomhost.console.operations.IOperation
import java.time.LocalDateTime

interface ICommunication {
    fun askForHosts(currentHosts: List<Host>): List<Host>
    fun askForName(currentMeetingName: String): String
    fun askForLocation(currentLocation: String): String
    fun askForDateTime(currentStartTime: LocalDateTime): LocalDateTime
    fun showHost(host: Host)
    fun show(message: String)
    fun askForOperation(operations: HashMap<Int, IOperation>): IOperation
}
