package top.guitoubing.qiniuweb.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.guitoubing.qiniuweb.domain.FileData;
import top.guitoubing.qiniuweb.domain.PublicationData;
import top.guitoubing.qiniuweb.service.FileService;
import top.guitoubing.qiniuweb.util.ConstantUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    @RequestMapping(value = "getFiles", method = RequestMethod.GET)
    @ResponseBody
    public List<FileData> getFiles(){
        return FileService.getFiles();
    }

    @RequestMapping(value = "getSARFiles", method = RequestMethod.GET)
    @ResponseBody
    public List<PublicationData> getSARFiles(){
        return FileService.transferAllFilesToPublicationDataList(FileService.getSARFiles());
    }

    @RequestMapping(value = "getFilesWithFilter", method = RequestMethod.POST)
    @ResponseBody
    public List<FileData> getFilesWithFilter(@RequestParam String content){
        return FileService.getFilesWithFilter(content);
    }

    @RequestMapping(value = "getSARFilesWithFilter", method = RequestMethod.POST)
    @ResponseBody
    public List<PublicationData> getSARFilesWithFilter(@RequestParam String content) {
        return FileService.getSARFilesWithFilter(content);
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
