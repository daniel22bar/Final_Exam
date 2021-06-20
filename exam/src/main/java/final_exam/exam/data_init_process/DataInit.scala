package final_exam.exam.data_init_process

import org.apache.spark.sql.{Dataset, Row}

trait DataInit {
    def doAction(value: Dataset[Row]):Dataset[Row];
}
