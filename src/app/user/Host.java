package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.utils.Enums;

import java.util.ArrayList;
import java.util.List;

public class Host extends User {
    private ArrayList<Podcast> podcasts;
    public Host(String username, int age, String city, Enums.UserType type) {
        super(username, age, city, type);
        podcasts = new ArrayList<>();
    }

    @Override
    public void deleteData() {
        super.deleteData();
        podcasts.clear();
        Admin.removePlaylistsData(getUsername());
        Admin.removeHostData(getUsername());
    }
}
