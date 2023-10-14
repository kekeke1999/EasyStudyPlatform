package com.keke.service;

import com.keke.ResponseResult;
import com.keke.converter.OpenOfficePDFConverter;
import com.keke.entities.Files;
import com.keke.entities.Markdowns;
import com.keke.entities.Paths;
import com.keke.entities.Pictures;
import com.keke.enums.ResponseCodeEnum;
import com.keke.mapper.FileUploadMapper;
import com.keke.mapper.MarkdownsMapper;
import com.keke.mapper.PicturesMapper;
import com.keke.utils.FileUtil;
import com.keke.utils.ToMp4ListenerTest;
import com.liumapp.workable.converter.exceptions.ConvertFailedException;
import org.apache.ibatis.annotations.Param;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

@Service
public class FileUploadService {

    @Value("${keke.file.root.path}")
    private String fileRootPath;

    private final static String[] VIDEO_TYPE = { "ASX", "ASF", "MPG", "WMV", "3GP", "MP4", "MOV", "AVI", "FLV", "asx", "asf", "mpg", "wmv", "3gp", "mp4","mov", "avi", "flv"}; // 可以处理的视频格式

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private PicturesMapper picturesMapper;

    @Autowired
    private MarkdownsMapper markdownsMapper;

    public int addClicks(Integer fid){
        return fileUploadMapper.addClicks(fid);
    }

    public List<Files> selectMostClicks(String username){
        List<Files> list = fileUploadMapper.selectMostClicks(username);
        List<Files> result = new LinkedList<>();
        for (Files f :list) {
            if(f.getClick()!=0){
                result.add(f);
            }
        }
        System.out.println("result:"+result);
        return result;
    }

    public int updateKeyword(String keyword, Integer fid){
        return fileUploadMapper.updateKeyword(keyword, fid);
    }

    public int deleteFolder(Integer pathId, String currentPath){
        return fileUploadMapper.deleteFolder(pathId, currentPath);
    }

    public int deleteFilesInFolder(Integer pathId, String currentPath){
        return fileUploadMapper.deleteFilesInFolder(pathId, currentPath);
    }

    public int deleteFile(Integer fid){
        return fileUploadMapper.deleteFile(fid);
    }

    public int fileRename(String filename,Integer fid,String currentPath){
        String oldName = fileUploadMapper.selectFilename(fid);
        File f = new File(fileRootPath+currentPath+"/"+oldName);
        f.renameTo(new File(fileRootPath+currentPath+"/"+filename));
        return fileUploadMapper.fileRename(oldName, filename, fid);
    }

    public int updateOriginalFile(String oldPath, String localPath, String username, Integer layer){
        return fileUploadMapper.updateOriginalFile(oldPath, localPath, username, layer);
    }

    public int updatePdfFile(String oldPath, String localPath, String username, Integer layer){
        return fileUploadMapper.updatePdfFile(oldPath, localPath, username, layer);
    }

    public List<Files> selectAllFiles(String username){
        List<Files> files = fileUploadMapper.selectAllFiles(username);
        System.out.println("files:"+files);
        return files;
    }

    public List<Paths> selectCurrentFolders(String beforePath, Integer layer ,String username){
        return fileUploadMapper.selectCurrentFolders(beforePath, layer, username);
    }

    public ResponseResult insertNewFolder(String beforePath, String localPath, Integer layer, String username, String newPath){
        File f = new File(fileRootPath+newPath);
        if(f.exists()){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "目录已存在！");
        }else{
            f.mkdir();
            if(fileUploadMapper.insertNewFolder(beforePath, localPath, layer, username)==1) {
                return ResponseResult.success();
            }
            else{
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "创建目录失败！");
            }
        }
    }

    public String selectFilename(Integer fid){
        return fileUploadMapper.selectFilename(fid);
    }

    public List<Files> selectHomeFiles(String username){
        List<Files> files = fileUploadMapper.selectHomeFiles(username);
        System.out.println("files:"+files);
        return files;
    }

    public List<Files> selectFiles(String username, String currentPath, Integer layer){
        List<Files> files = fileUploadMapper.selectFiles(username,currentPath, layer);
        System.out.println("files:"+files);
        return files;
    }

    public int updateFolder(Integer pathId, String localPath){
        return fileUploadMapper.updateFolder(pathId, localPath);
    }

    public int updateOthersFolder(String oldPath, String newPath, String username, Integer layer){
        return fileUploadMapper.updateOthersFolder(oldPath, newPath, username, layer);
    }

    public Paths selectPath(Integer pathId){
        return fileUploadMapper.selectPath(pathId);
    }

    public ResponseResult createNewPath(String newPath){
        File f = new File(newPath);
        if(f.exists()){
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "目录已存在！");
        }else{
            return ResponseResult.success();
        }
    }

    public ResponseResult fileUploadSingle(MultipartFile file, String username){
        String filePath = "";
        String newFilePath = "";
        String pdfUrl = "";
        int flag=0;
        // 多文件上传
            if (file.isEmpty()) {
                return null;
            }
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();

            // 存储路径
            filePath = new StringBuilder(fileRootPath)
                    .append("/")
                    .append(originalFilename)
                    .toString();
            System.out.println("filePath:"+filePath);
            File dest = new File(filePath);
            if (dest.exists()) {
                System.out.println("文件重复");
                String originalUrl = "http://localhost:9527/docs/"+originalFilename;
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }
                if(flag == 1){
                    pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";
                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx")){
                        pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";
                    }else{
                        pdfUrl = "http://localhost:9527/docs/"+originalFilename;
                    }
                }
                Files f = new Files(originalUrl,pdfUrl,filename,username);

                if(fileUploadMapper.insertIntoFiles(f)==1){
                    return ResponseResult.success();
                }else{
                    return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                }
            }

            try {
                // 保存文件
                file.transferTo(new File(filePath));
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                System.out.println("newFilePath:"+newFilePath);
                String originalUrl = "http://localhost:9527/docs/"+originalFilename;
                System.out.println("originalUrl:"+originalUrl);
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }

                if(flag == 1){
                    //将所有视频转化为mp4
                    ToMp4ListenerTest t = new ToMp4ListenerTest();
                    newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".mp4";
                    t.convertVideo(filePath, newFilePath);
                    pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";

                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx"))
                    {
                        //将Office文档转成pdf
                        newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".pdf";
                        Boolean s = new OpenOfficePDFConverter().convert(filePath,newFilePath);
                        if(!s){
                            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                        }
                        pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";


                    }else{
                        pdfUrl = originalUrl;
                    }
                }

                Files f = new Files(originalUrl,pdfUrl,originalFilename,username);

                fileUploadMapper.insertIntoFiles(f);


            } catch (IOException | OfficeException | ConvertFailedException e) {
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
            return ResponseResult.success();
    }

    public String picturesUpload(MultipartFile file) throws IOException {
        String pname = file.getOriginalFilename();
        String filePath = new StringBuilder(fileRootPath)
                .append("/pictures/")
                .append(pname)
                .toString();
        System.out.println("filePath:"+filePath);
        File dest = new File(filePath);
        if(dest.exists()) {
            return "http://localhost:9527/docs/pictures/"+pname;
        }
        file.transferTo(new File(filePath));
        String url = "http://localhost:9527/docs/pictures/"+pname;
        if(picturesMapper.insertPictures(url, pname)==1)
            return url;
        else
            return null;
    }

    public int saveMd(String content, String title, String username) throws UnsupportedEncodingException {
        String filePath = new StringBuilder(fileRootPath)
                .append("/")
                .append(username)
                .append("/markdowns/")
                .append(title)
                .append(".md")
                .toString();

        String result = java.net.URLDecoder.decode(content, "UTF-8");
        System.out.println("result:"+result);
        FileUtil.string2File(result,filePath);

        String url = "http://localhost:9527/docs/"+username+"/markdowns/"+title+".md";
        Files files = new Files(url,url,title+".md",username,1,0);

        Markdowns markdown = new Markdowns(content, title, username);
        if(fileUploadMapper.insertIntoFiles(files)==1 && markdownsMapper.insertMarkdown(markdown)==1){
            return 1;
        }else{
            return 0;
        }
    }

    public ResponseResult fileUploadSingle1(MultipartFile file){
        String filePath = "";
        String newFilePath = "";
        String pdfUrl = "";
        int flag=0;
        // 多文件上传
        if (file==null) {
            System.out.println("我为空");
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
        }
        // 上传简单文件名
        String originalFilename = file.getOriginalFilename();

        // 存储路径
        filePath = new StringBuilder(fileRootPath)
                .append("/")
                .append(originalFilename)
                .toString();
        System.out.println("filePath:"+filePath);
        File dest = new File(filePath);
        if (dest.exists()) {
            System.out.println("文件重复");
            String originalUrl = "http://localhost:9527/docs/"+originalFilename;
            String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            for (String str :
                    VIDEO_TYPE) {
                if (originalFilename.contains(str)) {
                    flag = 1;
                }
            }
            if(flag == 1){
                pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";
            }else{
                if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                        originalFilename.contains(".xls")||originalFilename.contains(".xlsx")){
                    pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";
                }else{
                    pdfUrl = "http://localhost:9527/docs/"+originalFilename;
                }
            }
            Files f = new Files(originalUrl,pdfUrl,filename);

            if(fileUploadMapper.insertIntoFiles(f)==1){
                return ResponseResult.success();
            }else{
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
        }

        try {
            // 保存文件
            file.transferTo(new File(filePath));
            String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            System.out.println("newFilePath:"+newFilePath);
            String originalUrl = "http://localhost:9527/docs/"+originalFilename;
            System.out.println("originalUrl:"+originalUrl);
            for (String str :
                    VIDEO_TYPE) {
                if (originalFilename.contains(str)) {
                    flag = 1;
                }
            }

            if(flag == 1){
                //将所有视频转化为mp4
                ToMp4ListenerTest t = new ToMp4ListenerTest();
                newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".mp4";
                t.convertVideo(filePath, newFilePath);
                pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";

            }else{
                if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                        originalFilename.contains(".xls")||originalFilename.contains(".xlsx"))
                {
                    //将Office文档转成pdf
                    newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".pdf";
                    Boolean s = new OpenOfficePDFConverter().convert(filePath,newFilePath);
                    if(!s){
                        return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                    }
                    pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";


                }else{
                    pdfUrl = originalUrl;
                }
            }

            Files f = new Files(originalUrl,pdfUrl,filename);

            fileUploadMapper.insertIntoFiles(f);


        } catch (IOException | OfficeException | ConvertFailedException e) {
            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
        }
        return ResponseResult.success();
    }

    public ResponseResult folderUpload(MultipartFile[] files, String username, String currentPath, Integer layer){
        System.out.println("我来服务里面了！");
        fileRootPath = "/Users/uu/Desktop/1";
        // 多文件上传
        System.out.println("files[0].getOriginalFilename()"+files[0].getOriginalFilename());
        for (MultipartFile file : files){
            String filePath = "";
            String newFilePath = "";
            String pdfUrl = "";
            int flag=0;

            if (file.isEmpty()) {
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            System.out.println("originalFilename:"+originalFilename);
            // 存储路径

            filePath = fileRootPath+currentPath+"/"+originalFilename;
            System.out.println("filePath:"+filePath);
            File dest = new File(filePath);
            if (dest.exists()) {
                System.out.println("文件重复");
                String originalUrl = "http://localhost:9527/docs"+currentPath+"/"+originalFilename;
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }
                if(flag == 1){
                    pdfUrl = "http://localhost:9527/docs"+currentPath+"/"+filename+".mp4";
                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx")){
                        pdfUrl = "http://localhost:9527/docs"+currentPath+"/"+filename+".pdf";
                    }else{
                        pdfUrl = "http://localhost:9527/docs"+currentPath+"/"+originalFilename;
                    }
                }
                Files f = new Files(originalUrl,pdfUrl,originalFilename,username,layer);

                if(fileUploadMapper.insertIntoFiles(f)==1){
                    continue;
                }else{
                    return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                }
            }
            try {
                // 保存文件
                file.transferTo(new File(filePath));
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                System.out.println("newFilePath:"+newFilePath);
                String originalUrl = "http://localhost:9527/docs"+currentPath+"/"+originalFilename;
                System.out.println("originalUrl:"+originalUrl);
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }

                if(flag == 1){
                    //将所有视频转化为mp4
                    ToMp4ListenerTest t = new ToMp4ListenerTest();
                    newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".mp4";
                    t.convertVideo(filePath, newFilePath);
                    pdfUrl = "http://localhost:9527/docs"+currentPath+"/"+filename+".mp4";

                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx"))
                    {
                        //将Office文档转成pdf
                        newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".pdf";
                        Boolean s = new OpenOfficePDFConverter().convert(filePath,newFilePath);
                        if(!s){
                            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                        }
                        pdfUrl = "http://localhost:9527/docs"+currentPath+"/"+filename+".pdf";


                    }else{
                        pdfUrl = originalUrl;
                    }
                }

                System.out.println("pdfUrl,ou,fn:"+pdfUrl+"//"+originalUrl+"//"+filename);

                Files f = new Files(originalUrl,pdfUrl,originalFilename,username,layer);

                if(fileUploadMapper.insertIntoFiles(f)==1){
                    continue;
                }
            } catch (IOException | OfficeException | ConvertFailedException e) {
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
        }
        return ResponseResult.success();
    }


    public ResponseResult fileUpload(MultipartFile[] files, String username){
        System.out.println("我来服务里面了！");
        fileRootPath = "/Users/uu/Desktop/1";
        // 多文件上传
        System.out.println("files[0].getOriginalFilename()"+files[0].getOriginalFilename());
        for (MultipartFile file : files){
            String filePath = "";
            String newFilePath = "";
            String pdfUrl = "";
            int flag=0;

            if (file.isEmpty()) {
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            System.out.println("originalFilename:"+originalFilename);
            // 存储路径

            filePath = fileRootPath+"/"+originalFilename;
            System.out.println("filePath:"+filePath);
            File dest = new File(filePath);
            if (dest.exists()) {
                System.out.println("文件重复");
                String originalUrl = "http://localhost:9527/docs/"+originalFilename;
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }
                if(flag == 1){
                    pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";
                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx")){
                        pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";
                    }else{
                        pdfUrl = "http://localhost:9527/docs/"+originalFilename;
                    }
                }
                Files f = new Files(originalUrl,pdfUrl,originalFilename,username);

                if(fileUploadMapper.insertIntoFiles(f)==1){
                    continue;
                }else{
                    return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                }
            }
            try {
                // 保存文件
                file.transferTo(new File(filePath));
                String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                System.out.println("newFilePath:"+newFilePath);
                String originalUrl = "http://localhost:9527/docs/"+originalFilename;
                System.out.println("originalUrl:"+originalUrl);
                for (String str :
                        VIDEO_TYPE) {
                    if (originalFilename.contains(str)) {
                        flag = 1;
                    }
                }

                if(flag == 1){
                    //将所有视频转化为mp4
                    ToMp4ListenerTest t = new ToMp4ListenerTest();
                    newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".mp4";
                    t.convertVideo(filePath, newFilePath);
                    pdfUrl = "http://localhost:9527/docs/"+filename+".mp4";

                }else{
                    if(originalFilename.contains(".doc")||originalFilename.contains(".docx")||originalFilename.contains(".ppt")||originalFilename.contains(".pptx")||
                            originalFilename.contains(".xls")||originalFilename.contains(".xlsx"))
                    {
                        //将Office文档转成pdf
                        newFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".pdf";
                        Boolean s = new OpenOfficePDFConverter().convert(filePath,newFilePath);
                        if(!s){
                            return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
                        }
                        pdfUrl = "http://localhost:9527/docs/"+filename+".pdf";


                    }else{
                        pdfUrl = originalUrl;
                    }
                }

                System.out.println("pdfUrl,ou,fn:"+pdfUrl+"//"+originalUrl+"//"+filename);

                Files f = new Files(originalUrl,pdfUrl,originalFilename,username);

                if(fileUploadMapper.insertIntoFiles(f)==1){
                    continue;
                }
            } catch (IOException | OfficeException | ConvertFailedException e) {
                return ResponseResult.error(ResponseCodeEnum.FAIL.getCode(), "文件上传失败！");
            }
        }
        return ResponseResult.success();
    }

}
