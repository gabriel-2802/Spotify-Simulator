package app.pagination.visitors;

/**
 * Visitor interface used for visiting pages
 */
public interface Visitable {
    /**
     * Accepts a visitor
     * @param visitor the visitor
     */
    void acceptVisitor(PageVisitor visitor);
}
