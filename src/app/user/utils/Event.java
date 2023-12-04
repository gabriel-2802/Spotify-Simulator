package app.user.utils;

import lombok.Data;

import javax.swing.text.DateFormatter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Event {
    private String name;
    private String date;
    private String description;

    public Event(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public String toString() {
        return name + " - " + date + ":\n\t" + description;
    }

    public boolean hasValidDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate date = LocalDate.parse(this.date, dateFormatter);
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            if (year < 1900 || year > 2023) {
                return false;
            }

            return month != 2 || day <= 28;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
