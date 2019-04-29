package ale7canna.randomhost

import java.time.LocalDateTime

data class Meeting(
    private val hostList: HostList,
    val meetingName: String = "meeting",
    val location: String = "location",
    val startTime: LocalDateTime = LocalDateTime.now()
) {
    val hosts = hostList.hostList

    fun appointHost(): Host = when (val host = hostList.drawHost()) {
        is NoHost -> throw Exception("Can't make Meeting without any Host")
        else -> host as Host
    }

    fun addHost(hostToAdd: Host): Meeting =
        Meeting(
            hostList.add(hostToAdd),
            meetingName,
            "meeting location",
            startTime
        )
}
