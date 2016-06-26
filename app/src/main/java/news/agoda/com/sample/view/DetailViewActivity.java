package news.agoda.com.sample.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;

import news.agoda.com.sample.R;

/**
 * News detail view
 */
public class DetailViewActivity extends Activity implements IDetailedView {
    private IDetailedViewPresenter presenter;
    private TextView titleView;
    private DraweeView imageView;
    private TextView summaryView;
    private Button fullStoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.titleView = (TextView) findViewById(R.id.title);
        this.imageView = (DraweeView) findViewById(R.id.news_image);
        this.summaryView = (TextView) findViewById(R.id.summary_content);
        this.fullStoryButton = (Button) findViewById(R.id.full_story_link);
        presenter = new DetailedViewPresenterImpl(this);
        presenter.onCreate(savedInstanceState, getIntent().getExtras());
    }

    public void onFullStoryClicked(View view) {
        String storyUrl = presenter.getStoryUrl();
        //JYHSU We already checked the url is valid in the presenter, but just in case
        if(URLUtil.isValidUrl(storyUrl)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(storyUrl));
            startActivity(intent);
        }
        //JYHSU In case something rare happens and the url is not valid, we may want to prompt user a dialog here. However, that's beyond
        // the scope of this simple test.
    }

    @Override
    public void setTitleView(String title) {
        this.titleView.setText(title);
    }

    @Override
    public void setSummaryView(String summary) {
        this.summaryView.setText(summary);
    }

    @Override
    public void setImageController(DraweeController controller) {
        this.imageView.setController(controller);
    }

    @Override
    public DraweeController getImageViewController() {
        return this.imageView.getController();
    }

    @Override
    public void setFullStoryLinkVisible(int visiblity) {
        this.fullStoryButton.setVisibility(visiblity);
    }
}
