package final_exam.exam.conf;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class SparkConfigForSpring {

    @Bean
    public SparkSession sparkSessionDev(){
        return SparkSession.builder().master("local[*]").appName("taxi").getOrCreate();
    }


}



