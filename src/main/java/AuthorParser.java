/**
 * Class for parsing the author Json file
 */
public class AuthorParser {

    private String author_name;
    private String author_email;
    private String author_url;

    public void setName(String newName) {
        this.author_name = newName;
    }

    public void setEmail(String newEmail) {
        this.author_email = newEmail;
    }

    public void setUrl(String newUrl) {
        this.author_url = newUrl;
    }

    public String getName() {
        return author_name;
    }

    public String getEmail() {
        return author_email;
    }

    public String getUrl() {
        return author_url;
    }

}
