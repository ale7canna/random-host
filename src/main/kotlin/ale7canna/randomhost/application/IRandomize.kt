package ale7canna.randomhost.application

interface IRandomize {
    fun <T> draw(list: List<T>): T
}
