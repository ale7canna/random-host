package ale7canna.randomhost.application

import java.time.LocalDateTime

interface ICommunication {
    fun askForHosts(): List<Host>
    fun askForName(): String
    fun askForLocation(): String
    fun askForDateTime(): LocalDateTime
    fun showExtractedHost(host: Host)
    fun show(message: String)
}
