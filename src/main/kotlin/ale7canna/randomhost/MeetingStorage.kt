package ale7canna.randomhost

import com.google.gson.GsonBuilder

class MeetingStorage(private val persistence: IPersistence) : IStorage<Meeting> {
    override fun store(data: Meeting) =
        persistence.save(GsonBuilder().setPrettyPrinting().create().toJson(data))
}
