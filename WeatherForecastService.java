import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherForecastService {
    private static final String TARGET_URL =
            "https://www.jma.go.jp/bosai/forecast/data/forecast/270000.json";

    public List<Forecast> loadForecasts() throws IOException {
        String responseBody = fetchForecastJson();
        return parseForecasts(responseBody);
    }

    private String fetchForecastJson() throws IOException {
        URL url = new URL(TARGET_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("データの取得に失敗しました。HTTPレスポンスコード: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
        }
    }

    private List<Forecast> parseForecasts(String jsonText) {
        JSONArray rootArray = new JSONArray(jsonText);
        JSONObject firstForecast = rootArray.getJSONObject(0);
        JSONObject timeSeriesObject = firstForecast
                .getJSONArray("timeSeries")
                .getJSONObject(0);

        JSONArray timeDefinesArray = timeSeriesObject.getJSONArray("timeDefines");
        JSONArray weathersArray = timeSeriesObject
                .getJSONArray("areas")
                .getJSONObject(0)
                .getJSONArray("weathers");

        List<Forecast> forecasts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        for (int i = 0; i < Math.min(timeDefinesArray.length(), weathersArray.length()); i++) {
            LocalDateTime dateTime = LocalDateTime.parse(timeDefinesArray.getString(i), formatter);
            String weather = weathersArray.getString(i);
            forecasts.add(new Forecast(dateTime, weather));
        }

        return forecasts;
    }
}
