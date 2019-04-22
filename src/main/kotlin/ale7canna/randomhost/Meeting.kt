package ale7canna.randomhost

class Meeting(private val hostList: HostList) {
    fun appointHost(): Host = when (val host = hostList.drawHost()) {
        is NoHost -> throw Exception("Can't make Meeting without any Host")
        else -> host as Host
    }

    fun addHost(hostToAdd: Host): Meeting = Meeting(hostList.add(hostToAdd))
}
