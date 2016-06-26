package news.agoda.com.sample.view;

import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;

import com.facebook.imagepipeline.request.ImageRequest;

import news.agoda.com.sample.util.ImageUtils;

/**
 * Created by JYHSU on 2016/6/26.
 */
public class DetailedViewPresenterImpl implements IDetailedViewPresenter {
    private final IDetailedView detailedView;
    private String storyUrl;

    DetailedViewPresenterImpl(IDetailedView detailedView) {
        this.detailedView = detailedView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Bundle extras) {
        this.storyUrl = extras.getString(IDetailedView.EXTRA_STORYURL);
        String title = extras.getString(IDetailedView.EXTRA_TITLE);
        String summary = extras.getString(IDetailedView.EXTRA_SUMMARY);
        String imageURL = extras.getString(IDetailedView.EXTRA_IMAGEURL);
        if(!URLUtil.isValidUrl(this.storyUrl))
            detailedView.setFullStoryLinkVisible(View.INVISIBLE);

        detailedView.setTitleView(title);
        detailedView.setSummaryView(summary);
        ImageUtils imageUtils = getImageUtils();
        ImageRequest request = imageUtils.getImageRequest(imageURL);
        if(null != request)
            detailedView.setImageController(imageUtils.getImageController(request,
                    detailedView.getImageViewController()));
    }

    @Override
    public String getStoryUrl() {
        return this.storyUrl;
    }

    @Override
    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public ImageUtils getImageUtils() {
        return new ImageUtils();
    }
}
