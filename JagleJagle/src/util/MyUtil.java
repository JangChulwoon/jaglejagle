package util;

public class MyUtil {
	//페이지 수 구하기
	public int getPageCount(int numPerPage, int dataCount){
		int pageCount=0;
		
		pageCount=dataCount/numPerPage;
		if(dataCount % numPerPage!=0)
			pageCount++;
		return pageCount;
	}
	
	public String pageIndexList(int current_page, int total_page, String list_url){
		if(current_page<1||total_page<1)
			return "";
		StringBuffer sb=new StringBuffer();
		int numPerBlock=10;
		int currentPageSetup;
		int n, page;
		
		if(list_url.indexOf("?")!=-1)
			list_url=list_url+"&";
		else
			list_url=list_url+"?";
		
		currentPageSetup = (current_page/numPerBlock)*numPerBlock;
		if(current_page%numPerBlock==0)
			currentPageSetup=currentPageSetup-numPerBlock;
		
		n=current_page-numPerBlock;
		if(total_page>numPerBlock && currentPageSetup>0){
			//sb.append("<a href='"+list_url+"pageNum=1;>1</a>&nbsp;");
			sb.append("<a href='"+list_url+"pageNum="+n+"'>[Prev]</a>&nbsp;");
		}
		
		page=currentPageSetup+1;
		while(page<=total_page && (page<=currentPageSetup+numPerBlock)){
			if(page==current_page){
				sb.append("<font color='Fuchsia'>"+page+"</font>&nbsp;");
			}else{
				sb.append("<a href='"+list_url+"pageNum="+page+"'>"+page+"</a>&nbsp;");
			}
			page++;
		}
	
	n = current_page+numPerBlock;
	if(total_page-currentPageSetup > numPerBlock){
		sb.append("<a href='"+list_url+"pageNum="+n+"'>[Next]</a>&nbsp;");
		//sb.append("<a href='"+list_url+"pageNum="+total_page+"'></a>");
	}
	return sb.toString();
	}
	
	public String escape(String str){
		if(str==null||str.length()==0)
			return "";
		StringBuilder builder = new StringBuilder((int)(str.length()*1.2f));
		
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			switch(c){
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '&':
				builder.append("&amp;");
				break;
			case '\"':
				builder.append("&quot;");
				break;
			default:
				builder.append(c);
			}
		}
		return builder.toString();
	}
	
	public String htmlSymbols(String str){
		if(str==null||str.length()==0)
			return "";
		
		str=str.replaceAll("&", "&amp;");
		str=str.replaceAll("\"", "&quot;");
		str=str.replaceAll(">", "&gt;");
		str=str.replaceAll("<", "&lt;");
		
		str=str.replaceAll(" ", "&nbsp;");
		str=str.replaceAll("\n", "<br>");
		
		return str;
	}
}

