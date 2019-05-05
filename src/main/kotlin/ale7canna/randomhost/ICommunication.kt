package ale7canna.randomhost

import java.time.LocalDateTime

interface ICommunication {
    fun<T> askInput(message: String): T
}
