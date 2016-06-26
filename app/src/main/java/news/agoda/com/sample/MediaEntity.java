package news.agoda.com.sample;

/**
 * This class represents a media item
 */
public class MediaEntity {

    public static final String MEDIA_URL = "url";
    public static final String MEDIA_FORMAT = "format";
    public static final String MEDIA_HEIGHT = "height";
    public static final String MEDIA_WIDTH = "width";
    public static final String MEDIA_TYPE = "type";
    public static final String MEDIA_SUBTYPE = "subtype";
    public static final String MEDIA_CAPTION = "caption";
    public static final String MEDIA_COPYRIGHT = "copyright";

    private String url;
    private String format;
    private int height;
    private int width;
    private String type;
    private String subType;
    private String caption;
    private String copyright;

    //JYHSU Use Builder pattern because the number of parameters is not small.
    public static class Builder {
        //JYHSU Required fields
        private final String url;

        //JYHSU Optional fields
        private String format;
        private int height;
        private int width;
        private String type;
        private String subType;
        private String caption;
        private String copyright;

        public Builder(String url) {
            this.url = url;
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder subType(String subType) {
            this.subType = subType;
            return this;
        }

        public Builder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder copyright(String copyright) {
            this.copyright = copyright;
            return this;
        }

        public MediaEntity build() {
            return new MediaEntity(this);
        }
    }

    private MediaEntity(Builder builder) {
        url = builder.url;
        format = builder.format;
        height = builder.height;
        width = builder.width;
        type = builder.type;
        subType = builder.subType;
        caption = builder.caption;
        copyright = builder.copyright;
    }

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getCaption() {
        return caption;
    }

    public String getCopyright() {
        return copyright;
    }

}
