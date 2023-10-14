package com.keke.service;

import com.keke.entities.Connect;
import com.keke.mapper.RelationsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class RelationService {

    @Autowired
    private RelationsMapper relationsMapper;

    public int deleteRelation(Integer rid){
        return relationsMapper.deleteRelation(rid);
    }

    public List<Connect> selectAllRelations(String username,Integer mid){
        return relationsMapper.selectAllRelations(username,mid);
    }

    public int addRelation(String username, Integer mid, String mainfile, String refile, Integer fid){
        return relationsMapper.addRelation(username, mid, mainfile, refile, fid);
    }


}
