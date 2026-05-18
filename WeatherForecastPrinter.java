import java.util.List;

public class WeatherForecastPrinter {
    public void print(List<Forecast> forecasts) {
        if (forecasts == null || forecasts.isEmpty()) {
            System.out.println("取得した天気予報データがありません。");
            return;
        }

        for (Forecast forecast : forecasts) {
            System.out.println(forecast.formatForDisplay());
        }
    }
}
