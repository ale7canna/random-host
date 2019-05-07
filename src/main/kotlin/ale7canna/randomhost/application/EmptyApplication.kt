package ale7canna.randomhost.application

class EmptyApplication(
    private val communication: ICommunication,
    storage: IStorage<Meeting?>,
    randomize: IRandomize
) : Application(
    communication,
    storage,
    randomize,
    Meeting(emptyList())) {

    override fun saveMeeting(): Application {
        communication.show("No meeting to save.")
        return this
    }

    override fun extractHost(): Application {
        communication.show("No meeting to extract the host from.")
        return this
    }
}