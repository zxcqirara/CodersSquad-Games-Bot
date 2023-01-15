package config

data class DiscordConfig(
	val token: String = "PASTE TOKEN HERE",
	val guildId: Long = 0,
	val roles: List<RoleObject> = listOf(RoleObject()),
	val privateRooms: PrivateRoomsConfig = PrivateRoomsConfig()
)
