import java.util.Date;

public class Article {
    private int id;
    private String title;
    private String content;
    private Date createDate; // 작성 날짜
    private Date modifyDate; // 수정 날짜
    private int views;

    public Article(int id, String title, String content, Date createDate, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate; // 작성 날짜 설정
        this.modifyDate = createDate; // 초기에는 수정 날짜도 작성 날짜와 동일하게 설정
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate; // 작성 날짜 반환
    }

    public Date getModifyDate() {
        return modifyDate; // 수정 날짜 반환
    }

    public int getViews() {
        return views;
    }

    public void incrementViews() {
        views++;
    }

    public void modifyArticle(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifyDate = new Date(); // 수정 날짜 업데이트
    }
}