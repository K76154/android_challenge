package news.agoda.com.sample;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JYHSU This is where we put the logic for handling news related data. The separation of data access and data logic is easier to unit test and
 * easier to extend in the future.
 */
public class NewsManager {
    private static final String TAG = NewsManager.class.getSimpleName();

    public NewsEntity createNewsEntity(final JSONObject jsonObject) throws JSONException {
            List<MediaEntity> mediaEntityList = new ArrayList<>();
            String title = jsonObject.getString(NewsEntity.NEWS_TITLE);
            String summary = jsonObject.getString(NewsEntity.NEWS_ABSTRACT);
            String articleUrl = jsonObject.getString(NewsEntity.NEWS_URL);
            String byline = jsonObject.getString(NewsEntity.NEWS_BYLINE);
            String publishedDate = jsonObject.getString(NewsEntity.NEWS_PUBLISHED_DATE);
            JSONArray mediaArray = jsonObject.getJSONArray(NewsEntity.NEWS_MULTIMEDIA);
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                try {
                    MediaEntity mediaEntity = this.createMediaEntity(mediaObject);
                    mediaEntityList.add(mediaEntity);
                }
                catch(JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

        return new NewsEntity.Builder(title, articleUrl, publishedDate).byline(byline).summary(summary)
                .mediaEntityList(mediaEntityList).build();
    }

    public MediaEntity createMediaEntity(final JSONObject jsonObject) throws JSONException {
        String url = jsonObject.getString(MediaEntity.MEDIA_URL);
        String format = jsonObject.getString(MediaEntity.MEDIA_FORMAT);
        int height = jsonObject.getInt(MediaEntity.MEDIA_HEIGHT);
        int width = jsonObject.getInt(MediaEntity.MEDIA_WIDTH);
        String type = jsonObject.getString(MediaEntity.MEDIA_TYPE);
        String subType = jsonObject.getString(MediaEntity.MEDIA_SUBTYPE);
        String caption = jsonObject.getString(MediaEntity.MEDIA_CAPTION);
        String copyright = jsonObject.getString(MediaEntity.MEDIA_COPYRIGHT);
        return new MediaEntity.Builder(url, format, height, width, type, subType)
                .caption(caption).copyright(copyright).build();
    }

    public String getThumbnailUrlForNews(final NewsEntity newsEntity) {
        List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
        if(mediaEntityList.size() > 0) {
            MediaEntity mediaEntity = mediaEntityList.get(0);
            return mediaEntity.getUrl();
        }

        return null;
    }
}
