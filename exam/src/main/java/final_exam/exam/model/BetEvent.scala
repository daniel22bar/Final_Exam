package final_exam.exam.model

case class BetEvent(
                     eventId: Int,
                     eventTime: String,
                     eventCountry: String,
                     eventCurrencyCode: String,
                     userId: Int,
                     bet: Double,
                     gameName: String,
                     win: Double,
                     onlineTimeSecs: Int
                   ) extends Serializable
