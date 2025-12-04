var target = {} 
   

function onetarget(tarid)
{
	const params = {tarid:tarid}
	
	axios.get('/onetarget',{params:params} )
		  .then(response => {
			  this.target = response.data.reObject  
			})
		  .catch(function (error) {  
			 showErrTips(error) 
		});
		
	return target
}
  

function  showTargetMsg333(msg )
{
	byClassDivs = document.getElementsByClassName("bk_tipdlgmsg") 
		 	
	for (var i=0;i<byClassDivs.length;i++)
	{  
		document.body.removeChild(byClassDivs[i]);  
	}
			
	var domid = "ID" + new Date().getTime();
	var tipdlgHtml = "<div id='" + domid + "' class='bk_tipdlgmsg'>  <div  >" + msg +
		"</div> </div>";  
	 
	//新建一个div元素节点
	var div=document.createElement("div");
	div.innerHTML = tipdlgHtml;
	//把div元素节点添加到body元素节点中成为其子节点，但是放在body的现有子节点的最后
	document.body.appendChild(div);
	//插入到最前面
	document.body.insertBefore(div, document.body.firstElementChild);
	
	//setTimeout(function(){document.body.removeChild(div);},2000);
}
  