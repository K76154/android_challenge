package news.agoda.com.sample.util;

import android.test.AndroidTestCase;

import com.facebook.imagepipeline.request.ImageRequest;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by JYHSU on 2016/6/26.
 */
public class ImageUtilsTest extends AndroidTestCase {
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
    public void testGetImageRequest() {
        ImageUtils imageUtils = new ImageUtils();
        ImageRequest request = imageUtils.getImageRequest("http://static01.nyt.com/images/2015/08/18/business/18EMPLOY/18EMPLOY-thumbLarge.jpg");
        assertNotNull(request);
    }

    @Test
    public void testGetImageRequest_With_Null_ImageURL() {
        ImageUtils imageUtils = new ImageUtils();
        ImageRequest request = imageUtils.getImageRequest(null);
        assertNull(request);
    }
}
