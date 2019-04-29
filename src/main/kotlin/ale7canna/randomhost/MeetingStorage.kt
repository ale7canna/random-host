package ale7canna.randomhost

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MeetingStorage(private val persistence: IPersistence) : IStorage<Meeting> {
    override fun restoreLatest(): Meeting =
        Gson().fromJson(persistence.loadLatest())

    override fun store(data: Meeting) =
        persistence.save(GsonBuilder().setPrettyPrinting().create().toJson(data))
}
