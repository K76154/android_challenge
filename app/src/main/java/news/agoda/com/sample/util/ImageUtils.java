package news.agoda.com.sample.util;

import android.net.Uri;
import android.webkit.URLUtil;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Created by JYHSU on 2016/6/26.
 */
public class ImageUtils {
    public DraweeController getImageController(ImageRequest request, DraweeController oldController) {
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(oldController).build();
    }

    public ImageRequest getImageRequest(String imageURL) {
        if(null == imageURL || !URLUtil.isValidUrl(imageURL))
            return null;

        return ImageRequest.fromUri(Uri.parse(imageURL));
    }
}
