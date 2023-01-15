package extensions

import Config
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.types.respond

class ReloadExtension : Extension() {
	override val name = "reload"

	override suspend fun setup() {
		ephemeralSlashCommand {
			name = "reload"
			description = "Перезагрузить конфиг бота"
			allowByDefault = false

			action {
				Config.update()

				respond { content = "Конфиг перезагружен!" }
			}
		}
	}
}