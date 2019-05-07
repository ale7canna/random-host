package ale7canna.randomhost.console

import ale7canna.randomhost.application.EmptyApplication
import ale7canna.randomhost.application.MeetingStorage

fun main(args: Array<String>) {
    val path = if (args.isEmpty()) "." else args.first()
    val persistence = FileSystemPersistence(path)
    val storage = MeetingStorage(persistence)
    val communication = ConsoleCommunication()
    val randomize = Randomize()

    val application = EmptyApplication(communication, storage, randomize)
    application.run()
}
