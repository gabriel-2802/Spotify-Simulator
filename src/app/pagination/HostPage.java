package app.pagination;

import app.audio.Collections.Podcast;
import app.user.Host;
import app.user.User;
import app.user.utils.Announcement;

import java.util.ArrayList;

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
        public void updatePage() {
                clearPage();

               for (Podcast podcast: ((Host)host).getPodcasts()) {
                        podcasts.add(podcast.toString());
                }

                for (Announcement announcement: ((Host)host).getAnnouncements()) {
                        announcements.add(announcement.toString());
                }
        }

        @Override
        public String toString() {
                return "Podcasts:\n\t[" + String.join(", ", podcasts) + "]\n\n" +
                        "Announcements:\n\t[" + String.join(", ", announcements) + "]";
        }
}
