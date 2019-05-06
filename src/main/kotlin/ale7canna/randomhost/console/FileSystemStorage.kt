package ale7canna.randomhost.console

import ale7canna.randomhost.application.IPersistence
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

class FileSystemPersistence(
    private val directoryPath: String
) : IPersistence {
    init {
        val path = Paths.get(directoryPath)
        if (Files.notExists(path))
            Files.createDirectory(path)
    }

    override fun save(data: String) =
        File("$directoryPath/${LocalDateTime.now()}.json")
            .writeText(data)

    override fun loadLatest(): String {
        return File("$directoryPath/*.json")
            .walkTopDown()
            .sortedBy { it.lastModified() }
            .first()
            .readText()
    }
}