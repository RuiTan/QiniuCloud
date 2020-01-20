package top.guitoubing.qiniuweb.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import top.guitoubing.qiniuweb.domain.FileData;
import top.guitoubing.qiniuweb.domain.PublicationData;
import top.guitoubing.qiniuweb.util.ConstantUtil;
import top.guitoubing.qiniuweb.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class FileService {
    public static List<FileData> getFiles(){
        return getFilesWithPrefix("");
    }

    private static List<FileData> getFilesWithPrefix(String prefix) {
        BucketManager bucketManager = QiniuConfiguration.getBucketManager();
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(ConstantUtil.Bucket, prefix, limit, delimiter);
        ArrayList<FileData> fileInfos = new ArrayList<>();
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                fileInfos.add(new FileData(item.key, TimeUtil.stampToDate(String.valueOf(item.putTime/10000)), item.mimeType, String.valueOf(new Float(item.fsize / 1000)) + "KB", ConstantUtil.DomainName+item.key));
            }
        }
        return fileInfos;
    }

    public static List<FileData> getSARFiles(){
        return getFilesWithPrefix("publications/");
    }

    public static List<FileData> getFilesWithFilter(String filter){
        List<FileData> fileData = getFiles();
        fileData.removeIf(data -> fileNameFilter(data.getName(), filter));
        return fileData;
    }

    public static List<PublicationData> transferAllFilesToPublicaitonDataList(List<FileData> fileDataList){
        List<PublicationData> publicationDataList = new ArrayList<>();
        for (FileData fileData: fileDataList) {
            publicationDataList.add(transferFileToPublicationData(fileData));
        }
        return publicationDataList;
    }

    private static PublicationData transferFileToPublicationData(FileData fileData) {
        String name = fileData.getName();
        String url = fileData.getUrl();
        String[] details = name.split("|");
        return new PublicationData(details[1], details[0], details[2], details[3], url);
    }

    private static boolean fileNameFilter(String name, String filter){
        name = name.toLowerCase();
        filter = filter.toLowerCase();
        return !name.contains(filter);
    }

    public static void uploadFile(String path){
        String upToken = QiniuConfiguration.getUpToken();
        UploadManager uploadManager = QiniuConfiguration.getUploadManager();
        String key = path.split("/")[path.split("/").length - 1];
        try {
            Response response = uploadManager.put(path, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ignored) {
            }
        }
    }

    public static void  uploadBytes(byte[] bytes, String key) {
        String upToken = QiniuConfiguration.getUpToken();
        UploadManager uploadManager = QiniuConfiguration.getUploadManager();
        try{
            uploadManager.put(bytes, key, upToken);
        }catch (QiniuException ignored){
        }
    }
}
