package news.agoda.com.sample.view;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.model.NewsEntity;
import news.agoda.com.sample.model.NewsManager;

/**
 * Created by JYHSU on 2016/6/26.
 */
public class MainActivityPresenterImpl implements IMainActivityPresenter {
    private static final String TAG = MainActivityPresenterImpl.class.getSimpleName();
    private final IMainView mainView;

    MainActivityPresenterImpl(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public String loadResource(String sourceUrl) {
        try {
            URL url = new URL(sourceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public List<NewsEntity> getNewsEntities(String data) {
        JSONObject jsonObject;
        NewsManager manager = new NewsManager();
        List<NewsEntity> newsItemList = new ArrayList<NewsEntity>();
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            Log.e(TAG, "fail to create JSON object");
            return null;
        }

        JSONArray resultArray = jsonObject.optJSONArray("results");
        if(null == resultArray) {
            Log.e(TAG, "fail to parse JSON data");
            return null;
        }

        for (int i = 0; i < resultArray.length(); i++) {
            NewsEntity newsEntity = manager.createNewsEntity(resultArray.optJSONObject(i));
            if(null != newsEntity)
                newsItemList.add(newsEntity);
        }

        return newsItemList;
    }
}
