package news.agoda.com.sample.view;

import android.os.Bundle;

/**
 * Created by JYHSU on 2016/6/26.
 */
public interface IDetailedViewPresenter {
    void onCreate(Bundle savedInstanceState, Bundle extras);
    String getStoryUrl();
    void setStoryUrl(String storyUrl);
}
