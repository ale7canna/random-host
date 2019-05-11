package ale7canna.randomhost.console

import ale7canna.randomhost.application.Host
import ale7canna.randomhost.application.ICommunication
import ale7canna.randomhost.application.operations.IOperation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConsoleCommunication : ICommunication {
    override fun show(message: String) =
        println(message)

    override fun showHost(host: Host) =
        println(host.toString())

    override fun askForHosts(currentHosts: List<Host>): List<Host> =
        askTo("Insert Hosts (current ones: $currentHosts)").and().returnsHosts(currentHosts)

    override fun askForName(currentMeetingName: String): String =
        askTo("Insert meeting name (current: $currentMeetingName)").and().read(currentMeetingName)

    override fun askForLocation(currentLocation: String): String =
        askTo("Insert meeting location (current: $currentLocation)").and().read(currentLocation)

    override fun askForDateTime(currentStartTime: LocalDateTime): LocalDateTime =
        askTo("Insert date and time (dd/MM/yy hh:mm). Leave empty to use the current time. (Current: $currentStartTime)")
            .and().readDateTime(currentStartTime)

    override fun askForOperation(operations: HashMap<Int, IOperation>): IOperation =
        askTo("Select available operation:")
            .and().readOperation(operations)
}

private fun askTo(message: String) {
    println(message)
}

private fun Unit.returnsHosts(hosts: List<Host>): List<Host> =
    when (askToContinue()) {
        true -> returnsHosts(hosts + askForHost())
        false -> hosts
    }

private fun askToContinue(): Boolean =
    println("Want to add an host? (yes | no)").and().read() != "no"

private fun askForHost(): Host =
    Host(
        println("Insert host name").and().read(),
        println("Insert host surname").and().read(),
    println("Is host present? (yes | no)").and().read() == "yes"
)

private fun Unit.and(): Unit = this

@Suppress("unused")
private fun Unit.read(currentValue: String = ""): String =
    when (val value = readLine()){
        null, "" -> currentValue
        else -> value
    }

@Suppress("unused")
fun Unit.readDateTime(currentStartTime: LocalDateTime): LocalDateTime =
    when (val date = readLine()) {
        null, "" -> if (currentStartTime == LocalDateTime.MIN) LocalDateTime.now() else currentStartTime
        else -> LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
    }

@Suppress("unused")
fun Unit.readOperation(operations: HashMap<Int, IOperation>): IOperation {
    operations.forEach { println("${it.key} - ${it.value.description}") }
    return operations[readLine()!!.toInt()]!!
}
