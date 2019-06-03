package top.guitoubing.qiniuweb.service;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import top.guitoubing.qiniuweb.util.ConstantUtil;

public class QiniuConfiguration {
    private static Configuration configuration;
    private static Auth auth;
    private static BucketManager bucketManager;
    private static String upToken;
    private static UploadManager uploadManager;

    public static UploadManager getUploadManager() {
        if (uploadManager == null)
            return new UploadManager(getConfiguration());
        return uploadManager;
    }

    public static String getUpToken() {
        if (upToken == null)
            return getAuth().uploadToken(ConstantUtil.Bucket);
        return upToken;
    }

    public static BucketManager getBucketManager() {
        if (bucketManager == null)
            return new BucketManager(getAuth(), getConfiguration());
        return bucketManager;
    }

    private static Auth getAuth() {
        if (auth == null){
            return Auth.create(ConstantUtil.AccessKey, ConstantUtil.SecretKey);
        }
        return auth;
    }

    private static Configuration getConfiguration() {
        if (configuration == null)
            return new Configuration(Zone.zone0());
        return configuration;
    }
}
