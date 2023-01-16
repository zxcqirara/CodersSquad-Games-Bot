package config

data class PrivateRoomsConfig(
	val enabled: Boolean = true,
	val categoryId: Long = 0,
	val createChannelId: Long = 0
)
