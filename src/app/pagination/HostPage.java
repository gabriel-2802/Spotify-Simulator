package app.pagination;

import app.user.Host;

public class HostPage extends Page {

        public HostPage(Host host) {
                this.owner = host.getUsername();

        }

        @Override
        public void clearPage() {

        }
        @Override
        public void updatePage() {

        }
}
