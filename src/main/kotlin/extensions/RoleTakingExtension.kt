package extensions

import Config
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.ephemeralSubCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.channel
import com.kotlindiscord.kord.extensions.components.components
import com.kotlindiscord.kord.extensions.components.ephemeralButton
import com.kotlindiscord.kord.extensions.components.types.emoji
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.utils.hasRole
import dev.kord.common.entity.ButtonStyle
import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.asChannelOf
import dev.kord.core.behavior.edit
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import dev.kord.rest.builder.message.modify.embed

class RoleTakingExtension : Extension() {
	override val name = "role-taking"

	override suspend fun setup() {
		var currentMessage: Message? = null

		ephemeralSlashCommand {
			name = "roles"
			description = "Управление ролями"
			allowByDefault = false

			ephemeralSubCommand(::RolesPostArguments) {
				name = "post"
				description = "Пост карточки со взятием ролей"

				action {
					val channel = arguments.channel.asChannelOf<TextChannel>()

					currentMessage = channel.createMessage("Wait...")
					editLikePostMessage(currentMessage!!)

					respond { content = "✅ Карточка опубликована" }
				}
			}

			ephemeralSubCommand {
				name = "update"
				description = "Обновить конфиг с ролями"

				action {
					if (currentMessage != null) {
						Config.update()
						editLikePostMessage(currentMessage!!)

						respond { content = "✅ Конфиг и карточка обновлены" }
					}
					else {
						respond { content = "А смысл? карточка ещё не публиковалась же" }
					}
				}
			}
		}
	}

	private suspend fun editLikePostMessage(message: Message) = message.edit {
		content = "Выдача ролей"

		embed {
			title = "🚩 Выдача ролей по играм"
			description = "Нажмите на одну из кнопок ниже для получения/удаления игровой роли"
		}

		components {
			Config.current.roles.forEach { roleInfo ->
				val role = message.getGuild().getRoleOrNull(Snowflake(roleInfo.id)) ?: return@forEach

				ephemeralButton {
					label = roleInfo.name
					style = ButtonStyle.Secondary
					roleInfo.emoji?.let { rawEmoji -> emoji(rawEmoji) }

					action {
						val member = member!!.asMember()

						if (!member.hasRole(role)) {
							member.addRole(role.id)

							respond { content = "➡ Вам выдана роль ${role.mention}" }
						}
						else {
							member.removeRole(role.id)

							respond { content = "↩ У Вас убрана роль ${role.mention}" }
						}
					}
				}
			}
		}
	}

	inner class RolesPostArguments : Arguments() {
		val channel by channel {
			name = "channel"
			description = "Канал для постинга"
			requiredChannelTypes += ChannelType.GuildText
		}
	}
}