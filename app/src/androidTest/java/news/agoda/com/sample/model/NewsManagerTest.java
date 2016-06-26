package news.agoda.com.sample.model;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by JYHSU on 2016/6/25.
 */
public class NewsManagerTest extends AndroidTestCase {

    //JYHSU Workaround for the dexmaker problem. I can make this in a super class and make all my test classes inherit it, but that will be a
    //very bad practice, as it is against the favor composition over inheritance guideline. When they eventually fix this problem, I am left with
    //a useless super class, and had to clean it up manually. Or worse, someone else saw that super class and added whole bunch of codes
    //into that, and we have a architecture problem.
    @BeforeClass
    public void setUp() {
        System.setProperty(
                "dexmaker.dexcache",
                getContext().getCacheDir().getPath());
    }

    @Test
    public void testCreateNewsEntity() throws JSONException {
        performCreateNewsEntityTest(false, true, "http://test.news", "Test News", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Empty_Url() throws JSONException {
        performCreateNewsEntityTest(true, true, null, "Test News", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Null_Url() throws JSONException {
        performCreateNewsEntityTest(true, true, "", "Test News", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Non_HTTP_Url() throws JSONException {
        performCreateNewsEntityTest(true, true, "file://testfile.tx", "Test News", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Null_Title() throws JSONException {
        performCreateNewsEntityTest(true, true, "http://test.news", null, "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Empty_Title() throws JSONException {
        performCreateNewsEntityTest(true, true, "http://test.news", "", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateNewsEntity_With_Only_Required_Fields() throws JSONException {
        performCreateNewsEntityTest(false, true, "http://test.news", "Title", null, null,
                null);
    }

    @Test
    public void testCreateNewsEntity_Without_Media_Entity() throws JSONException {
        performCreateNewsEntityTest(false, false, "http://test.news", "Test News", "summary", "by",
                "2015-08-18T04:00:00-5:00");
    }

    @Test
    public void testCreateMediaEntity() throws JSONException {
        performCreateMediaEntityTest(false, "http://test.media", "Standard Thumbnail", 75, 75,
                "image", "photo", "caption", "copyright");
    }

    @Test
    public void testCreateMediaEntity_With_Empty_URL() throws JSONException {
        performCreateMediaEntityTest(true, "", "Standard Thumbnail", 75, 75,
                "image", "photo", "caption", "copyright");
    }

    @Test
    public void testCreateMediaEntity_With_Null_URL() throws JSONException {
        performCreateMediaEntityTest(true, null, "Standard Thumbnail", 75, 75,
                "image", "photo", "caption", "copyright");
    }

    @Test
    public void testCreateMediaEntity_With_Only_Required_Fields() throws JSONException {
        performCreateMediaEntityTest(false, "http://test.media", null, 0, 0,
                null, null, null, null);
    }

    @Test
    public void testGetThumbnailUrl_With_Empty_MediaEntry_List() {
        NewsManager helper = new NewsManager();
        NewsEntity newsEntity = mock(NewsEntity.class);
        when(newsEntity.getMediaEntity()).thenReturn(new ArrayList<MediaEntity>());
        assertNull(helper.getThumbnailUrlForNews(newsEntity));
    }

    @Test
    public void testGetThumbnailUrl_With_Null_URL() {
        NewsManager helper = new NewsManager();
        NewsEntity newsEntity = mock(NewsEntity.class);
        List<MediaEntity> mediaEntities = new ArrayList<MediaEntity>();
        MediaEntity mediaEntity = mock(MediaEntity.class);
        when(mediaEntity.getUrl()).thenReturn(null);
        mediaEntities.add(mediaEntity);
        when(newsEntity.getMediaEntity()).thenReturn(mediaEntities);
        assertNull(helper.getThumbnailUrlForNews(newsEntity));
    }

    @Test
    public void testGetThumbnailUrl_With_Non_Null_URL() {
        NewsManager helper = new NewsManager();
        NewsEntity newsEntity = mock(NewsEntity.class);
        List<MediaEntity> mediaEntities = new ArrayList<MediaEntity>();
        MediaEntity mediaEntity = mock(MediaEntity.class);
        when(mediaEntity.getUrl()).thenReturn("http://some.url");
        mediaEntities.add(mediaEntity);
        when(newsEntity.getMediaEntity()).thenReturn(mediaEntities);
        assertNotNull(helper.getThumbnailUrlForNews(newsEntity));
    }

    @Test
    public void testGetThumbnailUrl_With_Null_MediaEntity() {
        NewsManager helper = new NewsManager();
        NewsEntity newsEntity = mock(NewsEntity.class);
        when(newsEntity.getMediaEntity()).thenReturn(null);
        assertNull(helper.getThumbnailUrlForNews(newsEntity));
    }

    private JSONObject getNewsEntityJSONObject(String title, String url, String summary, String byline,
                                                   String publishedDate, List<JSONObject> mediaArray) throws JSONException {
        Map<String, Object> newsEntityMap = new HashMap<String, Object>();
        putIntoMap(newsEntityMap, NewsEntity.NEWS_TITLE, title);
        putIntoMap(newsEntityMap, NewsEntity.NEWS_URL, url);
        putIntoMap(newsEntityMap, NewsEntity.NEWS_ABSTRACT, summary);
        putIntoMap(newsEntityMap, NewsEntity.NEWS_BYLINE, byline);
        putIntoMap(newsEntityMap, NewsEntity.NEWS_PUBLISHED_DATE, publishedDate);

        JSONObject jsonObject = new JSONObject(newsEntityMap);
        JSONArray jsonArray = new JSONArray();
        if(null != mediaArray) {
            for (JSONObject mediaEntityJsonObject : mediaArray) {
                jsonArray.put(mediaEntityJsonObject);
            }
            jsonObject.put(NewsEntity.NEWS_MULTIMEDIA, jsonArray.length() > 0 ? jsonArray : "");
        }


        return jsonObject;
    }

    private JSONObject getMediaEntityJSONObject(String url, String format, int height, int width,
                                                    String type, String subtype, String caption, String copyright) throws JSONException {
        Map<String, Object> mediaEntityMap = new HashMap<String, Object>();
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_URL, url);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_FORMAT, format);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_HEIGHT, height);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_WIDTH, width);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_TYPE, type);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_SUBTYPE, subtype);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_CAPTION, caption);
        putIntoMap(mediaEntityMap, MediaEntity.MEDIA_COPYRIGHT, copyright);
        return new JSONObject(mediaEntityMap);
    }

    private void performCreateNewsEntityTest(boolean shouldBeNull, boolean hasMediaEntity, String url, String title, String summary,
                                             String byline, String publishedDate) throws JSONException {
        List<JSONObject> mediaEntities = new ArrayList<>();
        if(hasMediaEntity) {
            JSONObject mediaEntityObject = getMediaEntityJSONObject(url, "Standard Thumbnail", 75, 75,
                    "image", "photo", "caption", "copyright");
            mediaEntities.add(mediaEntityObject);
        }
        JSONObject newsEntityObject = getNewsEntityJSONObject(title, url, summary, byline,
                publishedDate, mediaEntities);
        NewsEntity newsEntity = new NewsManager().createNewsEntity(newsEntityObject);
        assertEquals(shouldBeNull, null == newsEntity);
        if(!shouldBeNull) {
            assertEquals(newsEntity.getArticleUrl(), url);
            assertEquals(newsEntity.getTitle(), title);
            assertEquals(newsEntity.getSummary(), summary);
            assertEquals(newsEntity.getByline(), byline);
            assertEquals(newsEntity.getPublishedDate(), publishedDate);
            if(hasMediaEntity) {
                List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
                assertNotNull(mediaEntityList);
                assertEquals(mediaEntityList.size(), 1);
            }
        }
    }

    private void performCreateMediaEntityTest(boolean shouldBeNull, String url, String format, int height, int width,
                                              String type, String subType, String caption, String copyright) throws JSONException {
        JSONObject mediaEntityObject = getMediaEntityJSONObject(url, format, height, width,
                type, subType, caption, copyright);
        MediaEntity mediaEntity = new NewsManager().createMediaEntity(mediaEntityObject);
        assertEquals(shouldBeNull, null == mediaEntity);
        if(!shouldBeNull) {
            assertEquals(mediaEntity.getFormat(), format);
            assertEquals(mediaEntity.getHeight(), height);
            assertEquals(mediaEntity.getWidth(), width);
            assertEquals(mediaEntity.getType(), type);
            assertEquals(mediaEntity.getSubType(), subType);
            assertEquals(mediaEntity.getCaption(), caption);
            assertEquals(mediaEntity.getCopyright(), copyright);
        }
    }

    private void putIntoMap(Map<String, Object> map, String key, Object value) {
        if(null != value)
            map.put(key, value);
    }
}
