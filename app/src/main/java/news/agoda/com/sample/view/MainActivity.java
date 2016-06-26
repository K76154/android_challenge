package news.agoda.com.sample.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.model.NewsEntity;
import news.agoda.com.sample.model.NewsManager;

public class MainActivity
        extends ListActivity
        implements IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private IMainActivityPresenter presenter;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        presenter = new MainActivityPresenterImpl(this);
        new LoadResourcesTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadResourcesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return presenter.loadResource();
        }

        @Override
        protected void onPostExecute(String data) {
            final List<NewsEntity> newsItemList = presenter.getNewsEntities(data);

            NewsListAdapter adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, newsItemList);
            setListAdapter(adapter);

            ListView listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsEntity newsEntity = newsItemList.get(position);
                    String title = newsEntity.getTitle();
                    String summary = newsEntity.getSummary();
                    String url = newsEntity.getArticleUrl();
                    String imageUrl = new NewsManager().getLargeThumbnailUrlForNews(newsEntity);
                    Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
                    intent.putExtra(IDetailedView.EXTRA_TITLE, title);
                    intent.putExtra(IDetailedView.EXTRA_SUMMARY, summary);
                    intent.putExtra(IDetailedView.EXTRA_STORYURL, url);
                    intent.putExtra(IDetailedView.EXTRA_IMAGEURL, imageUrl);
                    startActivity(intent);
                }
            });
        }
    }
}
