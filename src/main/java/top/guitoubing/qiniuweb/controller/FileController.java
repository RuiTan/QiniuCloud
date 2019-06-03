package top.guitoubing.qiniuweb.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.guitoubing.qiniuweb.domain.FileData;
import top.guitoubing.qiniuweb.service.FileService;
import top.guitoubing.qiniuweb.util.ConstantUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class FileController {

    @RequestMapping(value = "getFiles", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<FileData> getFiles(){
        return FileService.getFiles();
    }

    @RequestMapping(value = "getFilesWithFilter", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<FileData> getFilesWithFilter(@RequestParam String content){
        return FileService.getFilesWithFilter(content);
    }

    @RequestMapping(value = "uploadFiles", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public MultipartFile uploadFiles(@RequestParam("file") MultipartFile files) throws IOException {
        System.err.println(files);
        FileService.uploadBytes(files.getBytes(), files.getOriginalFilename());
        return files;
    }

    @RequestMapping(value = "changeBucket", method = RequestMethod.POST)
    public void changeBucket(@RequestParam("bucket") String bucket){
        ConstantUtil.setBucket(bucket);
    }

}
