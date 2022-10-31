package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// 每个 model.Blog 对象，期望能够表示 blog 表中的一条记录
public class Blog {
    // 根据表结构，创建属性
    private int blogId;
    private String title;
    private String content;
    private int userId;
    private Timestamp postTime;

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /*public Timestamp getPostTime() {
        return postTime;
    }*/

    // 将毫秒级的时间戳 改成 String，格式化的时间模式
    public String getPostTime() {
        // 使用SimpleDateFormat 类，来完成时间戳到格式化日期时间的转换. 格式需要查询
        // 转换过程, 需要在构造方法中指定要转换的格式, 然后调用 format 来进行转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(postTime);
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
}
