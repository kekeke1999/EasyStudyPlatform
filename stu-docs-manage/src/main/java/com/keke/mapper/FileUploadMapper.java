package com.keke.mapper;

import com.keke.entities.Files;
import com.keke.entities.Paths;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface FileUploadMapper {

    List<Files> selectMostClicks(String username);

    int updateKeyword(@Param("keyword") String keyword, @Param("fid") Integer fid);

    int addClicks(Integer fid);

    int deleteFolder(@Param("pathId") Integer pathId, @Param("currentPath") String currentPath);

    int deleteFile(@Param("fid")Integer fid);

    int deleteFilesInFolder(@Param("pathId") Integer pathId, @Param("currentPath") String currentPath);

    int fileRename(@Param("oldName")String oldName, @Param("newName")String newName,@Param("fid")Integer fid);

    String selectFilename(@Param("fid")Integer fid);

    int updateOthersFolder(@Param("oldPath") String oldPath, @Param("newPath") String newPath, @Param("username") String username, @Param("layer") Integer layer);

    int updateOriginalFile(@Param("oldPath") String oldPath, @Param("newPath") String newPath, @Param("username") String username, @Param("layer") Integer layer);

    int updatePdfFile(@Param("oldPath") String oldPath, @Param("newPath") String newPath, @Param("username") String username, @Param("layer") Integer layer);

    int updateFolder(@Param("pathId") Integer pathId, @Param("localPath") String localPath);

    int insertIntoFiles(Files file);

    int insertNewFolder(@Param("beforePath") String beforePath, @Param("localPath") String localPath, @Param("layer") Integer layer, @Param("username") String username);

    Paths selectPath(@Param("pathId") Integer pathId);

    List<Files> selectAllFiles(String username);

    List<Files> selectHomeFiles(String username);

    List<Files> selectFiles(@Param("username") String username, @Param("currentPath") String currentPath, @Param("layer") Integer layer);

    List<Paths> selectCurrentFolders(@Param("beforePath") String beforePath, @Param("layer") Integer layer, @Param("username") String username);

}
