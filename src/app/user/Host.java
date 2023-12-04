package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Collections.PodcastOutput;
import app.audio.Files.Episode;
import app.user.utils.Announcement;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Host extends User {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    public Host(String username, int age, String city, Enums.UserType type) {
        super(username, age, city, type);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    @Override
    public void deleteData() {
        super.deleteData();
        podcasts.clear();
        Admin.removeHostData(getUsername());
    }

    @Override
    public String addPodcast(String name, String owner, List<EpisodeInput> episodeInputs) {
        List<Episode> episodes = new ArrayList<>();
        List<String> episodeNames = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription(), owner));
            episodeNames.add(episodeInput.getName());
        }

        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return getUsername() + " has another podcast with the same name.";
            }
        }

        for (String episodeName : episodeNames) {
            int count = Collections.frequency(episodeNames, episodeName);
            if (count > 1) {
                return getUsername() + " has the same episode in this podcast.";
            }
        }

        Podcast newPodcast = new Podcast(name, owner, episodes);
        podcasts.add(newPodcast);
        Admin.addPodcast(newPodcast);
        return getUsername() + " has added new podcast successfully.";
    }

    @Override
    public String addAnnouncement(String name, String message) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }

        Announcement newAnnouncement = new Announcement(name, message);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    @Override
    public String removeAnnouncement(String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                announcements.remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    @Override
    public ArrayList<PodcastOutput> showPodcasts() {
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }
        return podcastOutputs;
    }

    @Override
    public String removePodcast(String name) {
        Podcast podcastToRemove = null;
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                podcastToRemove = podcast;
                break;
            }
        }

        if (podcastToRemove == null) {
            return getUsername() + " doesn't have a podcast with the given name.";
        }

        return Admin.removePodcast(this, podcastToRemove);
    }

    @Override
    public String changePage(String pageName) {
        throw new UnsupportedOperationException("Host cannot change page.");
    }
}
