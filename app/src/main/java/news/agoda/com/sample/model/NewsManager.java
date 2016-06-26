package news.agoda.com.sample.model;

import android.text.TextUtils;
import android.webkit.URLUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JYHSU This is where we put the logic for handling news related data. The separation of data access and data logic is easier to unit test and
 * easier to extend in the future.
 */
public class NewsManager {
    private static final String TAG = NewsManager.class.getSimpleName();

    public NewsEntity createNewsEntity(JSONObject jsonObject) {
        if(null == jsonObject)
            return null;

        String articleUrl = jsonObject.optString(NewsEntity.NEWS_URL, null);
        //JYHSU Not a valid URL, or not http/https
        if(!(URLUtil.isValidUrl(articleUrl) && (URLUtil.isHttpsUrl(articleUrl) || URLUtil.isHttpUrl(articleUrl))))
            return null;

        String title = jsonObject.optString(NewsEntity.NEWS_TITLE, null);
        if(TextUtils.isEmpty(title))
            return null;

        String summary = jsonObject.optString(NewsEntity.NEWS_ABSTRACT, null);
        String byline = jsonObject.optString(NewsEntity.NEWS_BYLINE, null);
        String publishedDate = jsonObject.optString(NewsEntity.NEWS_PUBLISHED_DATE, null);

        NewsEntity.Builder builder = new NewsEntity.Builder(title, articleUrl).byline(byline).summary(summary)
                .publishedDate(publishedDate);

        JSONArray mediaArray = jsonObject.optJSONArray(NewsEntity.NEWS_MULTIMEDIA);
        if(null != mediaArray) {
            List<MediaEntity> mediaEntityList = new ArrayList<>();
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.optJSONObject(i);
                MediaEntity mediaEntity = this.createMediaEntity(mediaObject);
                if (null != mediaEntity)
                    mediaEntityList.add(mediaEntity);
            }

            if (mediaEntityList.size() > 0)
                builder.mediaEntityList(mediaEntityList);
        }

        return builder.build();
    }

    public MediaEntity createMediaEntity(JSONObject jsonObject) {
        if(null == jsonObject)
            return null;

        String url = jsonObject.optString(MediaEntity.MEDIA_URL, null);
        if(!URLUtil.isValidUrl(url))
            return null;

        String format = jsonObject.optString(MediaEntity.MEDIA_FORMAT, null);
        int height = jsonObject.optInt(MediaEntity.MEDIA_HEIGHT);
        int width = jsonObject.optInt(MediaEntity.MEDIA_WIDTH);
        String type = jsonObject.optString(MediaEntity.MEDIA_TYPE, null);
        String subType = jsonObject.optString(MediaEntity.MEDIA_SUBTYPE, null);
        String caption = jsonObject.optString(MediaEntity.MEDIA_CAPTION, null);
        String copyright = jsonObject.optString(MediaEntity.MEDIA_COPYRIGHT, null);
        return new MediaEntity.Builder(url).format(format).height(height).width(width).type(type)
                .subType(subType).caption(caption).copyright(copyright).build();
    }

    public String getThumbnailUrlForNews(final NewsEntity newsEntity) {
        return getImageUrlForNews(newsEntity, 0);
    }

    public String getLargeThumbnailUrlForNews(final NewsEntity newsEntity) {
        return getImageUrlForNews(newsEntity, 1);
    }

    private String getImageUrlForNews(final NewsEntity newsEntity, int index) {
        List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
        if(null != mediaEntityList && mediaEntityList.size() > index) {
            MediaEntity mediaEntity = mediaEntityList.get(index);
            return mediaEntity.getUrl();
        }

        return null;
    }
}
