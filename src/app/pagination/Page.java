package app.pagination;

import app.pagination.visitors.Visitable;
import app.pagination.visitors.PageVisitor;
import lombok.Getter;

/**
 * Abstract class for pages
 */
@Getter
public abstract class Page implements Visitable {
    // The owner of the page
    protected String owner;;

    /**
     * Clears the page
     */
   public abstract void clearPage();

    /**
     * Accepts a visitor
     * @param visitor the visitor
     */
    public abstract void acceptVisitor(PageVisitor visitor);
}
