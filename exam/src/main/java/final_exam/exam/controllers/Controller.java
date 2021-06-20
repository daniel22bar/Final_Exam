package final_exam.exam.controllers;

import final_exam.exam.rest_service.GameService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/game")
public class Controller {
    @Autowired
    private GameService gameService;

    @GetMapping("suspicious")
    public String showSuspiciousGameActivityByDateRange(@RequestParam String startDate,@RequestParam String endDate) {
        return gameService.getSuspiciousActivity(startDate,endDate).toJSON().collectAsList().toString();
    }

    @GetMapping("gameStatistic")
    public String showGameStatisticByName(@RequestParam String gameName,@RequestParam String startDate,@RequestParam String endDate) {
        return gameService.getGameStatisticByName(gameName,startDate,endDate).toJSON().collectAsList().toString();
    }

    @SneakyThrows
    @GetMapping("allGameStatistic")
    public String showAllGameStatistic(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return gameService.getAllGamesStatistic(startDate,endDate).toJSON().collectAsList().toString();
    }


}
