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
    private static final int MAX_YEAR = 2023;
    private static final int MIN_YEAR = 1900;
    private static final int FEBRUARY = 2;
    private static final int FEBRUARY_DAYS = 28;


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
            LocalDate givenDate = LocalDate.parse(this.date, dateFormatter);
            int year = givenDate.getYear();
            int month = givenDate.getMonthValue();
            int day = givenDate.getDayOfMonth();

            if (year < MIN_YEAR || year > MAX_YEAR) {
                return false;
            }

            return month != FEBRUARY || day <= FEBRUARY_DAYS;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
