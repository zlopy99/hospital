package Login;

public enum Option {
    Admin, Lijecnik;

    private Option() {}

    public String value(){
        return name();
    }

    public static Option fromValue(String v){
        return valueOf(v);
    }
}
