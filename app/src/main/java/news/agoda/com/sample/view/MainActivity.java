package news.agoda.com.sample.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
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
    private LoadResourcesTask loadResourcesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        presenter = new MainActivityPresenterImpl(this);
        loadResourcesTask = new LoadResourcesTask();
        loadResourcesTask.mainActivity = this;
        loadResourcesTask.execute("http://www.mocky.io/v2/573c89f31100004a1daa8adb");
    }

    @Override
    protected void onDestroy() {
        loadResourcesTask.cancel(false);
        loadResourcesTask.mainActivity = null;
        super.onDestroy();
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

    static class LoadResourcesTask extends AsyncTask<String, Void, String> {
        private MainActivity mainActivity = null;

        @Override
        protected String doInBackground(String... strings) {
            if(null != strings[0]) {
                String data = mainActivity.presenter.loadResource(strings[0]);
                if(!isCancelled())
                    return data;
                else
                    return null;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(String data) {
            //JYHSU Something went wrong when loading the data. In that case we may want to prompt a dialog telling the user something
            //went wrong, but it's beyond the scope of this simple test.
            if(TextUtils.isEmpty(data))
                return;

            final List<NewsEntity> newsItemList = mainActivity.presenter.getNewsEntities(data);

            //JYHSU There may also be something wrong when parsing the JSON and result in a null list. In the case of null list,
            // we may want to prompt a dialog to tell the user something went wrong, but it's beyond the scope of this simple test.
            if(isCancelled() || null == newsItemList)
                return;

            NewsListAdapter adapter = new NewsListAdapter(mainActivity, R.layout.list_item_news, newsItemList);
            mainActivity.setListAdapter(adapter);

            ListView listView = mainActivity.getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsEntity newsEntity = newsItemList.get(position);
                    String title = newsEntity.getTitle();
                    String summary = newsEntity.getSummary();
                    String url = newsEntity.getArticleUrl();
                    String imageUrl = new NewsManager().getLargeThumbnailUrlForNews(newsEntity);
                    Intent intent = new Intent(mainActivity, DetailViewActivity.class);
                    intent.putExtra(IDetailedView.EXTRA_TITLE, title);
                    intent.putExtra(IDetailedView.EXTRA_SUMMARY, summary);
                    intent.putExtra(IDetailedView.EXTRA_STORYURL, url);
                    intent.putExtra(IDetailedView.EXTRA_IMAGEURL, imageUrl);
                    mainActivity.startActivity(intent);
                }
            });
        }
    }
}
