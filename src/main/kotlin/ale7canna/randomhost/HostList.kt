package ale7canna.randomhost

class HostList(private val randomize: IRandomize, private val hostList: List<IHost>) {

    fun drawHost(): IHost =
        when (hostList.count()) {
            0 -> NoHost()
            else -> randomize.draw(hostList)
        }
}
