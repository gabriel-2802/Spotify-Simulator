package app.pagination;

import app.audio.Collections.Podcast;
import app.pagination.visitors.Visitor;
import app.user.Host;
import app.user.User;
import app.user.utils.Announcement;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class HostPage extends Page {
        private final User host;
        private ArrayList<String> announcements;
        private ArrayList<String> podcasts;


        public HostPage(User host) {
                this.host = host;
                this.owner = host.getUsername();
                announcements = new ArrayList<>();
                podcasts = new ArrayList<>();

        }

        @Override
        public void clearPage() {
                announcements.clear();
                podcasts.clear();

        }
        @Override
        public void acceptVisitor(Visitor visitor) {
                visitor.visit(this);
        }

        @Override
        public String toString() {
                return "Podcasts:\n\t[" + String.join(", ", podcasts) + "]\n\n" +
                        "Announcements:\n\t[" + String.join(", ", announcements) + "]";
        }
}
