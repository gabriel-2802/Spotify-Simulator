package app.pagination.visitors;

import app.pagination.ArtistPage;
import app.pagination.HomePage;
import app.pagination.HostPage;
import app.pagination.LikedContentPage;

public interface Visitor {
    public void visit(ArtistPage page);
    public void visit(HomePage page);
    public void visit(HostPage page);
    public void visit(LikedContentPage page);

}
