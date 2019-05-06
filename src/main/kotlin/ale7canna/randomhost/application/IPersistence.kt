package ale7canna.randomhost.application

interface IPersistence {
    fun save(data: String)
    fun loadLatest(): String
}
