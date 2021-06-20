package final_exam.exam.repository

import final_exam.exam.model.BetEvent
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.springframework.stereotype.Component



@Component
class BetRepository(sparkSession: SparkSession) {
  def readBet(): Dataset[Row] = {
    val schema = org.apache.spark.sql.Encoders.product[BetEvent].schema
    sparkSession.read.schema(schema).json("data/generated_event.json")
  }
}
