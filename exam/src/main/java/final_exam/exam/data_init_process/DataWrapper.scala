package final_exam.exam.data_init_process

import final_exam.exam.model.ScalaUser
import final_exam.exam.repository.UserRepository
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._


@Component
class DataWrapper(dataInitProcess: DataInitProcess,userRepository: UserRepository,sparkSession: SparkSession) {
    import sparkSession.implicits._
    val bet:Dataset[Row] = dataInitProcess.preformInitialize();
    val user:Dataset[ScalaUser] = sparkSession.createDataset(userRepository.findAll().asScala.toList.map(
        x=>ScalaUser(x.getId,x.getName,x.getLastName,x.getCountryOfOrigin,x.getEmail)
    ));
}
