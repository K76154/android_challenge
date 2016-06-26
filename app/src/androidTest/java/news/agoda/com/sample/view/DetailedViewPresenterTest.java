package news.agoda.com.sample.view;

import android.os.Bundle;
import android.test.InstrumentationTestCase;
import android.view.View;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import news.agoda.com.sample.util.ImageUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by JYHSU on 2016/6/26.
 */
public class DetailedViewPresenterTest extends InstrumentationTestCase {
    //JYHSU Workaround for the dexmaker problem. I can make this in a super class and make all my test classes inherit it, but that will be a
    //very bad practice, as it is against the favor composition over inheritance guideline. When they eventually fix this problem, I am left with
    //a useless super class, and had to clean it up manually. Or worse, someone else saw that super class and added whole bunch of codes
    //into that, and we have a architecture problem.
    @BeforeClass
    public void setUp() {
        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    @Test
    public void testOnCreate() {
        IDetailedView detailedView = Mockito.mock(IDetailedView.class);
        DetailedViewPresenterImpl presenter = Mockito.spy(new DetailedViewPresenterImpl(detailedView));
        Bundle extras = new Bundle();
        extras.putString(IDetailedView.EXTRA_TITLE, "title");
        extras.putString(IDetailedView.EXTRA_SUMMARY, "summary");
        extras.putString(IDetailedView.EXTRA_IMAGEURL, "http://static01.nyt.com/images/2015/08/18/business/18EMPLOY/18EMPLOY-thumbLarge.jpg");
        extras.putString(IDetailedView.EXTRA_STORYURL, "http://www.nytimes.com/2015/08/13/technology/personaltech/new-school-technology-for-class-and-the-quad.html");
        DraweeController mockController = Mockito.mock(DraweeController.class);
        ImageUtils imageUtils = Mockito.spy(new ImageUtils());
        doReturn(mockController).when(imageUtils).getImageController(any(ImageRequest.class), any(DraweeController.class));
        doReturn(imageUtils).when(presenter).getImageUtils();
        presenter.onCreate(null, extras);
        verify(detailedView, times(1)).setTitleView("title");
        verify(detailedView, times(1)).setSummaryView("summary");
        verify(detailedView, times(1)).setImageController(mockController);
    }

    @Test
    public void testOnCreate_Without_StoryUrl() {
        IDetailedView detailedView = Mockito.mock(IDetailedView.class);
        DetailedViewPresenterImpl presenter = Mockito.spy(new DetailedViewPresenterImpl(detailedView));
        Bundle extras = new Bundle();
        extras.putString(IDetailedView.EXTRA_TITLE, "title");
        extras.putString(IDetailedView.EXTRA_SUMMARY, "summary");
        presenter.onCreate(null, extras);
        verify(detailedView, times(1)).setFullStoryLinkVisible(View.INVISIBLE);
    }
}
