package final_exam.exam.data_init_process

import final_exam.exam.data_init_process.adjustment.ConvertToUSD
import final_exam.exam.repository.BetRepository
import org.apache.spark.sql.{Dataset, Row}
import org.springframework.stereotype.Component


@Component
class DataInitProcess (betRepository: BetRepository,data:java.util.List[DataInit]){
    def preformInitialize() = {
      var value: Dataset[Row] = betRepository.readBet()
      val a:ConvertToUSD = new ConvertToUSD
      data.forEach(x=>{value = x.doAction(value)}) //Refactor
      value.cache()
    }
}
