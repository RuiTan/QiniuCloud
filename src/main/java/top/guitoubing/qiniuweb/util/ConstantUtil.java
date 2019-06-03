package top.guitoubing.qiniuweb.util;

public class ConstantUtil {
    public static final String AccessKey = "LIjKmW98gcj0ahR_Fogx3AV8V1JsRtOQo6qT43Cu";
    public static final String SecretKey = "W_lPo12yjHkaVyfibquVSJH8ImLWXw-tIXeggNSo";
    public static String Bucket = "archer";
    public static final String DomainName = "http://archer.guitoubing.top/";
    private static final String Archer = "archer";
    private static final String Guitoubing = "guitoubing";
    private static final String Editor = "editor";

    public static void setBucket(String bucket) {
        if (bucket.equals(Archer) || bucket.equals(Guitoubing) || bucket.equals(Editor))
            Bucket = bucket;
    }
}
