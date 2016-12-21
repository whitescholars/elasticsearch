/**
 * Created by Mark on 2016/12/20.
 */
public class Blog {
    private Integer id;
    private String title;
    private String posttime;
    private String content;
    private Integer sum;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Blog() {
    }

    public Blog(Integer id, String title, String posttime, String content,Integer sum) {
        this.id = id;
        this.title = title;
        this.posttime = posttime;
        this.content = content;
        this.sum=sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
