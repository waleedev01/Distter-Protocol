import java.util.ArrayList;
//
public class Post {
    //all post fields
    private String postId;
    private String created;
    private String author;
    ArrayList<String> tags = new ArrayList<String>(); // Create an ArrayList object
    private String content;
    ArrayList<String> lines = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> replies = new ArrayList<String>(); // Create an ArrayList object
    /**
     * Initialise a new Post. To Craete a post, create object Post().
     */
    public Post() {
    }
    //getters and setters method.
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(String tag) {
        tags.add(tag);
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(String line) {
        lines.add(line);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ArrayList<String> getReplies() {
        return replies;
    }

    public void setReplies(String reply) {
        replies.add(reply);
    }
}
