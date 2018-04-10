package com.spring.rest.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.rest.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	private static final String namespace="mappers.ReplyMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void create(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+".create",vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+".update",vo);
	}

	@Override
	public void delete(int rno) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace+".delete",rno);
	}

	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".list",bno);
	}

}
