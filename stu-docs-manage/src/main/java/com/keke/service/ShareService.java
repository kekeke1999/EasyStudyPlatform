package com.keke.service;

import com.keke.entities.Share;
import com.keke.mapper.ShareFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ShareService {

    @Autowired
    private ShareFileMapper shareFileMapper;

    public String insertShare(Integer fid, String username){
        String sid = UUID.randomUUID().toString().replaceAll("-", "");
        String shareUrl = "http://localhost:8080/share?sid="+sid;
        shareFileMapper.insertShare(sid, fid, username, shareUrl);
        return shareUrl;
    }

    public Share getShare(String sid){
        return shareFileMapper.getShare(sid);
    }

    public int deleteShare(Integer sid){
        return shareFileMapper.deleteShare(sid);
    }

}
