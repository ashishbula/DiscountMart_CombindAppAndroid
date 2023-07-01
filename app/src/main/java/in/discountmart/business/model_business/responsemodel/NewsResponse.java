package in.discountmart.business.model_business.responsemodel;

import java.util.ArrayList;

public class NewsResponse extends BaseResponse {
    ArrayList<NewsList> news;
    //String response;//":"OK"}


    public ArrayList<NewsList> getNews() {
        return news;
    }

    public void setNews(ArrayList<NewsList> news) {
        this.news = news;
    }

    public static class NewsList{
        String newsid;//":"1","
        String newshead;//":"Welcome:","
        String newsdetail;//":"Goldwings","
        String type;//":"News"

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getNewshead() {
            return newshead;
        }

        public void setNewshead(String newshead) {
            this.newshead = newshead;
        }

        public String getNewsdetail() {
            return newsdetail;
        }

        public void setNewsdetail(String newsdetail) {
            this.newsdetail = newsdetail;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}
