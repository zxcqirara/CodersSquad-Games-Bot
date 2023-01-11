
import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.typesafe.config.ConfigRenderOptions
import config.DiscordConfig
import dev.kord.core.event.gateway.ReadyEvent
import extensions.RoleTakingExtension
import io.github.config4k.toConfig
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.system.exitProcess

suspend fun main() {
	val configPath = Config.path.toPath()
	if (!FileSystem.SYSTEM.exists(configPath)) {
		println("Config not found, creating...")

		val renderOptions = ConfigRenderOptions.defaults()
			.setJson(false)
			.setOriginComments(false)

		FileSystem.SYSTEM.write(configPath, true) {
			writeUtf8(
				DiscordConfig().toConfig("discord")
					.root().render(renderOptions)
			)
		}

		println("Configure the config!")
		exitProcess(1)
	}

	Config.update()
	val discordConfig = Config.current

	val bot = ExtensibleBot(discordConfig.token) {
		applicationCommands {
			defaultGuild(discordConfig.guildId.toULong())
		}

		extensions {
			add(::RoleTakingExtension)
		}
	}

	bot.on<ReadyEvent> {
		println("Bot ready! Logged in as ${self.username}")
	}

	bot.start()
}