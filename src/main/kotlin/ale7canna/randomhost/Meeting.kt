package ale7canna.randomhost

import java.time.LocalDateTime

data class Meeting(
    val hosts: List<Host>,
    val meetingName: String = "meeting",
    val location: String = "location",
    val startTime: LocalDateTime = LocalDateTime.now()
) {
    fun extractHost(randomize: IRandomize): Host =
        when (val host = hosts.drawHost(randomize)) {
            is NoHost -> throw Exception("Can't make Meeting without any Host")
            else -> host as Host
        }

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