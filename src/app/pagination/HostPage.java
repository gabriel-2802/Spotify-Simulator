package app.pagination;

import app.audio.Collections.Podcast;
import app.pagination.visitors.PageVisitor;
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
        
        @Override
        public void updatePage() {
                clearPage();

                for (Podcast podcast: ((Host) getHost()).getPodcasts()) {
                        getPodcasts().add(podcast.toString());
                }

                for (Announcement announcement: ((Host) getHost()).getAnnouncements()) {
                        getAnnouncements().add(announcement.toString());
                }
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
