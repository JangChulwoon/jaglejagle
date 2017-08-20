<%@page import="dbconfig.DBInit"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();

%>
<%@ page import="java.util.*, java.sql.*, java.io.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>VIEW CONTENT</title>
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
.btn{
	font-size : 9pt; color:rbg(0,0,0; border:1px solid #AAAAAA; background-color:rbg(255,255,255); padding:0px 7px 3px 7px; font-fmaily : 나눔고딕, 맑은고딕, 굴림;
}
</style>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	function deleteBoard(num){
		if(confirm("위 자료룰 삭제하시겠습니까?")){
			var url = "<%= cp%>/bbs/delete.do?Pool="+${Pool}+"&pageNum="+num+"&idx=${dto.idx}";
			location.href = url;
		}
	}
	function updateRecommand(num){
			var url = "<%= cp%>/bbs/recommand.do?Pool="+${Pool}+"&pageNum="+num+"&idx=${dto.idx}";
			location.href = url;
	}
	function sendReply(){
		var f = document.repleForm;
		 if( !$("#replecontent").val() ){
			alert("\n내용을 입력하세요. ");
			f.replecontent.focus();
			return false;
		}
 		f.action="<%=cp%>/bbs/reple_ok.do";		
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

<table style="width:560px; margin:0px auto; margin-top:20px; border-spacing:0px;">
	<tr><td colspan = "4" height = "3" bgcolor = "#507CD1"></td></tr>
	
	<tr height = "35">
		<td colspan = "4" align = "center">
		</td>
	</tr>
	<tr><td colspan = "4" height = "1" bgcolor = "#507CD1"></td></tr>
	<tr height = "30">
		<td width = "60" bgcolor = "#EEEEEE" align = "center">제목</td>
		<td width = "220" allign = "left" style = "padding-left:10px;">
			${dto.title}
		</td>
		<td width = "60" bgcolor = "#EEEEEE" align = "center">작성자</td>
		<td width = "220" allign = "left" style = "padding-left:10px;">
			${dto.nickname}
		</td>
	</tr>
	<tr><td colspan = "4" height = "1" bgcolor = "#DBDBDB"></td></tr>
	
	
	<tr height = "30">
		<td width = "60" bgcolor = "#EEEEEE" align = "center">작성일</td>
		<td width = "220" allign = "left" style = "padding-left:10px;">
			${dto.registration_date}
		</td>
		
		<td width = "60" bgcolor = "#EEEEEE" align = "center">추천수</td>
		<td width = "220" allign = "left" style = "padding-left:10px;">
			${dto.recommand} 
		</td>
		
		<td width = "60" bgcolor = "#EEEEEE" align = "center">조회수</td>
		<td width = "220" allign = "left" style = "padding-left:10px;">
			${dto.view_count} 
		</td>
	</tr>
	
	
	<tr><td colspan = "4" height = "1" bgcolor ="#507CD1"></td></tr>
	
	<tr>
		<td colspan = "4" align = "left" style = "padding: 20px 80px 20px 62px;" valign = "top" height = "200">
			${dto.contents}
			</br>
		</td>
	</tr>
	<tr>
		<td>
			<img src="<%=cp%>${Idto.path}" width = "200" hight="160">
		</td>
		
	
	</tr>
	<tr><td width="60"></td><td width="100"></td><td width="60"></td>
		<td width = "100" align = "right">
			<input type="button" name="rec" value="추천" class="btn" onclick="updateRecommand('${pageNum}');">
		</td>
	<tr><td colspan = "4" height = "1" bgcolor ="#507CD1"></td></tr>

		<tr>
			<td colspan="4" height="3" bgcolor="#507CD1" align="center"></td>
		</tr>
		<tr height="30">
		</tr>
	</table>	
	<table style="width: 560px; margin: 0px auto; border-spacing: 0px;">
		<tr height="30">
			<td width="50%" align="left"><input type="button" value=" 수정 "
				class="btn"
				onclick="javascript:location.href='<%=cp%>/bbs/update.do?Pool=${Pool }&pageNum=${pageNum}&idx=${dto.idx}';" />
				<input type="button" value=" 삭제 " class="btn"
				onclick="deleteBoard('${pageNum}');" /></td>
			<td align="right"><input type="button" value=" 리스트 " class="btn"
				onclick="javascript:location.href='<%=cp%>/bbs/list.do?Pool=${Pool}&${params}';" />
			</td>
		</tr>
	</table>
	<!-- 댓글 -->
	<form name="repleForm" id="repleForm" method="post" >
    <input type="hidden" id="b_idx" name="b_idx" value="${dto.idx}" />
    <input type="hidden" id="PageNum" name="PageNum" value="${pageNum}" />
    <input type="hidden" id="Pool" name="Pool" value="${Pool}" />
	<table style="width: 600px; margin: 0px auto; margin-top: 5px;">
		<tr height="50" align = "center">
				 <!-- <textarea rows="5" cols="84" class="boxTF" name="contents" style="height: 45px;">${Rdto.contents}</textarea>-->
			   <td width="480" style="padding-left:10px;padding-bottom:5px;">
				 <textarea name="replecontent" id ="replecontent" cols="72" rows="12" class="boxTA">${Rdto.contents}</textarea>
				 </td>
		
			<td width="80" align="right">
			   <input type="button" value="등록" class ="btn"
			          style="width: 60px; height: 50px;background: #FFFFFF; border: 1px solid #cccccc" onclick="sendReply();"/> 
			</td>
		 </tr>           
        </table>
	</form>	
	</br>
	<table style="width:560px; align:center; margin:0px auto: margin-top: 20px; boarder-spaoing:0px;">
		<tr align="center" bgcolor="#ffffff" height="25"><td align="center">▶댓글◀</td></tr>
		<tr align="center" bgcolor="#ffffff" height="25">
			<td align="center">작성 일자</td>
			<td align="center">댓글</td>
			<td align="center">작성자</td>
		</tr>
		<tr><td colspan = "4" height = "1" bgcolor ="#507CD1"></td></tr>
	<c:forEach var="Rdto" items="${list}">
		<tr align="center" bgcolor="#ffffff" height="25">
			<td align="center">${Rdto.registration_date}</td>
			<td align="left">${Rdto.contents}</td>
			<td align="center">${Rdto.nickname}</td>
			
			
			
		</tr>
		<tr><td height="1" colspan="5" ></td></tr>
	</c:forEach>
	</table>

</body>
</html>