import com.typesafe.config.ConfigFactory
import config.DiscordConfig
import io.github.config4k.extract
import java.io.File

object Config {
	const val path = "config"
	lateinit var current: DiscordConfig

	fun update() {
		val config = ConfigFactory.parseFile(File(path))
		current = config.extract("discord")
	}

	private val disabledExtensionsFile = File("disabled.txt")
	val disabledExtensions = mutableSetOf<String>()

	fun loadDisabledExtensions() {
		with(disabledExtensionsFile) {
			if (!exists()) {
				createNewFile()

				return@with
			}

			disabledExtensions.addAll(disabledExtensionsFile.readLines())
		}
	}

	fun dumpDisabledExtensions() {
		disabledExtensionsFile.writeText(
			disabledExtensions.joinToString("\n")
		)
	}
}