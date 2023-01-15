package extensions

import Config
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.ephemeralSubCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.types.respond

class ExtensionsExtension : Extension() {
	override val name = "extensions"
	override suspend fun setup() {
		ephemeralSlashCommand {
			name = "extensions"
			description = "Управление расширениями"
			allowByDefault = false

			ephemeralSubCommand {
				name = "list"
				description = "Список расширений"

				action {
					val listString = buildString {
						appendLine("```diff")

						bot.extensions.forEach { (name, extension) ->
							val symbol = if (extension.loaded) "+" else "-"

							appendLine("$symbol $name")
						}

						appendLine("```")
					}

					respond { content = listString }
				}
			}

			ephemeralSubCommand(::ExtensionNameArg) {
				name = "enable"
				description = "Подключить расширение"

				action {
					bot.loadExtension(arguments.name)

					Config.disabledExtensions -= arguments.name
					Config.dumpDisabledExtensions()

					respond { content = "Расширение ${arguments.name} подключено" }
				}
			}

			ephemeralSubCommand(::ExtensionNameArg) {
				name = "disable"
				description = "Отключить расширение"

				action {
					bot.unloadExtension(arguments.name)

					Config.disabledExtensions += arguments.name
					Config.dumpDisabledExtensions()

					respond { content = "Расширение ${arguments.name} отключено" }
				}
			}
		}
	}

	inner class  ExtensionNameArg : Arguments() {
		val name by string {
			name = "name"
			description = "Название расширения"
		}
	}
}