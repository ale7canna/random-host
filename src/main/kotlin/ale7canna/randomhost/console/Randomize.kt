package ale7canna.randomhost.console

import ale7canna.randomhost.application.IRandomize

class Randomize: IRandomize {
    override fun <T> draw(list: List<T>): T = list.shuffled().first()
}
