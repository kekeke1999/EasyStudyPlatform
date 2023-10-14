package com.keke.controller;

import com.keke.ResponseResult;
import com.keke.entities.Connect;
import com.keke.entities.Files;
import com.keke.entities.Paths;
import com.keke.enums.ResponseCodeEnum;
import com.keke.service.FileUploadService;
import com.keke.service.RelationService;
import com.keke.utils.DeleteUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.IntStream;


@RestController
public class UploadController {

    @Value("${keke.file.root.path}")
    private String fileRootPath;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RelationService relationService;

    @RequestMapping("/file/allOptions")
    public ResponseResult allOptionsFiles(@RequestHeader("username") String username){
        //   HashMap<String,Object> map = new LinkedHashMap<>();
        List<HashMap> list = new LinkedList<>();
        List<Files> files = fileUploadService.selectAllFiles(username);
        for (Files f :
                files) {
            HashMap<String,Object> map = new LinkedHashMap<>();
            map.put("value",f.getFid());
            map.put("label",f.getFilename());
            list.add(map);
        }
        return ResponseResult.success(list);
    }

    //@RequestParam("mainfile") String mainfile,,  @RequestParam("ids") List<Integer> ids
    @PostMapping("/file/addRelations")
    public ResponseResult addRelation(@RequestHeader("username") String username,@RequestParam("mid") Integer mid,  @RequestParam("id") Integer id,@RequestParam("mainfile") String mainfile){
        System.out.println("访问到了！");
        relationService.addRelation(username,mid,mainfile,fileUploadService.selectFilename(id),id);
    /*    for (Integer id :
                ids) {
            relationService.addRelation(username,mid,"000",fileUploadService.selectFilename(id),id);
        }*/
        return ResponseResult.success();
    }

    @PostMapping("/file/deleteRelation")
    public ResponseResult deleteRelation(@RequestParam("rid") Integer rid){
        if(relationService.deleteRelation(rid)==1){
            return ResponseResult.success();
        }else{
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除失败！");
        }
    }

    @GetMapping("/file/allRelations")
    public ResponseResult selectAllRelations(@RequestHeader("username") String username, @RequestParam("mid") Integer mid){
        System.out.println("mid:"+mid);
        List<Connect> connects = relationService.selectAllRelations(username, mid);
        List<Connect> list = new LinkedList<>();
        for (Connect c :
                connects) {
            String str = c.getOriginal_url();
            String str1 = str.replace("http://localhost:9527/docs","");
            c.setOriginal_url(str1.replace("/"+c.getRefile(),""));
            System.out.println("str1:"+str1);
            System.out.println("ori:"+c.getOriginal_url());
            list.add(c);
        }
        if (connects==null) {
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "空");
        }
        else {
            return ResponseResult.success(list);
        }
    }

    @GetMapping("/file/username")
    public String getUsername(@RequestHeader("username") String username){
        return username;
    }

    @PostMapping("/file/newFolder")
    public ResponseResult createNewPath(@RequestParam("beforePath") String beforePath, @RequestParam("localPath") String localPath, @RequestHeader("username") String username){
        String newPath = beforePath+"/"+localPath;
        int layer = newPath.split("/").length - 3;
        return fileUploadService.insertNewFolder(beforePath,localPath,layer, username,newPath);
    }

    @PostMapping("/file/upload")
    public ResponseResult upload(@RequestBody MultipartFile[] files, @RequestHeader("username") String username){
        System.out.println(files.length);
        if(files==null) {
            System.out.println("文件为空！");
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件为空！");
        }

        System.out.println("我收到东西了！");
        System.out.println("files[0].getOriginalFilename()"+files[0].getOriginalFilename());
        return fileUploadService.fileUpload(files, username);
    }

    @PostMapping("/file/uploadByFolder")
    public ResponseResult uploadIntoFolder(@RequestParam("currentPath") String currentPath, @RequestParam("layer") Integer layer,@RequestParam("files") MultipartFile[] files, @RequestHeader("username") String username){
        System.out.println(files.length);
        System.out.println("currentPath:"+currentPath);
        System.out.println("layer:"+layer);
        if(files==null) {
            System.out.println("文件为空！");
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件为空！");
        }

        System.out.println("我收到东西了！");
        System.out.println("files[0].getOriginalFilename()"+files[0].getOriginalFilename());
        return fileUploadService.folderUpload(files, username,currentPath,layer);
    }

    @PostMapping("/file/fileRename")
    public ResponseResult fileRename(@RequestParam("fid") Integer fid, @RequestParam("filename") String filename, @RequestParam("currentPath") String currentPath){
        if(fileUploadService.fileRename(filename, fid, currentPath)==0){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件重命名失败！");
        }else{
            return ResponseResult.success("重命名成功！");
        }

    }

    @PostMapping("/file/picturesUpload")
    public String picturesUpload(MultipartFile file) throws IOException {
        return fileUploadService.picturesUpload(file);
    }

    @PostMapping("/file/saveMd")
    public ResponseResult saveMd(@RequestParam("content") String content, @RequestParam("title") String title, @RequestHeader("username") String username) throws UnsupportedEncodingException {
        if(fileUploadService.saveMd(content, title, username)==1){
            return ResponseResult.success();
        }else {
            return ResponseResult.error();
        }
    }

    @RequestMapping("/file/deleteFile")
    public ResponseResult deleteFile(@RequestParam("fid") Integer fid,@RequestParam("filename") String filename,  @RequestParam("currentPath") String currentPath){
        File f = new File(fileRootPath+currentPath+"/"+filename);
        System.out.println("文件目录："+fileRootPath+currentPath+"/"+filename);
        if(f.exists()&& f.isFile()){

            if (fileUploadService.deleteFile(fid)==1 && f.delete()){
                return ResponseResult.success();
            }else{
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除文件失败！");
            }
        }else{
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除文件失败！");
        }
    }

    @RequestMapping("/file/deleteFolder")
    public ResponseResult deleteFolder(@RequestParam("pathId") Integer pathId,@RequestParam("filename") String filename,  @RequestParam("currentPath") String currentPath){
        File f = new File(fileRootPath+currentPath+"/"+filename);
        System.out.println("文件目录："+fileRootPath+currentPath+"/"+filename);
        currentPath = currentPath+"/"+filename;
        System.out.println("当前目录："+currentPath);
        if(f.exists()){
            if (DeleteUtils.deleteDir(f)){
                int a[] = {1,2,3,5,4};
                String s = "asnnn";
                char[] chars = s.toCharArray();
                s.length();
                List<Integer> l = new LinkedList<>();
                chars.toString();
                fileUploadService.deleteFolder(pathId,currentPath);
                fileUploadService.deleteFilesInFolder(pathId, currentPath);
                return ResponseResult.success();
            }else{
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除文件夹失败！");
            }
        }else{
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "删除文件夹失败！");
        }
    }


    @PostMapping("/file/uploadSingle")
    public ResponseResult uploadSingle(@RequestBody MultipartFile file, @RequestHeader("username") String username){
        return fileUploadService.fileUploadSingle(file, username);
    }

    @PostMapping("/file/uploadSingle1")
    public ResponseResult uploadSingle(@RequestBody MultipartFile file){
        return fileUploadService.fileUploadSingle1(file);
    }

    @GetMapping("/file/askFiles")
    public ResponseResult askFiles(@RequestHeader("username") String username, @RequestParam("currentPath") String currentPath, @RequestParam("layer") Integer layer ){
        System.out.println("username:"+username);
        List<Files> files = fileUploadService.selectFiles(username, currentPath, layer);

        List<Map<String, Object>> list = new LinkedList<>();


        for (Files f :
                files) {
            Map<String, Object> map = new HashMap<>();
            map.put("fileName",f.getFilename());
            System.out.println(f.getFilename().split("\\.")[1]);
            map.put("type",f.getFilename().split("\\.")[1]);
            map.put("createTime",f.getCreateTime());
            map.put("keyword",f.getKeyword());
            map.put("pdfUrl", f.getPdfUrl());
            map.put("originalUrl",f.getOriginalUrl());
            map.put("fid",f.getFid());
            map.put("pathId","-");
            map.put("layer", f.getLayer());
            list.add(map);
        }
        System.out.println("files:"+files);



        List<Paths> paths = fileUploadService.selectCurrentFolders(currentPath, layer, username);
        for (Paths p :
                paths) {
            if(p.getPathId()==null || p.getPathId().equals("") ){
                break;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("fileName",p.getLocalPath());
            map.put("type","folder");
            map.put("createTime",p.getCreateTime());
            map.put("keyword","-");
            map.put("pdfUrl", "-");
            map.put("originalUrl","-");
            map.put("fid","-");
            map.put("pathId",p.getPathId());
            map.put("layer", p.getLayer());
            list.add(map);
        }
        System.out.println("List:"+list);
        return ResponseResult.success(list);
    }

    @PostMapping("/file/updateFolder")
    public ResponseResult updateFolder(@RequestHeader("username") String username,@RequestParam("layer") Integer layer, @RequestParam("pathId") Integer pathId, @RequestParam("localPath") String localPath){

        Paths p = fileUploadService.selectPath(pathId);

        System.out.println("layer:"+layer);

        String oldPath = p.getBeforePath()+"/"+p.getLocalPath();
        System.out.println("oldPath:"+oldPath);

        String newPath = p.getBeforePath()+"/"+localPath;
        System.out.println("newPath:"+newPath);

        String newLocalPath = fileRootPath+newPath;
        System.out.println("newLocalPath:"+newLocalPath);

        String oldLocalPath = fileRootPath+oldPath;
        System.out.println("oldLocalPath:"+oldLocalPath);

        File f = new File(oldLocalPath);
        f.renameTo(new File(newLocalPath));

        fileUploadService.updateFolder(pathId, localPath);

        fileUploadService.updateOthersFolder(oldPath,newPath,username,layer);

        fileUploadService.updateOriginalFile(oldPath,newPath,username,layer);

        fileUploadService.updatePdfFile(oldPath,newPath,username,layer);

        return ResponseResult.success(ResponseCodeEnum.SUCCESS);

    /*    if(fileUploadService.updateOriginalFile(oldPath,newPath,username,layer) == 0){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "重命名文件夹失败！");
        }else{
            if(fileUploadService.updatePdfFile(oldPath,newPath,username,layer) == 0){
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "重命名文件夹失败！");
            }else{
                return ResponseResult.success(ResponseCodeEnum.SUCCESS);
            }
        }*/
    }

    @GetMapping("/file/homeFiles")
    public ResponseResult homeFiles(@RequestHeader("username") String username){
        System.out.println("username:"+username);
        List<Files> files = fileUploadService.selectHomeFiles(username);

        List<Map<String, Object>> list = new LinkedList<>();


        for (Files f :
                files) {
            Map<String, Object> map = new HashMap<>();
            map.put("fileName",f.getFilename());
            System.out.println(f.getFilename().split("\\.")[1]);
            map.put("type",f.getFilename().split("\\.")[1]);
            map.put("createTime",f.getCreateTime());
            map.put("keyword",f.getKeyword());
            map.put("pdfUrl", f.getPdfUrl());
            map.put("originalUrl",f.getOriginalUrl());
            map.put("fid",f.getFid());
            map.put("pathId","-");
            map.put("layer", f.getLayer());
            list.add(map);
        }
        System.out.println("files:"+files);



        List<Paths> paths = fileUploadService.selectCurrentFolders(new String("/"+username), 0, username);
        for (Paths p :
                paths) {
            if(p.getPathId()==null || p.getPathId().equals("") ){
                break;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("fileName",p.getLocalPath());
            map.put("type","folder");
            map.put("createTime",p.getCreateTime());
            map.put("keyword","-");
            map.put("pdfUrl", "-");
            map.put("originalUrl","-");
            map.put("fid","-");
            map.put("pathId",p.getPathId());
            map.put("layer", p.getLayer());
            list.add(map);
        }
        System.out.println("List:"+list);

        if(files.isEmpty()){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "无文件！");
        }

        return ResponseResult.success(list);
    }

    @GetMapping("/file/mostClicks")
    public ResponseResult selectMostClicks(@RequestHeader("username") String username){

        return ResponseResult.success(fileUploadService.selectMostClicks(username));
    }

    @PostMapping("/file/addClicks")
    public ResponseResult addClick(@RequestParam("fid") Integer fid){
        if(fileUploadService.addClicks(fid)==1){
            return ResponseResult.success();
        }else{
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "失败");
        }
    }

    @PostMapping("/file/updateKeyword")
    public ResponseResult updateKeyword(@RequestParam("keyword") String keyword, @RequestParam("fid") Integer fid){
        if(fileUploadService.updateKeyword(keyword, fid)==1){
            return ResponseResult.success();
        }else{
            return ResponseResult.error();
        }
    }
    @GetMapping("/file/allFiles")
    public ResponseResult allFiles(@RequestHeader("username") String username){
        System.out.println("username:"+username);
        List<Files> files = fileUploadService.selectAllFiles(username);
        System.out.println("files:"+files);
        if(files.isEmpty()){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "无文件！");
        }
        return ResponseResult.success(files);
    }

    public ResponseResult selectCurrentFolders(@RequestParam("beforePath") String beforePath, @RequestParam("layer") Integer layer, @RequestHeader("username") String username){
        List<Paths> paths = fileUploadService.selectCurrentFolders(beforePath, layer, username);
        return null;
    }



}
