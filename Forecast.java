import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Forecast {
    private final LocalDateTime dateTime;
    private final String weather;

    public Forecast(LocalDateTime dateTime, String weather) {
        this.dateTime = dateTime;
        this.weather = weather;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getWeather() {
        return weather;
    }

    public String formatForDisplay() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + " " + weather;
    }
}
