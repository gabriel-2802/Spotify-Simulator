package app.pagination;

import app.pagination.visitors.Visitable;
import app.pagination.visitors.PageVisitor;
import lombok.Getter;

/**
 * Abstract class for pages
 */
@Getter
public abstract class Page {
    // The owner of the page
    protected String owner;;

    /**
     * Clears the page
     */
   public abstract void clearPage();

    /**
     * updates the page
     */
    public abstract void updatePage();
}
