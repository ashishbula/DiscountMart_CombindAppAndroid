package in.discountmart.business.model_business.requestmodel;

public class MyDirectRequest extends BaseRequest {

    //String userid;//":"DT009189","
    //String reqtype;//":"directs","
    //String passwd;//":"mission@d","
    String levelid;//":"","
    String level;//":"","
    String from;//":"1","
    String to;//":"10"}
    String status;
    String legno;
    String pageno;
    String startdate;
    String enddate;
    String search;

    public String getLegno() {
        return legno;
    }

    public void setLegno(String legno) {
        this.legno = legno;
    }

    public String getLevelid() {
        return levelid;
    }

    public void setLevelid(String levelid) {
        this.levelid = levelid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
