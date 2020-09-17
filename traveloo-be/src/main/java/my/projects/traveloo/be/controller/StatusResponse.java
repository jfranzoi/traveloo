package my.projects.traveloo.be.controller;

public class StatusResponse {

    private String global;
    private String be;
    private String flights;

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getBe() {
        return be;
    }

    public void setBe(String be) {
        this.be = be;
    }

    public void setFlights(String flights) {
        this.flights = flights;
    }

    public String getFlights() {
        return flights;
    }
}
