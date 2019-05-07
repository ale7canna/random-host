package ale7canna.randomhost.application

import java.time.LocalDateTime

data class Meeting(
    val hosts: List<Host>,
    val meetingName: String = "meeting",
    val location: String = "location",
    val startTime: LocalDateTime = LocalDateTime.now()
) {
    fun extractHost(randomize: IRandomize): IHost =
        hosts.drawHost(randomize)

    fun addHost(hostToAdd: Host): Meeting =
        Meeting(
            hosts.add(hostToAdd),
            meetingName,
            "meeting location",
            startTime
        )
}

fun List<Host>.drawHost(randomize: IRandomize): IHost =
    when (this.count()) {
        0 -> NoHost()
        else -> randomize.draw(this.filter { it.present })
    }

fun List<Host>.add(hostToAdd: Host): List<Host> = this + hostToAdd