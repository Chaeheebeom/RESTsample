package com.spring.rest;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.domain.ReplyVO;
import com.spring.rest.service.ReplyService;
import java.util.List;

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
	//수정하기 ㅎ
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
}
