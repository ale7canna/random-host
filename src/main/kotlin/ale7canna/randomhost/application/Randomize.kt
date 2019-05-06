package ale7canna.randomhost.application

class Randomize: IRandomize {
    override fun <T> draw(list: List<T>): T = list.shuffled().first()

}
