package com.spring.rest.persistence;

import java.util.List;

import com.spring.rest.domain.ReplyVO;

public interface ReplyDAO {
	public void create(ReplyVO vo) throws Exception;
	public void update(ReplyVO vo) throws Exception;
	public void delete(int rno) throws Exception;
	public List<ReplyVO> list(int bno) throws Exception;
}
