package session4;

import javax.swing.text.html.Option;
import java.util.Optional;

public class OnlineClass {

    private Integer id;

    private String title;

    private boolean closed;

    public Progress progress;



    public OnlineClass(Integer id, String title, boolean closed) {
        this.id = id;
        this.title = title;
        this.closed = closed;
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

    /**
     * Optional로 감싸주는 경우는 return 받는 경우에 해주면 된다.(getter)
     * 만약 null일 수도 있는 경우 -> ofNullable을 사용하면 감싸주면 된다.
     * 되도록 getter에 넣어라.
     */
    public Optional<Progress> getProgress() {
        // Optional로 감싸서 보내준다.
        return Optional.ofNullable(progress);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}