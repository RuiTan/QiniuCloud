package top.guitoubing.qiniuweb.main;

import top.guitoubing.qiniuweb.service.FileService;

public class Main {
    public static void main(String[] args){
        FileService.uploadFile("/Users/tanrui/Desktop/宝贝.jpg");
        FileService.getFiles();
    }
}
