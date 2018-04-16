package com.spring.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.domain.Criteria;
import com.spring.rest.domain.PageMaker;
import com.spring.rest.domain.ReplyVO;
import com.spring.rest.service.ReplyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/replies")
public class ReplyController {

	@Inject
	private ReplyService service;
	
	@RequestMapping(path="",method=RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		try {
			service.create(vo);
			entity=new ResponseEntity<>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	@RequestMapping(path="/all/{bno}",method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> List(@PathVariable("bno")int bno){
		ResponseEntity<List<ReplyVO>> entity=null;
		try {
			List<ReplyVO> list=service.list(bno);
			entity=new ResponseEntity<>(list,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//수정하기 ㅎ
	@RequestMapping(path="/{rno}",method=RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno")int rno,@RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		try {
			vo.setRno(rno);
			service.update(vo);
			entity=new ResponseEntity<>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//삭제하기
		@RequestMapping(path="/{rno}",method=RequestMethod.DELETE)
		public ResponseEntity<String> delete(@PathVariable("rno")int rno){
			ResponseEntity<String> entity=null;
			try {
				service.delete(rno);
				entity=new ResponseEntity<>("SUCCESS",HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				entity=new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		@RequestMapping(path="/{bno}/{page}",method=RequestMethod.GET)
		public ResponseEntity<Map<String,Object>> listPage(@PathVariable("bno")int bno
				,@PathVariable("page")int page){
			ResponseEntity<Map<String,Object>> entity=null;
			try {
				Criteria cri=new Criteria();
				cri.setPage(page);
				PageMaker maker=new PageMaker();
				maker.setCri(cri);
				Map<String,Object> map=new HashMap<>();
				
				List<ReplyVO> list=service.listReplyPage(bno, cri);
				map.put("list", list);
				
				int replyCount=service.count(bno);
				
				maker.setTotalCount(replyCount);
				map.put("maker",maker);
				
				entity=new ResponseEntity<>(map,HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				entity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		
}
