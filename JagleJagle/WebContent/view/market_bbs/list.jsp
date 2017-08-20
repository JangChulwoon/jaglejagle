<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="fmt" uri ="http://java.sun.com/jsp/jstl/core" %>
<% 
	String cp = request.getContextPath();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>

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

</head>
<body>
<table style="width: 700px; margin: 0px auto:margin-top: 30px; border-spaoing: 0px;">
	<tr height="40">
		<td align="left" class="title">
		</td>
	</tr>
	</table>
	<br/>


<table style="width:700px; margin:0px auto; margin-top:20px; border-spacing:0px;">
	<tr height="20">
		<td align="center" width="70%">
			<form name="searchForm" action="" method="post">
				<select name="searchKey" class="selectField">
					<option value="title">제목</option>
					<option value="name">작성자</option>
					<option value="contents">내용</option>
				</select>
				<input type="text" id="searchValue" name="searchValue" class="boxTF" />
				<input type="button" value="검색" class="btn" onclick="searchList()"/>
			</form>
		</td>
	</tr>
</table>

<table style="width:450px; margin:0px auto; margin-top:20px; border-spacing:0px;">
	<tr align="center" bgcolor="#507CD1" height="25">
		<td width="2000" style="color:#ffffff;">주간 인기글</td>
	</tr>
	<c:forEach var="dto" items="${popList}" >
	<tr align="center" bgcolor="#ffffff" height="25">
		<td align="center" width="60" style="color:#000000">
			<input type="button" value=1 name="1st" class="boxTF"/>  
			 	<a href="${articleUrl}?num=${dto.idx}">${dto.title}</a>
		</td>
	</tr>
		</c:forEach>
</table>


<table style="width:700px; margin:0px auto; margin-top:20px; border-spacing:0px;">
	<tr height="30">
		<td align="left" width="70%">
		<form name="searchForm" action="" method="post">
				<select name="searchKey" class="selectField" id = "sorting-ID">
					<option value="" selected="selected">정렬</option>
					<option value="recommand" >추천순</option>
					<option value="registration_date" >일자순</option>
					<option value="view_count">조회수순</option>
				</select>
			</form>
		<td align ="right">
			<form name="createdForm" action="" method="post">
				<input type="hidden" id="Pool" name="Pool" value="${currentPool}" />
				<input type="button" value="글올리기" class="btn" onclick="createdF()"/>
			</form>
		</td>
	</tr>	
</table>
<table style="width:700px; margin:0px auto; border-spacing:0px;">
	<tr align="center" bgcolor="#507CD1" height="25">
		<td width="80" style="color:#ffffff;">일시</td>
		<td width="400" style="color:#ffffff;">제목</td>
		<td width="100" style="color:#ffffff;">현재가격</td>
		<td width="80" style="color:#ffffff;">작성자</td>
		<td width="60" style="color:#ffffff;">추천</td>
		<td width="60" style="color:#ffffff;">조회</td>
	</tr>
	
	<c:forEach var="dto" items="${list}">
		<tr align="center" bgcolor="#ffffff" height="25">
			<td align="center">${dto.registration_date}</td>
			<td align="center" style="padding-left:10px;">
				<a href="${articleUrl}&idx=${dto.idx}">${dto.title}</a>
			
			</td>
			<td align="center">${dto.price}</td> 
			<td align="center">${dto.nickname}</td>
			<td align="center">${dto.recommand}</td>
			<td align="center">${dto.view_count}</td>
			
		</tr>
		<tr><td height="1" colspan="5" bgcolor="#e4e4e4"></td></tr>
	</c:forEach>
</table>
<table style="width:700px; margin:0px auto; border-spacing:0px;">
	<tr height="30">
		<td align="center">
		<c:if test="${dataCount==0 }">
			등록된 게시물이 없습니다.
		</c:if>
		<c:if test="${dataCount!=0 }">
			${pageIndexList}
		</c:if>
		</td>
	</tr>
</table>

<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	function searchList(){
		var f = document.searchForm;
		f.action="<%=cp%>/bbs/list.do";
		f.submit();
	}
	function createdF(){
		var f = document.createdForm;
		f.action="<%=cp%>/bbs/created.do";
		f.submit();
	}
	
	$(document).ready( function(){ 
		var event = function changValue(){
			var kind = $(this).prop("value");
			location.href = '/JagleJagle/bbs/list.do?Pool='+${currentPool}+'&sort='+kind;
		};
		$("#sorting-ID").on("change",event);
	});
</script>

</body>
</html>