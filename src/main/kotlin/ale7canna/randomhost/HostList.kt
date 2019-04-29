package ale7canna.randomhost

data class HostList(private val randomize: IRandomize, val hostList: List<Host>) {

    fun drawHost(): IHost =
        when (hostList.count()) {
            0 -> NoHost()
            else -> randomize.draw(hostList.filter { it.present })
        }

    fun add(hostToAdd: Host): HostList = HostList(randomize, hostList + hostToAdd)
}