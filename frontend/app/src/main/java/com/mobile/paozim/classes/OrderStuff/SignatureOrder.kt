import kotlinx.serialization.Serializable

@Serializable
data class SignatureOrder(
    val productId: Int,
    val userEmail: String
)
