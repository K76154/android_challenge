package news.agoda.com.sample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

import news.agoda.com.sample.R;
import news.agoda.com.sample.model.NewsEntity;
import news.agoda.com.sample.model.NewsManager;
import news.agoda.com.sample.util.ImageUtils;

public class NewsListAdapter extends ArrayAdapter<NewsEntity> {
    private static class ViewHolder {
        TextView newsTitle;
        DraweeView imageView;
    }

    public NewsListAdapter(Context context, int resource, List<NewsEntity> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_news, parent, false);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.imageView = (DraweeView) convertView.findViewById(R.id.news_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsEntity newsEntity = getItem(position);
        viewHolder.newsTitle.setText(newsEntity.getTitle());
        updateThumbnail(viewHolder, newsEntity);

        return convertView;
    }

    public void updateThumbnail(ViewHolder viewHolder, NewsEntity newsEntity) {
        ImageUtils imageUtils = new ImageUtils();
        ImageRequest request = imageUtils.getImageRequest(new NewsManager().getThumbnailUrlForNews(newsEntity));
        if(null != request) {
            DraweeController draweeController = imageUtils.getImageController(request,
                    viewHolder.imageView.getController());
            viewHolder.imageView.setController(draweeController);
        }
    }
}
