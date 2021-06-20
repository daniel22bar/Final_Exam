package final_exam.exam.data_init_process.validators

import final_exam.exam.const.DataFrameConstant.{EVENT_COUNTRY, GAME_NAME}
import final_exam.exam.data_init_process.DataInit
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Dataset, Row}
import org.springframework.stereotype.Component

@Component
class FilterUsDemoGames extends DataInit{
  override def doAction(value: Dataset[Row]): Dataset[Row] = {
    value.filter(!(col(EVENT_COUNTRY) === "USA" && col(GAME_NAME).contains("-demo")))
  }
}
