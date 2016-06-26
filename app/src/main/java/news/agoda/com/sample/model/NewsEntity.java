package news.agoda.com.sample.model;

import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {
    private static final String TAG = NewsEntity.class.getSimpleName();

    public static final String NEWS_TITLE = "title";
    public static final String NEWS_ABSTRACT = "abstract";
    public static final String NEWS_URL = "url";
    public static final String NEWS_BYLINE = "byline";
    public static final String NEWS_PUBLISHED_DATE = "published_date";
    public static final String NEWS_MULTIMEDIA = "multimedia";

    private String title;
    private String summary;
    private String articleUrl;
    private String byline;
    private String publishedDate;
    private List<MediaEntity> mediaEntityList;

    //JYHSU Use Builder pattern because the number of parameters is not small.
    public static class Builder {
        //JYHSU Required fields
        private final String title;
        private final String articleUrl;


        //JYHSU Optional fields
        private String publishedDate;
        private String summary = "";
        private String byline = "";
        private List<MediaEntity> mediaEntityList = null;

        public Builder(String title, String articleUrl) {
            this.title = title;
            this.articleUrl = articleUrl;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder byline(String byline) {
            this.byline = byline;
            return this;
        }

        public Builder publishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder mediaEntityList(final List<MediaEntity> mediaEntityList) {
            this.mediaEntityList = mediaEntityList;
            return this;
        }

        public NewsEntity build() {
            return new NewsEntity(this);
        }
    }

    private NewsEntity(Builder builder) {
        title = builder.title;
        summary = builder.summary;
        articleUrl = builder.articleUrl;
        byline = builder.byline;
        publishedDate = builder.publishedDate;
        mediaEntityList = builder.mediaEntityList;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<MediaEntity> getMediaEntity() {
        return mediaEntityList;
    }
}
