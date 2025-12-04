window.onload = function(){
	document.getElementById("startmenu").innerHTML = getStartMenuHtml_fb()
} 

function getStartMenuHtml_fb()
{
	var menulist =  getStartMenuHtml()
	
	menulist = menulist + "<div  class=\"tx_bottom\"></div><div  class=\"tx_bottomLev3\"></div>" +
	"	<div  class=\"tx_bottomLev2\"></div>" 
	
	return menulist
}


function getStartMenuHtml()
{
	var menulist = "<div id=\"mainmenulist\" class=\"tx_mainmenulist \" >"+ 
	"	<div id=\"accordion\"  > 	 "+
	 
    "   <div class=\"tx_menulv1\" data-bs-toggle=\"collapse\" href=\"#collapseOne\"> "+ 
    "    基础管理 </div> "+
    "   <div id=\"collapseOne\" class=\"collapse\" data-bs-parent=\"#accordion\">"+ 
    "	  <div class=\"tx_menulv2 \"onclick=\"window.open('../txwk/companylist.html','_self')\">  公司列表</div>"+
	"	  <div class=\"tx_menulv2   \"onclick=\"window.open('../txwk/cominvs.html','_self') \"> 供应商台账</div>"+
	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/accounts.html','_self')\">   公司账户  </div>"+
	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/contacter.html','_self')\">  公司联系人 </div>	"+
	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/buslist.html','_self')\"> 合作项目 </div>"+ 
    "    </div> "+
    
    "   <div class=\"tx_menulv1\" data-bs-toggle=\"collapse\" href=\"#acclistdetail\"> "+ 
    "    汇款明细管理 </div> "+
    "   <div id=\"acclistdetail\" class=\"collapse\" data-bs-parent=\"#accordion\">"+ 
   	"	   <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/acclist.html','_self')\"> 汇款明细(系统) </div>   " +
   	"	   <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/billfiledata.html','_self')\"> 汇款记录(文件) </div>"+ 
	"	   <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/checkbills.html','_self')\"> 汇款明细核对 </div>"+ 
    "    </div> "+
    
 	
    "   <div class=\"tx_menulv1\" data-bs-toggle=\"collapse\" href=\"#businessdetail\"> "+ 
    "    业务数据管理 </div> "+
    "   <div id=\"businessdetail\" class=\"collapse\" data-bs-parent=\"#accordion\">"+ 
    "	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/belist.html','_self')\"> 出口关单列表</div>"+ 
   	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/invlist.html','_self')\"> 工厂发票明细</div>"+
   	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/accretaxlist.html','_self')\"> 退税流水明细 </div>   " +
	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/accchecklist.html','_self')\"> 公账银行明细 </div>   " +
	"	  <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/invacclist.html','_self')\"> 报销费用明细 </div></div>  " +
	"    </div> "+
	 
	
	
    "   <div class=\"tx_menulv1\" data-bs-toggle=\"collapse\" href=\"#systool\"> "+ 
    "    系统工具</div> "+
    "   <div id=\"systool\" class=\"collapse\" data-bs-parent=\"#accordion\">"+ 
    "	   <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/filemngt.html','_self')\"> 文件数据上传 </div>"+
	"	   <div class=\"tx_menulv2  \"onclick=\"window.open('../txwk/pythons.html','_self')\"> Python脚本管理 </div>"+
	"    </div> "  ;
	
	return "<div onclick=\"navNav()\" class=\"tx_startmenu\"  >"+
	"<span> "+
	"<svg width=\"36\" height=\"36\" fill=\"white\" viewBox=\"0 0 24 24 \"  "+
	"xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M12 17a2 2 0 110 4 2 2 0 010-4zm7 0a2 2 0  "+
	"110 4 2 2 0 010-4zM5 17a2 2 0 110 4 2 2 0 010-4zm7-7a2 2 0 "+
	"110 4 2 2 0 010-4zm7 0a2 2 0 110 4 2 2 0 010-4zM5 10a2 2 0 "+
	"110 4 2 2 0 010-4zm7-7a2 2 0 110 4 2 2 0 010-4zm7 0a2 2 0 "+
	"110 4 2 2 0 010-4zM5 3a2 2 0 110 4 2 2 0 010-4z\"></path></svg> "+
	"</span>  </div> " + menulist;
}
 