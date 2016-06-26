package news.agoda.com.sample.view;

import com.facebook.drawee.interfaces.DraweeController;

/**
 * Created by JYHSU on 2016/6/26.
 */
public interface IDetailedView {
    String EXTRA_STORYURL = "storyURL";
    String EXTRA_TITLE = "title";
    String EXTRA_SUMMARY = "summary";
    String EXTRA_IMAGEURL = "imageURL";

    void setTitleView(String title);
    void setSummaryView(String summary);
    void setImageController(DraweeController controller);
    void setFullStoryLinkVisible(int visibility);
    DraweeController getImageViewController();
}
