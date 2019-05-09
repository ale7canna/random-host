package ale7canna.randomhost.application

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MeetingStorage(private val persistence: IPersistence) :
    IStorage<Meeting?> {
    override fun restoreLatest(): Meeting? =
        when (val data = persistence.loadLatest()) {
            null, "" -> null
            else -> Gson().fromJson(data)
        }

    override fun store(data: Meeting?) =
        persistence.save(GsonBuilder().setPrettyPrinting().create().toJson(data))
}
