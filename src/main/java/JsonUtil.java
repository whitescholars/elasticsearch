import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by Mark on 2016/12/20.
 */
public class JsonUtil {
    // Java实体对象转json对象
    public static String model2Json(Blog blog) {
        String jsonData = null;
        try {
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject().field("id", blog.getId()).field("title", blog.getTitle())
                    .field("posttime", blog.getPosttime()).field("content",blog.getContent()).field("sum",blog.getSum()).endObject();
            jsonData = jsonBuild.string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData;
    }
}
