package eu.rex2go.hardcore.util;

public class TimeStringUtil {

    public static String formatMillis(long time) {
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / (1000 * 60)) % 60);
        int milliseconds = (int) (time - seconds * 1000 - minutes * 1000 * 60);

        return String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds);
    }

    public static String formatSeconds(int seconds) {
        int numberOfDays;
        int numberOfHours;
        int numberOfMinutes;
        int numberOfSeconds;

        numberOfDays = seconds / 86400;
        numberOfHours = (seconds % 86400) / 3600;
        numberOfMinutes = ((seconds % 86400) % 3600) / 60;
        numberOfSeconds = ((seconds % 86400) % 3600) % 60;

        StringBuilder str = new StringBuilder();

        if (numberOfDays != 0) {
            str.append(numberOfDays).append(" ").append(numberOfDays == 1 ? "Tag" : "Tage");

            int count = 0;
            if (numberOfHours != 0) count++;
            if (numberOfMinutes != 0) count++;
            if (numberOfSeconds != 0) count++;

            if (count > 1) {
                str.append(", ");
            } else if (count == 1) {
                str.append(" ").append("und").append(" ");
            }
        }

        if (numberOfHours != 0) {
            str.append(numberOfHours).append(" ").append(numberOfHours == 1 ? "Stunde" : "Stunden");

            int count = 0;
            if (numberOfMinutes != 0) count++;
            if (numberOfSeconds != 0) count++;

            if (count > 1) {
                str.append(", ");
            } else if (count == 1) {
                str.append(" ").append("und").append(" ");
            }
        }

        if (numberOfMinutes != 0) {
            str.append(numberOfMinutes).append(" ").append(numberOfMinutes == 1 ? "Minute" : "Minuten");

            int count = 0;
            if (numberOfSeconds != 0) count++;

            if (count == 1) {
                str.append(" ").append("und").append(" ");
            }
        }

        if (numberOfSeconds != 0) {
            str.append(numberOfSeconds).append(" ").append(numberOfSeconds == 1 ? "Sekunde" : "Sekunden");
        }

        return str.toString();
    }
}
