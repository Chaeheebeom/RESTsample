<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#modDiv{
		width:280px;
		height:100px;
		background-color:gray;
		position:absolute;
		top:50%;
		left:50%;
		margin-top:-50px;
		margin-left:-150px;
		padding:10px;
		z-index:1000;
	}
	.pagination{
		sidth:100%;
	}
	.pagination li{
		list-style:none;
		float:left;
		padding:3px;
		border:1px solid blue;
		margin:3px;
	}
	.pagination li a{
		margin:3px;
		text-decoration:none;
	}
</style>
<title>AJAXTEST</title>
</head>
<body>
	<h3>Ajax 테스트 ㅎ</h3>
	<div>
		<div>
			REPLYER : <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLY TEXT : <input type="text"name="replytext" id="newReplyText">
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>
	<ul id="replies">
		
	</ul>
	<!-- 댓글페이지나누가 -->
	<ul class="pagination">
		
	</ul>
	
	<!-- Modal 창을 이용해 수정과 삭제 작업 진행 -->
	<div id="modDiv" style="display:none">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext">
		</div>
		<div>
			<button type="button" id="replyModBtn">modify</button>
			<button type="button" id="replyDelBtn">delete</button>
			<button type="button" id="closeBtn">close</button>
		</div>
	</div>
	
	
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script>
	var bno=40;
		//MOD버튼
			$("#replies").on("click",".replyLi button",function(){
			var reply=$(this).parent();
			var rno=reply.attr("data-rno");
			var replytext=reply.text();
				//alert(rno+" "+replytext);
			//modal창띄우기	
				$(".modal-title").html(rno);
				$("#replytext").val(replytext);
				$("#modDiv").show("slow");
		});
	//수정하기
	$("#replyModBtn").on("click",function(){
		var rno=$(".modal-title").html();
		var replytext=$("#replytext").val();
		$.ajax({
			type:'put',
			url:'/replies/'+rno,
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"PUT"
			},
			data:JSON.stringify({replytext:replytext}),
			dataType:'text',
			success:function(result){
				if(result=='SUCCESS')
					alert('수정되었습니다');
				$("#modDiv").hide("slow");
				//getAllList();
				getPageList(1);
			}
		});
	});
	//삭제하깋	
	$("#replyDelBtn").on("click",function(){
		var rno=$(".modal-title").html();
		$.ajax({
			type:'delete',
			url:'/replies/'+rno,
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-override":"DELETE"
			},
			dataType:'text',  
			success:function(result){
				if(result=='SUCCESS')
					alert('삭제되었습니다');
				$("#modDiv").hide("slow");
				//getAllList();
				getPageList(1);
			}

		});
		
	});
	
	//닫기
	$("#closeBtn").on("click",function(){
		var a=confirm('닫을꺼야??');
		if(a)
			$("#modDiv").hide();
	});
	var replyPage=1;
	$(".pagination").on("click","li a",function(event){
		event.preventDefault();//기존에 링크 걸어진 이벤트 막음
		replyPage=$(this).attr("href"); //href에 있는 값 가져옴(i값)
		getPageList(replyPage); //getpagelist호출
	});
	
	//replyAddBtn 클릭시 		
		$("#replyAddBtn").on("click",function(){
			//text필드값 가쟈ㅕ오기
			var replyer=$("#newReplyWriter").val();
			var replytext=$("#newReplyText").val();
			
			//가져온 값을 json형태로 만들어 보내기
			$.ajax({
				type:'post',
				url:'/replies',
				headers:{
					"Content-Type":"application/json",
					"X-HTTP-Method-Override":"POST"
				},
				dataType:'text',
				data:JSON.stringify({ //JSON형식으로 데이타바꾸는부ㅜㄴ
					bno:bno,
					replyer:replyer,
					replytext:replytext
				}),
				success:function(result){
					if(result=='SUCCESS'){
						alert("등록되었습니다.");
						//getAllList();
						getPageList(1);
					}
				}
			});
		});		
		/* function getAllList(){
			var str="";
			$.getJSON("/replies/all/"+bno,function(data){
					console.log(data.length);
			$(data).each(
				function(){
					str+="<li data-rno='"+this.rno+"' class='replyLi'>"
					+this.rno+":"+this.replytext
					+"<button>MOD</button>"
					+"</li>";	
				}); 
				$("#replies").html(str);
			});
		}			
 */	
 	function getPageList(page){
		 var str="";
		$.getJSON("/replies/"+bno+"/"+page,function(data){
				
		$(data.list).each(
			function(){
				str+="<li data-rno='"+this.rno+"' class='replyLi'>"
				+this.rno+":"+this.replytext
				+"<button>MOD</button>"
				+"</li>";	
			}); 
			$("#replies").html(str);
			printPaging(data.maker);
		});
 	}
 	function printPaging(maker){
	 	var str="";
 		if (maker.prev)
			str+="<li><a href="+(maker.startPage-1)+">prev</a><li>";
		
		for (var i=maker.startPage, len=maker.endPage; i<=len;i++){
			var value=maker.cri.page==i?'class=active':'';
			str+="<li "+value+"><a href="+i+">"+i+"</a></li>";
		}
		if(maker.next){
			str+="<li><a href="+(maker.startPage+1)+">next</a></li>";
	 	}
		$(".pagination").html(str);
 	}
 </script>
</body>
</html>