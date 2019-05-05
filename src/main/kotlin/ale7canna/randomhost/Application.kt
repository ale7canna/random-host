package ale7canna.randomhost

class Application(private val communication: ICommunication) {
    fun createMeeting(): Meeting =
        Meeting(
            communication.askInput("Insert hosts"),
            communication.askInput("Insert name"),
            communication.askInput("Insert location"),
            communication.askInput("Insert time")
        )
}
