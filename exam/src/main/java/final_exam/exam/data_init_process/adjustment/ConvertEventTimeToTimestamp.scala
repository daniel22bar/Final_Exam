package final_exam.exam.data_init_process.adjustment

import final_exam.exam.const.DataFrameConstant.EVENT_TIME
import final_exam.exam.data_init_process.DataInit
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Dataset, Row}
import org.springframework.stereotype.Component

@Component
class ConvertEventTimeToTimestamp extends DataInit{
  override def doAction(value: Dataset[Row]) = {
    value.withColumn(EVENT_TIME,col(EVENT_TIME).cast("timestamp"))
  }
}
