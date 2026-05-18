import java.util.List;

/**
 * 天気予報アプリ - 本体
 * このアプリケーションは、気象庁のWeb APIから大阪府の天気予報データを取得して表示します
 * @author n.katayama
 * @version 1.0
 */
public class WeatherForecastApp {

    /**
     * メイン処理。天気予報の取得と表示を実行します
     *
     * @param args コマンドライン引数（使用しません）
     */
    public static void main(String[] args) {
        try {
            WeatherForecastService service = new WeatherForecastService();
            List<Forecast> forecasts = service.loadForecasts();
            new WeatherForecastPrinter().print(forecasts);
        } catch (Exception e) {
            System.err.println("天気予報の取得または表示に失敗しました。詳細を確認してください。");
            e.printStackTrace();
        }
    }
}