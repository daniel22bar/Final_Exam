package final_exam.exam.data_init_process.adjustment

import final_exam.exam.const.DataFrameConstant.{BET, EVENT_CURRENCY_CODE}
import final_exam.exam.data_init_process.DataInit
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, Row}
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
 class ConvertToUSD extends DataInit{

  @Value("${usd_eur_conversion_rate}")
  var rate:String = null

  override def doAction(value: Dataset[Row]) = {
    value.withColumn("bet",
      when(col(EVENT_CURRENCY_CODE) === "EUR",round(col(BET)*rate,2))
        .otherwise(col(BET)))
      .withColumn(EVENT_CURRENCY_CODE,when(col(EVENT_CURRENCY_CODE) === "EUR","USD").otherwise(col(EVENT_CURRENCY_CODE)))
  }


}
