function showErrTips(msg)
{
	showTips(msg,"alert-danger");
}

function  showOKTips(msg)
{
	showTips(msg,"alert-success"); 
}	

function  showTips(msg,alertType)
{ 

	var domid = "ID" + new Date().getTime();
	var tipdlgHtml = "<div id='" + domid + "' class='tx_tipdlgmid '>" +  
		"<div class='tx_tipdlginner  " + alertType +"'>" + msg +
		"</div></div>";  
	 
	//新建一个div元素节点
	var div=document.createElement("div");
	div.innerHTML = tipdlgHtml;
	//把div元素节点添加到body元素节点中成为其子节点，但是放在body的现有子节点的最后
	document.body.appendChild(div);
	//插入到最前面
	document.body.insertBefore(div, document.body.firstElementChild);
	
	setTimeout(function(){document.body.removeChild(div);},1200);
}	

function  showTargetMsg(msg )
{ 	
	var domid = "ID" + new Date().getTime();
	var tipdlgHtml = "<div id='" + domid + "' class='tb_modal'> " +
	 " <div class='tx_modal_dlgframe' > " +
	  "<div class='tx_modal_dlgcontent' ><div class='tx_modal_dlgheader' ><div style='cursor: pointer;' onclick='  ()' > " +
		"	<svg xmlns='http://www.w3.org/2000/svg' width='30' height='30' fill='white' viewBox='0 0 16 16'> " +
		"		  <path d='M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z'/> " +
		"		</svg>" +
	  "		</div>" + msg + " </div> "  + 
	  "<div class='tx_modal_dlgcontainer'> " +
		  "<div class='input-group tx_mb_2'> " +
			  "<span class='input-group-text'>名称</span> " +
			  "<input type='text' id='tarname'   class='form-control' placeholder='名称'> " +
		  "</div>  " +
		   "<div class='input-group tx_mb_2'> " +
			  "<span class='input-group-text'>名称</span> " +
			  "<input type='text' id='tarname'   class='form-control' placeholder='名称'> " +
		  "</div>  " +
	  "</div>  " +
	  " </div>  </div></div>";  
	 
	//新建一个div元素节点
	var div=document.createElement("div");
	div.innerHTML = tipdlgHtml;
	
	//把div元素节点添加到body元素节点中成为其子节点，但是放在body的现有子节点的最后
	//document.body.appendChild(div);
	//插入到最前面
	//document.body.insertBefore(div, document.body.firstElementChild);
	
	// setTimeout(function(){document.body.removeChild(div);},2000);
	
	var div2 = document.getElementById("persondiv");
	div2.innerHTML = tipdlgHtml;
	
}

function  showTarget(target )
{ 	
	console.log(target)
	var targetId = 0 
	if(target.id != undefined)
	{
		 targetId = target.id
	}
	
	var targetName = ''
	var title = "新增"
	if(target.name != undefined)
	{
		title = target.name
		targetName = target.name
	}
	
	var tarDetail = ""
	if(target.detail!= undefined)
	{ 
		tarDetail = target.detail
	}
	
	var tarAddress = ""
	if(target.address!= undefined)
	{ 
		tarAddress = target.address
	}

	var tarbgtime = ""
	if(target.bgtime!= undefined)
	{ 
		tarbgtime = target.bgtime
	}
	
	var tarendtime = ""
	if(target.endtime!= undefined)
	{ 
		tarendtime = target.endtime
	}
	 
	var domid = "ID" + new Date().getTime();
	var tipdlgHtml = "<div id='" + domid + "' class='tb_modal' onclick='clickModal()'> " +
	 " <div class='tx_modal_dlgframe' > " +
	  "<div class='tx_modal_dlgcontent' ><div class='tx_modal_dlgheader' ><div style='cursor: pointer;' onclick='closeModalDlg()' > " +
		"	<svg xmlns='http://www.w3.org/2000/svg' width='30' height='30' fill='currentcolor' viewBox='0 0 16 16'> " +
		"		  <path d='M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z'/> " +
		"		</svg>" +
	  "		</div>" + title + " </div> "  + 
	  "<div class='tx_modal_dlgcontainer'> " +
		  "<div class='input-group tx_mb_2'> " +
			  "<span class='input-group-text'>名称</span> " +
			   "<input type='hidden' id='t_tarid' value='" +   targetId +   "' > " +
			  "<input type='text' id='t_tarname' value='" +   targetName +   "'  class='form-control' placeholder='名称'> " +
			  "<span class='input-group-text'>类型</span> " +
			  	"<select class='form-select tx_input_font' id='t_tartype'> " +
				"		<option value='1' selected='true'>人物</option> " +
				"		<option value='2'>事件</option>" +
				"		<option value='3'>地点</option>" +
				"		<option value='4'>官职</option>" +
				"		<option value='5'>其它</option>" +
				"	</select>" +
		  "</div>  " +
		  "<div class='input-group tx_mb_2'> " +
			  "<span class='input-group-text'>年份</span> " +
			  "<input type='number' id='t_startyear' value='" + tarbgtime + "' min='-1000' max='1900' onchange='inputyear()' class='form-control' placeholder='开始/出生年份'> " +
			  "<span class='input-group-text'> - </span> " +
			  "<input type='number' value='" + tarendtime + "' id='t_endyear' min='-1000' max='1900'  onchange='inputyear()'  class='form-control' placeholder='结束年份'> " +
		  "</div>  " +
		   "<div class='input-group tx_mb_2'> " + 
			  "<span class='input-group-text'>国家</span> " +
			  "<select class='form-select tx_input_font' id='t_tarnation'> " +
				"		<option value='0' selected='true'>请选择</option> " + 
			  "</select>" +
		  "</div>  " +
		   "<div class='input-group tx_mb_2 dropdown'> " +
			  "<span class='input-group-text'>地址</span> " +
			  "<input type='text' id='t_taraddr' onInput='loadAddress()'  value='" + tarAddress + "'  class='form-control'  data-bs-toggle='dropdown'  placeholder='地址'> " +
		  	  "<ul class='dropdown-menu' id='tx_ul_addrlist'> " + 
			  "</ul> " + 
		  "</div>  " +
		
		  "<div class='input-group tx_mb_2'> " +
			  "<span class='input-group-text'>描述</span> " +
			  "<textarea  cols='8' rows='10'  id='t_tardetail'   class='form-control' placeholder='简介描述'> " + tarDetail + "</textarea>" +
		  "</div>  " + 
		  "<div class='input-group tx_mb_2'  style='flex-direction: row-reverse;'>  " + 
			  "<button type='button' class='tb_btn'  onClick='toSaveTarget()' >保存</button>" +
		  "</div>  " + 
	  "</div> <div class='tb_modal_baike_btn'>百科 </div> </div>  " + 
	  "" +
	  
	  "</div></div>";  
	 
	//新建一个div元素节点
	var div=document.createElement("div");
	div.innerHTML = tipdlgHtml;
	
	//把div元素节点添加到body元素节点中成为其子节点，但是放在body的现有子节点的最后
	//document.body.appendChild(div);
	//插入到最前面
	//document.body.insertBefore(div, document.body.firstElementChild);
	
	// setTimeout(function(){document.body.removeChild(div);},2000);
	
	var div2 = document.getElementById("persondiv");
	div2.innerHTML = tipdlgHtml; 
	loadNationDynasty()
	loadAddress() 
}

function toSaveTarget()
{ 
	var tarid = document.getElementById("t_tarid").value
	var name =  document.getElementById("t_tarname").value 
	var country = document.getElementById("t_tarnation").value 
	var address = document.getElementById("t_taraddr").value
	var startYear = document.getElementById("t_startyear").value
	var endYear = document.getElementById("t_endyear").value
	var detail = document.getElementById("t_tardetail").value
			  
			
	const params = {
		id:tarid,
		name:name, 
		country:country,
		address:address,
		bgtime:startYear,
		endtime:endYear,
		detail:detail , 
	}
	axios
	  .post('/savetarget', params)
	  .then(
	  function (response) {
		  showOKTips(params.name + "：保存成功！") 
		  closeModalDlg()
	  }  
	  )
	  .catch(function (error) { // 请求失败处理
		  showErrTips(params.name + "：保存失败！")
	});  
			
	console.log(params)
}

function inputyear()
{
	loadNationDynasty()
}

function loadNationDynasty()
{ 
	 
	var bgYear = document.getElementById("t_startyear").value;
	var endYear = document.getElementById("t_endyear").value;
	
	const params   = {
		bgTime:-50 + Number(bgYear ),
		endTime:50 + Number(endYear) 
	}
	
	axios.get('/selectcountry' ,{params:params} )
	  .then(response => {
			var countrys = response.data.reObject   
			   
			var sel = document.getElementById("t_tarnation");
			sel.innerHTML = "";
		  	for(i=0;i< countrys.length;i++   ) 
			{
				var opt = new Option(countrys[i].name,countrys[i].id);//第一个代表标签内的内容，第二个代表value
				sel.options.add(opt);
			}
		})
	  .catch(function (error) {  
		 showErrTips("朝代信息：" + error) 
	});
}

function loadAddress()
{ 
	var address = document.getElementById("t_taraddr").value
	const params2  = {
		bgTime:-1000,
		endTime:2000,
		name:address
	}
	 
	axios.get('/selectaddress' ,{params:params2} )
	  .then(response => {
			var addrs = response.data.reObject   
			var addrUl = document.getElementById("tx_ul_addrlist");
			addrUl.innerHTML = "" 
			for(i=0;i< addrs.length;i++   ) 
			{
				var  newLi =  document.createElement("li");
				newLi.innerHTML = "<div class='dropdown-item' onClick='setAddress(\"" + addrs[i].name + "\")'>" + addrs[i].name + "</div> "
				addrUl.appendChild(newLi)
			}
		})
	  .catch(function (error) {  
		 showErrTips("地址信息：" + error) 
	});
}

function setAddress(addrs)
{
	console.log(addrs) 
	document.getElementById("t_taraddr").value = addrs
}
		
function clickModal()
{
	//console.log(event.srcElement.className)
	
	if("tb_modal"==event.srcElement.className)
	{
		closeModalDlg("")
	}
}

function closeModalDlg(divid)
{
	console.log("divid:" + divid)
	var div = document.getElementById(divid); 
	console.log(div)
	//div.remove()
	
	var div2 = document.getElementById("persondiv");
	div2.innerHTML = "";
}

function getUrlParam(pname)
{
	var Param= new Object();

	Param= getUrlParams();
	
	return (Param[pname])	
}

 
function getUrlParams() { 
	
    var url = window.location.search; //获取url中"?"符后的字符串包括‘？’ ，window.location.href获取的是url完整的字符串
     
    var theParam = new Object(); 
    if (url.indexOf("?") != -1) { //确保‘？’不是最后一个字符串，即携带的参数不为空
        var str = url.substr(1); //substr是字符串的用法之一，抽取指定字符的数目，这里抽取？后的所有字符
        strs = str.split("&"); //将获取到的字符串从&分割，输出参数数组，即输出[参数1=xx,参数2=xx,参数3=xx,...]的数组形式
        for(var i = 0; i < strs.length; i ++) { //遍历参数数组
            theParam[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); //这里意思是抽取每个参数等号后面的值，unescape是解码的意思
        } 
    } 
    return theParam; //返回参数值
}	
 
 