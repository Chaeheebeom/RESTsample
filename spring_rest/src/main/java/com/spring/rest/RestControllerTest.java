package com.spring.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.domain.PersonVO;

@RestController
public class RestControllerTest {
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	@RequestMapping("/sendVO") 
	public PersonVO sendVO() {
		PersonVO vo=new PersonVO();
		vo.setMno(12345);
		vo.setFirstName("doe");
		vo.setLastName("john");
		return vo; //이 객체데이터를 보낼려면 JSON을 써야함 jackson databind가 있으면 됨
	}
	@RequestMapping("/sendList") 
	public List<PersonVO> sendList() {
		List<PersonVO> list=new ArrayList<PersonVO>();
		for(int i=0;i<10;i++) {
			PersonVO vo=new PersonVO();
			vo.setMno(12345);
			vo.setFirstName("doe");
			vo.setLastName("john");
			list.add(vo);
		}
		return list; 
	}
}
