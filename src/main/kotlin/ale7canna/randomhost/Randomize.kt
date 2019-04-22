package ale7canna.randomhost

class Randomize: IRandomize{
    override fun <T> draw(list: List<T>): T = list.shuffled().first()

}
