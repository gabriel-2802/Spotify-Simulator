package app.pagination;

import app.pagination.visitors.PageVisitor;
import app.user.User;
import lombok.Getter;
import java.util.ArrayList;

@Getter
public class HostPage extends Page {
        private final User host;
        private ArrayList<String> announcements;
        private ArrayList<String> podcasts;


        public HostPage(final User host) {
                this.host = host;
                this.owner = host.getUsername();
                announcements = new ArrayList<>();
                podcasts = new ArrayList<>();

        }

        /**
         * Clears the page
         */
        @Override
        public void clearPage() {
                announcements.clear();
                podcasts.clear();

        }

        /**
         * Accepts a visitor
         * @param visitor the visitor
         */
        @Override
        public void acceptVisitor(final PageVisitor visitor) {
                visitor.visit(this);
        }

        /**
         * Returns a string representation of the page
         * @return a string representation of the page
         */
        @Override
        public String toString() {
                return "Podcasts:\n\t[" + String.join(", ", podcasts) + "]\n\n"
                        + "Announcements:\n\t[" + String.join(", ", announcements) + "]";
        }
}
