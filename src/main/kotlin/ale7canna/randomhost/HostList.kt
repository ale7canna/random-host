package ale7canna.randomhost

class HostList(val hostList: List<IHost>) {

    fun drawHost(): IHost =
            when (hostList.count()) {
                0 -> NoHost()
                else -> hostList.first()
            }
}
