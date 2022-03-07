public class CourierDataPattern {

    //Шаблон для передачи данных курьера в виде JSON
    private String login;
    private String password;
    private String firstName;

    public CourierDataPattern() {
    }

    public CourierDataPattern(String login) {
        this.login = login;
    }

    public CourierDataPattern(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierDataPattern(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
