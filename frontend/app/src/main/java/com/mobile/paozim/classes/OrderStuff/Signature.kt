import kotlinx.serialization.Serializable

@Serializable
data class Signature(
    val id: Int,
    val itemID: Int,
    val userEmail: String,
    val totalPrice: Double,
    val quantity: Int,
    var status: String,
    val arriveTime: String,
    val dayStart: String,
    val frequency: String,
    var currPeriod: Int,
    val totalPeriod: Int
)
