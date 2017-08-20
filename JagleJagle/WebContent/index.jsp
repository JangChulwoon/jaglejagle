<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%String cp = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

	<a href="<%=cp %>/bbs/list.do?Pool=2">벼룩시장 게시판</a></br>
	<a href="<%=cp %>/bbs/list.do?Pool=3">자유 게시판</a></br>
	<a href="<%=cp %>/bbs/list.do?Pool=4">방제보 게시판</a></br>
	<a href="<%=cp %>/bbs/list.do?Pool=5">정보공유 게시판</a></br>
	<a href="<%=cp %>/bbs/list.do?Pool=1">모임 게시판</a>

</body>
</html>