var targets = []
var dynastys = []
var theme = '' 
var target = {} 
 
function getTheme( )
{
	 
	axios.get('/getTheme'  )
		  .then(response => {
			  this.theme = response.data.reObject  
			  document.body.className = this.theme
			})
		  .catch(function ( ) {  
			  
		});
		
	return targets
}

function getTargets(gid)
{
	const params = {gid:gid}
	
	axios.get('/selectTargetsByParag',{params:params} )
		  .then(response => {
			  this.targets = response.data.reObject  
			})
		  .catch(function (error) {  
			 showErrTips(error) 
		});
		
	return targets
}

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
 
function getDynastys()
{ 
	axios.get('/selectDynasty'  )
		  .then(response => {
			  this.dynastys = response.data.reObject  
			})
		  .catch(function (error) {  
			 showErrTips(error) 
		});
		
	return dynastys
}