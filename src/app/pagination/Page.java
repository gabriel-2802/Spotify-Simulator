package app.pagination;

import app.pagination.visitors.Visitable;
import app.pagination.visitors.Visitor;
import lombok.Getter;

@Getter
public abstract class Page implements Visitable {
    protected String owner;
//    abstract public void updatePage();
    abstract public void clearPage();
   // abstract public void acceptVisitor(Visitor visitor);
    abstract public void acceptVisitor(Visitor visitor);
}
