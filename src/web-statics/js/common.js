var navClosed = true

 
function processDot(num, precision) {
  return (+(Math.round(+(num + "e" + precision)) + "e" + -precision)).toFixed(
    precision,
  );
}
 
function formatNumber(num) {
  const result = processDot(num, 2);
  return result.replace(/\d(?=(\d{3})+\.)/g, "$&,");
} 

function formatAmta(amt)
{
	 return String(amt)
    .split('')
    .reverse()
    .reduce((prev, next, index) => {
      return (index % 3 ? next : next + ',') + prev;
    }); 
}

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
	
	setTimeout(function(){document.body.removeChild(div);},1500);
}	
  
function navNav()
{ 
	if(navClosed)
	{ 
		document.getElementById("mainmenulist").style.height = "1000px"; 
		
	}
	else{ 
		document.getElementById("mainmenulist").style.height = "0";   
	}
	
	navClosed = !navClosed
		
}

function closeNav()
{  
	document.getElementById("mainmenulist").style.height = "0";    
	navClosed = true
		
}


function getDate( )
{
	var curDate = new Date()
	var year = curDate.getFullYear() 
	var month = curDate.getMonth() + 1; 
	var day = curDate.getDate();  
	
	var dayStr = "0" + day
	if(day>9)
	{
		dayStr =   day
	} 
	
	var currdate = year + "-0" + month + "-" + dayStr 
	if(month>9)
	{
		currdate = year + "-" + month + "-" + dayStr 
	} 
	
	return  currdate
}

//alert(getDate());            //当前时间
//alert(getDate("day", -2));   //前两天的时间
//alert(getDate("day", 2));    //后两天的时间
//alert(getDate("month", -2)); //前两个月的时间
//alert(getDate("month", 2));  //后两个月的时间
 

function getDate(type=null,number=0) {
	
	
            
    var nowdate = new Date();
    var y = nowdate.getFullYear();
    var m = nowdate.getMonth() + 1;
    var d = nowdate.getDate();
    
     
    switch (type) {
        case "day":   //取number天前、后的时间
            nowdate.setTime(nowdate.getTime() + (24 * 3600 * 1000) * number);
            var y = nowdate.getFullYear();
            var m = nowdate.getMonth() + 1;
            var d = nowdate.getDate();
            
            break;
        case "week":  //取number周前、后的时间
            var weekdate = new Date(nowdate + (7 * 24 * 3600 * 1000) * number);
            var y = weekdate.getFullYear();
            var m = weekdate.getMonth() + 1;
            var d = weekdate.getDate();
             
            break;
        case "month":  //取number月前、后的时间
            nowdate.setMonth(nowdate.getMonth() + number);
            var y = nowdate.getFullYear();
            var m = nowdate.getMonth() + 1;
            var d = nowdate.getDate();
             
            break;
        case "year":  //取number年前、后的时间
            nowdate.setFullYear(nowdate.getFullYear() + number);
            var y = nowdate.getFullYear();
            var m = nowdate.getMonth() + 1;
            var d = nowdate.getDate();
             
            break;
        default:     //取当前时间
            var y = nowdate.getFullYear();
            var m = nowdate.getMonth() + 1;
            var d = nowdate.getDate();
             
    }
    
    
    
    var dayStr = "0" + d
	if(d>9)
	{
		dayStr =   d
	} 
	
	var currdate = y + "-0" + m + "-" + dayStr 
	if(m>9)
	{
		currdate = y + "-" + m + "-" + dayStr 
	} 
	 
	
	return  currdate 
}

function toAmtNumber(value)
{
	var value1 = String(value).replace(",","");
	
	while(String(value1).indexOf(",")>=0)
	{
		value1 = String(value1).replace(",","");
	}
	 
	return Number(value1) ;
}

function tipDiv(divId){
                         
                       
	var div = document.getElementById(divId);
	
	div.className = div.className + " tx_showborder";
	 
	
	setTimeout(function(){
	
	var div = document.getElementById(divId);
	
	div.className = div.className.replace("tx_showborder","");
	
	},1000);
	  
}

function convertCurrency(currencyDigits) { 
	// Constants: 
	    var MAXIMUM_NUMBER = 99999999999.99; 
	    // Predefine the radix characters and currency symbols for output: 
	    var CN_ZERO = "零"; 
	    var CN_ONE = "壹"; 
	    var CN_TWO = "贰"; 
	    var CN_THREE = "叁"; 
	    var CN_FOUR = "肆"; 
	    var CN_FIVE = "伍"; 
	    var CN_SIX = "陆"; 
	    var CN_SEVEN = "柒"; 
	    var CN_EIGHT = "捌"; 
	    var CN_NINE = "玖"; 
	    var CN_TEN = "拾"; 
	    var CN_HUNDRED = "佰"; 
	    var CN_THOUSAND = "仟"; 
	    var CN_TEN_THOUSAND = "万"; 
	    var CN_HUNDRED_MILLION = "亿"; 
	    //var CN_SYMBOL = "人民币"; 
	    var CN_SYMBOL = ""; 
	    var CN_DOLLAR = "元"; 
	    var CN_TEN_CENT = "角"; 
	    var CN_CENT = "分"; 
	    var CN_INTEGER = "整"; 
	     
	// Variables: 
	    var integral;    // Represent integral part of digit number. 
	    var decimal;    // Represent decimal part of digit number. 
	    var outputCharacters;    // The output result. 
	    var parts; 
	    var digits, radices, bigRadices, decimals; 
	    var zeroCount; 
	    var i, p, d; 
	    var quotient, modulus; 
	     
	// Validate input string: 
	    currencyDigits = currencyDigits.toString(); 
	    if (currencyDigits == "") { 
	         
	        return currencyDigits; 
	    } 
	    if (currencyDigits.match(/[^,.\d]/) != null) { 
	         
	        return currencyDigits; 
	    } 
	    if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) { 
	        alert("小写金额的格式不正确！"); 
	        return ""; 
	    } 
	     
	// Normalize the format of input digits: 
	    currencyDigits = currencyDigits.replace(/,/g, "");    // Remove comma delimiters. 
	    currencyDigits = currencyDigits.replace(/^0+/, "");    // Trim zeros at the beginning. 
	    // Assert the number is not greater than the maximum number. 
	    if (Number(currencyDigits) > MAXIMUM_NUMBER) { 
	        alert("金额过大，应小于1000亿元！"); 
	        return ""; 
	    } 
	     
	// Process the coversion from currency digits to characters: 
	    // Separate integral and decimal parts before processing coversion: 
	    parts = currencyDigits.split("."); 
	    if (parts.length > 1) { 
	        integral = parts[0]; 
	        decimal = parts[1]; 
	        // Cut down redundant decimal digits that are after the second. 
	        decimal = decimal.substr(0, 2); 
	    } 
	    else { 
	        integral = parts[0]; 
	        decimal = ""; 
	    } 
	    // Prepare the characters corresponding to the digits: 
	    digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE); 
	    radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND); 
	    bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION); 
	    decimals = new Array(CN_TEN_CENT, CN_CENT); 
	    // Start processing: 
	    outputCharacters = ""; 
	    // Process integral part if it is larger than 0: 
	    if (Number(integral) > 0) { 
	        zeroCount = 0; 
	        for (i = 0; i < integral.length; i++) { 
	            p = integral.length - i - 1; 
	            d = integral.substr(i, 1); 
	            quotient = p / 4; 
	            modulus = p % 4; 
	            if (d == "0") { 
	                zeroCount++; 
	            } 
	            else { 
	                if (zeroCount > 0) 
	                { 
	                    outputCharacters += digits[0]; 
	                } 
	                zeroCount = 0; 
	                outputCharacters += digits[Number(d)] + radices[modulus]; 
	            } 
	            if (modulus == 0 && zeroCount < 4) { 
	                outputCharacters += bigRadices[quotient]; 
	                zeroCount = 0; 
	            } 
	        } 
	        outputCharacters += CN_DOLLAR; 
	    } 
	    // Process decimal part if there is: 
	    if (decimal != "") { 
	        for (i = 0; i < decimal.length; i++) { 
	            d = decimal.substr(i, 1); 
	            if (d != "0") { 
	                outputCharacters += digits[Number(d)] + decimals[i]; 
	            } 
	        } 
	    } 
	    // Confirm and return the final output string: 
	    if (outputCharacters == "") { 
	        outputCharacters = CN_ZERO + CN_DOLLAR; 
	    } 
	    if (decimal == "") { 
	        outputCharacters += CN_INTEGER; 
	    } 
	    outputCharacters = CN_SYMBOL + outputCharacters; 
	    return outputCharacters; 
} 

 