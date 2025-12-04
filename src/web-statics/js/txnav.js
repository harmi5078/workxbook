 

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
	
	"	<div id=\"managerMenu\" >"+
	"	  <div class=\"tx_menulv1 \"onclick=\"window.open('../txwk/companylist.html','_self')\"> "+
	"		 公司列表</div>"+
	"	  <div class=\"tx_menulv1   \"onclick=\"window.open('../txwk/cominvs.html','_self') \"> 供应商台账</div>"+
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/accounts.html','_self')\">   公司账户  </div>"+
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/contacter.html','_self')\">  公司联系人 </div>	</div> "+
	 
	
	"	<div id=\"bussMenu\"   >"+
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/buslist.html','_self')\"> 合作项目 </div>"+ 
	"	 </div>"+
	
	 
	"	<div id=\"billentryMenu\"  >"+
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/belist.html','_self')\"> 出口关单</div>"+ 
	"	 </div>"+
  
	 
	"	<div id=\"finaMenu\" >"+
	
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/invlist.html','_self')\"> 发票明细</div>"+
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/acclist.html','_self')\"> 支付流水 </div>   " +
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/accchecklist.html','_self')\"> 对账明细 </div>   " +
	"	  <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/invacclist.html','_self')\"> 报销费用 </div></div>  " +
	
	 
	"	<div id=\"fileMenu\" >"+
	"	   <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/fileupd.html','_self')\"> 文件数据上传 </div>"+
	"	   <div class=\"tx_menulv1  \"onclick=\"window.open('../txwk/checkbills.html','_self')\"> 汇款支付核对 </div>"+
	 
	"	   </div>  </div> </div>  " ;
	
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
 