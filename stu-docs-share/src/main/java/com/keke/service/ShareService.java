package com.keke.service;

import com.keke.entities.Files;
import com.keke.entities.Share;
import com.keke.entities.ShareDetail;
import com.keke.mapper.ShareFileMapper;
import com.keke.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class ShareService {

    @Value("${keke.file.root.path}")
    private String fileRootPath;

    @Autowired
    private ShareFileMapper shareFileMapper;

    public String insertShare(Integer fid, String username){
        String sid = UUID.randomUUID().toString().replaceAll("-", "");
        String shareUrl = "http://localhost:8080/#/share?sid="+sid;
        shareFileMapper.insertShare(sid, fid, username, shareUrl);
        return shareUrl;
    }

    public List<ShareDetail> getShareDetail(String sid){
        ShareDetail shareDetail = shareFileMapper.getShareDetail(sid);
        LinkedList<ShareDetail> list = new LinkedList<>();
        list.add(shareDetail);
        return list;
    }

    public int saveShare(String sid,String username) throws Exception {
        System.out.println("sid//username:"+sid+","+username);
        Share share = shareFileMapper.getShare(sid);
        Integer fid = share.getFid();
        System.out.println("fid:"+fid);
        Files fileDetail = shareFileMapper.getFileDetail(fid);

        System.out.println(fileDetail.toString());
        System.out.println("ourl:"+fileDetail.getOriginalUrl());
        String originalUrl = fileDetail.getOriginalUrl().replace("http://localhost:9527/docs", fileRootPath);

        String filename = fileDetail.getFilename();

        File originalFile = new File(originalUrl);

        String toOFilePath = fileRootPath+"/"+username+"/保存的文件/"+fileDetail.getFilename();
        File toOFile = new File(toOFilePath);

        FileUtil.copy(originalFile,toOFile);

        if(filename.contains(".doc")||filename.contains("docx")||filename.contains("xls")||filename.contains("xlsx")||filename.contains("ppt")||filename.contains(".pptx")){
            String originalPdf = fileDetail.getPdfUrl().replace("http://localhost:9527/docs", fileRootPath);
            System.out.println("originalPdf:"+originalPdf);
            File originalPdfFile = new File(originalPdf);

            String toPFilePath = toOFilePath.substring(0, toOFilePath.lastIndexOf("."))+".pdf";
            System.out.println("toPfile:"+toPFilePath);


            File toPFile = new File(toPFilePath);

            FileUtil.copy(originalPdfFile,toPFile);

            String newOriginalUrl = "http://localhost:9527/docs/"+username+"/保存的文件/"+filename;
            System.out.println("newOriginalUrl:"+newOriginalUrl);

            String newPdfUrl = "http://localhost:9527/docs/"+username+"/保存的文件/"+filename.substring(0, filename.lastIndexOf("."))+".pdf";
            Files files = new Files(newOriginalUrl, newPdfUrl, filename, username, 1);

            shareFileMapper.insertFiles(files);
        } else{
            String newOriginalUrl = "http://localhost:9527/docs/"+username+"/保存的文件/"+filename;
            System.out.println("newOriginalUrl:"+newOriginalUrl);

            Files files = new Files(newOriginalUrl, newOriginalUrl, filename, username, 1);

            shareFileMapper.insertFiles(files);
        }

        return 1;

    }

    public Share getShare(String sid){
        return shareFileMapper.getShare(sid);
    }

    public int deleteShare(String sid){
        return shareFileMapper.deleteShare(sid);
    }

}
