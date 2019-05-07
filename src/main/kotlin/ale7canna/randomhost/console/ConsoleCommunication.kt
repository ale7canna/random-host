package ale7canna.randomhost.console

import ale7canna.randomhost.application.Host
import ale7canna.randomhost.application.ICommunication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConsoleCommunication : ICommunication {
    override fun show(message: String) =
        println(message)

    override fun showExtractedHost(host: Host) =
        println(host.toString())

    override fun askForHosts(): List<Host> =
        askTo("Insert Hosts").and().returnsHosts()

    override fun askForName(): String =
        askTo("Insert meeting name").and().read()

    override fun askForLocation(): String =
        askTo("Insert meeting location").and().read()

    override fun askForDateTime(): LocalDateTime =
        askTo("Insert date and time (dd/MM/yy hh:mm). Leave empty to use the current time.")
            .and().readDateTime()
}

private fun askTo(message: String) {
    println(message)
}

@Suppress("unused")
private fun Unit.returnsHosts(): List<Host> {
    var result = emptyList<Host>()
    var continueInsertion = askToContinue()
    while (continueInsertion) {
        result = result + askForHost()
        continueInsertion = askToContinue()
    }
    return result
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
private fun Unit.read(): String =
    readLine()!!

@Suppress("unused")
fun Unit.readDateTime(): LocalDateTime =
    when (val date = readLine()) {
        "" -> LocalDateTime.now()
        else -> LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))
    }

