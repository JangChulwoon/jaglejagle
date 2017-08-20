<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
	int Pool = Integer.parseInt(request.getParameter("Pool"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="UTF-8">
<title>InputModify_Form</title>

<style type = "text/css">
*{
	margin:0px;padding:0px;
}
body{
	font-size:9pt;font-family:돋움;
}
td{
	font-size:9pt;
}
a{
	color:#000000;
	text-decorarion:none;
	cursor:pointer;
}
a:active, a:hover{
	text-decoration:underline;
	color:tomato;
}
.title{
	font-weight:bold;
	font-size:13pt;
	margin-bottom:10px;
	font-family:나눔고딕, 맑은고딕,굴림;
}
.boxTF{
	border:1px solid #666666; height:15px; margin-top:3px; padding:2px 2px 2px 2px;
	background-color:#ffffff; font-family:"굴림"; font-size:9px;
}
.boxTA{
	border:1px solid #666666; height:150px; margin-top:3px; padding:2px 2px 2px 2px;
	background-color:#ffffff; font-family:"굴림"; font-size:9px;
}
.btn{
	font-size:9pt; color:rbg(0,0,0); border:1px solid #AAAAAA; background-color:rbg(255,255,255); padding:0px 7px 3px 7px; font-family:나눔고딕, 맑은고딕, 굴림;
}
</style>
<script src="//code.jquery.com/jquery.min.js"></script>

<script type="text/javascript">
	//String.prototype.trim=function(){
	//	var TRIM_PATTERN = /(^\s*)|(\s*$)/g;
	//	return this.replace(TRIM_PATTERN, "");
	//};
	
	
	function sendBoard(){
		//alert("${Pool}");
		var f = document.boardForm;
		var form = $("#boardform");
 		var str = f.title.value;
		//str=str.trim();//좌우공백제거
		console.log($("#boarder-title").val());
		if( !$("#boarder-title").val() ){
			alert("\n제목을 입력하세요. ");
			f.title.focus();
			return false;
		}
		str = f.price.value;
		if(!str){
			alert("\n가격을 입력하세요. ");
			f.price.focus();
			return false;
		}
		str = f.contents.value;
		if(!str){
			alert("\n내용을 입력하세요. ");
			f.contents.focus();
			return false;
		}
		var mode= "${mode}";
		
		if(mode=="created"){
			f.action="<%=cp%>/bbs/created_ok.do";
		}
		else if(mode=="update")
			f.action="<%=cp%>/bbs/update_ok.do";
		f.submit();	 
	}
</script>
</head>
<body>
	<table style="width: 560px; margin: 0px auto:margin-top: 30px; border-spaoing: 0px;">
	<tr height="40">
		<td align="left" class="title">
			
		</td>
	</tr>
	</table>
	<br/>
	<form name="boardForm" method="post" enctype="multipart/form-data">
	
		<table style="width:560px; margin:0px auto: margin-top: 20px; boarder-spaoing:0px;">
		<tr><td colspan="2" height="3" bgcolor="#407CD1"></td></tr>
		<tr align="left" height="30">
			<td width="80" bgcolor="#EEEEEE" style="padding-left:20px;">제목</td>
			<td width="480" style="padding-left:10px;">
				<input type="text" name="title" id ="boarder-title" size="74" maxlength="100" class="boxTF" value="${dto.title}"/>
			</td>
		</tr>
		
		<tr><td colspan="2" height="1" bgcolor="DBDBDB"></td></tr>
		
			<tr align="left" height="30">
			<C:if test="<%=Pool%>==2">
				<td width="80" bgcolor="#EEEEEE" style="padding-left:20px;">가격</td>
				<td width="480" style="padding-left:10px;">
					<input type="text" name="price" size="74" maxlength="7" class="boxTF" value="${dto.price}" />
				</td>
			</C:if>
			</tr>
			
		<tr align="left" height="30">
			<td width="80" bgcolor="#EEEEEE" style="padding-left:20px;">내용</td>
			<td width="480" style="padding-left:10px;padding-bottom:5px;">
				 <textarea name="contents" cols="72" rows="12" class="boxTA">${dto.contents}</textarea>
				 </td>
			</tr>
			<tr><td colspan="2" height="1" bgcolor="#DBDB"></td></tr>
			
			<tr align="left" height="30">
				<td width="80" bgcolor="#EEEEEE" style="padding-left:20px;">이미지</br>(선택입력)</td>
				<td width="480" style="padding-left:10px;padding-bottom:5px;">
					
						<input type="file" name="filename1" id="filename1" size=43>
						<!-- <input type="submit" value="업로드">	-->
					
					
						<input type="file" name="filename2" id="filename2" size=43>
						<!-- <input type="submit" value="업로드">	-->
					
					
						<input type="file" name="filename3" id="filename3" size=43>
						<!-- <input type="submit" value="업로드">	-->
					
					
						<input type="file" name="filename4" id="filename4" size=43>
						<!-- <input type="submit" value="업로드">	-->
					

						<input type="file" name="filename5" id="filename5" size=43>
						<!-- <input type="submit" value="업로드">	-->
					
				</td>
			</tr>
			
			<tr><td colspan="2" height="3" bgcolor="#507CD1">
			</td></tr>
	</table>
	
	<table style="width: 560px; margin: 0px; spaoing:0px;">
		<tr height="40">
		<td align="center">
			<C:if test="${mode=='update'}">
				<input type="hidden" name="idx" value="${dto.idx}"/>
				<input type="hidden" name="pageNum" value="${pageNum}"/><!-- ************** -->
			</C:if>
			<C:if test="${mode=='created'}">
				<input type="hidden" name="Pool" value="${Pool}" />
			</C:if>
			<input type="button" value="${mode!='created'?'수정완료':'등록하기'}" class="btn" onclick="sendBoard();"/>
			<input type="button" value="${mode!='created'?'수정취소':'등록취소'}" class="btn" onclick="javascript:location.href='<%=cp%>/bbs/list.do?Pool=<%=Pool%>';"/>
		</td>
		</tr>
	</table>
	</form>
</body>
</html>