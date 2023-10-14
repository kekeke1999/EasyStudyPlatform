package com.keke.service;

import com.keke.entities.Markdowns;
import com.keke.mapper.MarkdownsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Repository
public class MarkdownService {

    @Autowired
    private MarkdownsMapper markdownsMapper;

    public List<Markdowns> getAllMarkdowns(String username){

        return markdownsMapper.selectAllMarkdowns(username);

    }


}
