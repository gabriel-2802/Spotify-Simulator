package app.user.utils;

import lombok.Data;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Event class
 */
@Data
public class Event {
    private String name;
    private String date;
    private String description;

    public Event(final String name, final String date, final String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    /**
     * Returns a string representation of the event
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        return name + " - " + date + ":\n\t" + description;
    }

    /**
     * Checks if the date of the event is valid
     * @return true if the date is valid, false otherwise
     */
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
