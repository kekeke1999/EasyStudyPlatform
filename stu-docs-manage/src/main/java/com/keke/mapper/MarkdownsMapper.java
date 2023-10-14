package com.keke.mapper;

import com.keke.entities.Markdowns;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkdownsMapper {

    int insertMarkdown(Markdowns markdown);

    List<Markdowns> selectAllMarkdowns(String username);

    int updateMarkdown(@Param("content") String content, @Param("mid") Integer mid);

}
