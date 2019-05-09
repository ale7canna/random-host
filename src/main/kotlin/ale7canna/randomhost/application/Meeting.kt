package ale7canna.randomhost.application

import java.time.LocalDateTime

data class Meeting(
    val hosts: List<Host>,
    val meetingName: String = "",
    val location: String = "",
    val startTime: LocalDateTime = LocalDateTime.MIN
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

fun List<Host>.drawHost(randomize: IRandomize): IHost {
    val hosts = this.filter { it.present }
    return when (hosts.count()) {
        0 -> NoHost()
        else -> randomize.draw(hosts)
    }
}

fun List<Host>.add(hostToAdd: Host): List<Host> = this + hostToAdd