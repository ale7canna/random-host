package ale7canna.randomhost

interface IPersistence {
    fun save(data: String)
    fun loadLatest(): String
}
