package final_exam.exam.rest_service
import final_exam.exam.const.DataFrameConstant._
import final_exam.exam.data_init_process.DataWrapper
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class GameService(wrap:DataWrapper,sparkSession: SparkSession) {

  @Value("${win_bet_ratio}")
  var winBatRatio:String = null

  @Value("${user_online_time}")
  var userOnlineTime:String = null

  def getSuspiciousActivity(startDate: String,endDate: String): DataFrame ={
    val encoder: Encoder[String] = sparkSession.implicits.newStringEncoder
    val filterByDate = wrap.bet.where(col(EVENT_TIME).between(startDate.toString, endDate.toString)).persist()

    val suspiciousUserIds: DataFrame = filterByDate
      .groupBy(USER_ID)
      .agg(collect_list(EVENT_COUNTRY) as COUNTRY_LIST,collect_list(EVENT_ID) as EVENT_ID_LIST)
      .where(size(col(COUNTRY_LIST)).gt(1))
      .select(col(USER_ID),explode(col(EVENT_ID_LIST)) as EVENT_ID)

    val suspiciousTimeOfUse = filterByDate.where(col(ONLINE_TIME_SECS).gt(userOnlineTime))
      .select(col(USER_ID),col(EVENT_ID))

    val suspiciousWinRatio = filterByDate.where(col(WIN).divide(col(BET)).gt(winBatRatio))
      .select(col(USER_ID),col(EVENT_ID))

    val unionSuspiciousEvents: Dataset[Row] = suspiciousWinRatio.union(suspiciousUserIds).union(suspiciousTimeOfUse).dropDuplicates()

    val suspiciousEvents: Dataset[Row] = filterByDate.where(col(EVENT_ID)
      .isInCollection(unionSuspiciousEvents.select(col(EVENT_ID)).as[String](encoder).collect.toList))

    val frame: DataFrame = suspiciousEvents.join(wrap.user, wrap.user(ID) === suspiciousEvents(USER_ID)).drop(ID)
    filterByDate.unpersist()
    frame
  }

  def getGameStatisticByName(gameName:String,startDate: String,endDate: String):DataFrame={
    val filterByDate = wrap.bet.where(col(EVENT_TIME).between(startDate.toString, endDate.toString)).persist()
    val gameStatistic = getStatisticForGames(filterByDate.where(col(GAME_NAME)===gameName))
    filterByDate.unpersist()
    gameStatistic
  }


  def getAllGamesStatistic(startDate: LocalDateTime, endDate: LocalDateTime):DataFrame={
    val filterByDate = wrap.bet.where(col(EVENT_TIME).between(startDate.toString, endDate.toString)).persist()
    val allGameStatistic = getStatisticForGames(filterByDate)
    filterByDate.unpersist()
    allGameStatistic
  }

  def getStatisticForGames(df:DataFrame)={
    df.withColumn(PROFIT,round(col(WIN).minus(col(BET)),2))
      .groupBy(col(GAME_NAME))
      .agg(
        max(col(BET)) as "Max Bet",
        min(col(BET)) as "Min Bet",
        round(avg(col(BET)),2) as "Avg Bet",
        max(col(WIN)) as "Max Win",
        min(col(WIN)) as "Min Win",
        round(avg(col(WIN)),2) as "Avg Win",
        max(col(PROFIT)) as "Max Profit",
        min(col(PROFIT)) as "Min Profit",
        round(avg(col(PROFIT)),2) as "Avg Profit",
      )
  }
}
