package app.pagination;

import lombok.Getter;

@Getter
public abstract class Page {
    protected String owner;
    abstract public void updatePage();
    abstract public void clearPage();
}
