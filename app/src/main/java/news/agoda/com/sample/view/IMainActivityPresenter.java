package news.agoda.com.sample.view;

import java.io.InputStream;
import java.util.List;

import news.agoda.com.sample.model.NewsEntity;

/**
 * Created by JYHSU on 2016/6/26.
 */
public interface IMainActivityPresenter {
    String loadResource(String sourceUrl);
    String readStream(InputStream in);
    List<NewsEntity> getNewsEntities(String data);
}
