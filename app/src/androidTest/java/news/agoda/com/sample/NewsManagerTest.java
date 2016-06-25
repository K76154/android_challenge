package news.agoda.com.sample;

import android.test.InstrumentationTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by JYHSU on 2016/6/25.
 */
public class NewsManagerTest extends InstrumentationTestCase {

    @BeforeClass
    public void setUp() {
        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    @Test
    public void getCreateNewsEntity() throws JSONException {
        JSONObject jsonObject = mock(JSONObject.class);
        when(jsonObject.getString(NewsEntity.NEWS_TITLE)).thenReturn("Test Title");
        when(jsonObject.getString(NewsEntity.NEWS_URL)).thenReturn("https://test.url.com");
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
}
