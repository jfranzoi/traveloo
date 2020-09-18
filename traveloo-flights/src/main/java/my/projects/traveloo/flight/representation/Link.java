package my.projects.traveloo.flight.representation;

public class Link {

    public static Link create(String rel, String href) {
        Link link = new Link();
        link.setRel(rel);
        link.setHref(href);
        return link;
    }

    private String rel;
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
