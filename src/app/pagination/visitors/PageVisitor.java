package app.pagination.visitors;

import app.pagination.ArtistPage;
import app.pagination.HomePage;
import app.pagination.HostPage;
import app.pagination.LikedContentPage;

/**
 * Visitor interface
 */
public interface PageVisitor {
    /**
     * Visits an artist page
     * @param page the page to be visited
     */
 void visit(ArtistPage page);

 /**
  * Visits a home page
  * @param page the page to be visited
  */
 void visit(HomePage page);

 /**
  * Visits a host page
  * @param page the page to be visited
  */
 void visit(HostPage page);

    /**
    * Visits a liked content page
    * @param page the page to be visited
    */
 void visit(LikedContentPage page);

}
