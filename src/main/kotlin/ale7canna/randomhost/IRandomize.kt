package ale7canna.randomhost

interface IRandomize {
    fun <T> draw(list: List<T>): T
}
