package ale7canna.randomhost.application

interface ICommunication {
    fun<T> askInput(message: String): T
}
