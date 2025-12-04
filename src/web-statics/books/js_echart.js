var myEchats;

 
function createEcharts(divId)
{
	myEchats = echarts.init(document.getElementById(divId));
	 
	initOptions()
	
	return myEchats;
}

function testData()
{
	var datas = [
		  {
		    "id": "1938",
		    "name": "萧昭基",
		    "symbolSize": "20",
		    "category": "一级",
		    "draggable": true,
		    "type": 0,
		    "status": 0,
		    "latitude": 0,
		    "longitude": 0
		  },
		  {
		    "id": "1598",
		    "name": "萧无极可以",
		    "symbolSize": "12",
		    "category": "一级",
		    "draggable": true,
		    "type": 0,
		    "status": 0,
		    "latitude": 0,
		    "longitude": 0
		  },
		  {
		    "id": "1597",
		    "name": "萧子1597",
		    "symbolSize": "12",
		    "category": "一级",
		    "draggable": true,
		    "type": 0,
		    "status": 0,
		    "latitude": 0,
		    "longitude": 0
		  },
		  {
		    "id": "1596",
		    "name": "刘骏帝",
		    "symbolSize": "12",
		    "category": "刘宋",
		    "draggable": true,
		    "type": 0,
		    "status": 0,
		    "latitude": 0,
		    "longitude": 0
		  },
		  {
		    "id": "1734",
		    "name": "萧南鸾",
		    "symbolSize": "12",
		    "category": "南齐",
		    "draggable": true,
		    "type": 0,
		    "status": 0,
		    "latitude": 0,
		    "longitude": 0
		  }]
	  
	var links = [
		  {
		    "source": "1938",
		    "target": "1598", 
		    "category": "一级"
		  },
		  {
		    "source": "1938",
		    "target": "1597", 
		    "category": "一级"
		  },
		  {
		    "source": "1938",
		    "target": "1596", 
		    "category": "一级"
		  },
		  {
		    "source": "1938",
		    "target": "1734", 
		    "category": "一级"
		  }  
		]
		
	var cats = [ 
		  {
		    "name": "一级"
		  },
		  {
		    "name": "刘宋"
		  },
		  {
		    "name": "南齐"
		  },
		  {
		    "name": "北魏"
		  },
		  {
		    "name": "二级"
		  },
		  {
		    "name": "南梁"
		  }
		]
	
	// 把数据设置到Echart中data
	myEchats.setOption({
	    series: [{
			name: "9999",
	        data: datas,
	        categories: cats,
	        links:links,
	        lineStyle: {
	            // 线的颜色[ default: '#aaa' ]
	            //color: '#aaa',
	            color:  'red' ,   
	            // 线宽[ default: 1 ]
	            width: 1,
	            // 线的类型[ default: solid实线 ]   'dashed'虚线    'dotted'
	            type: 'solid',
	            // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。[ default: 0.5 ]
	            opacity: 0.5,
	            // 边的曲度，支持从 0 到 1 的值，值越大曲度越大。[ default: 0 ]
	            curveness: 0
	        }
	    }]
	})
	
}

function initOptions()
{
	
	var echartOptions = {
	    title: { },
	    tooltip: {
	        show: false
	    },
	    legend: {
	        show: false, 
			textStyle:{ 
                  color:'red' 
            }
 
	    },
	    xAxis: {
	        show: false
	    },
	    yAxis: {
	        show: false
	    },
	    grid: {
	        top: '100px'
	    },
	    series: [{
	        type: "graph",
	        // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
	        roam: true,
	        // 是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。[ default: false ]
	        focusNodeAdjacency: true,
	        // 力引导布局相关的配置项，力引导布局是模拟弹簧电荷模型在每两个节点之间添加一个斥力，每条边的两个节点之间添加一个引力，每次迭代节点会在各个斥力和引力的作用下移动位置，多次迭代后节点会静止在一个受力平衡的位置，达到整个模型的能量最小化。
	        force: {
	            // 力引导布局的结果有良好的对称性和局部聚合性，也比较美观。
	            // [ default: 50 ]节点之间的斥力因子(关系对象之间的距离)。支持设置成数组表达斥力的范围，此时不同大小的值会线性映射到不同的斥力。值越大则斥力越大
	            repulsion: 300,
	            // [ default: 30 ]边的两个节点之间的距离(关系对象连接线两端对象的距离,会根据关系对象值得大小来判断距离的大小)，
	            edgeLength: [100, 300]
	                // 这个距离也会受 repulsion。支持设置成数组表达边长的范围，此时不同大小的值会线性映射到不同的长度。值越小则长度越长。如下示例:
	                // 值最大的边长度会趋向于 10，值最小的边长度会趋向于 50      edgeLength: [10, 50]
	        }, 
	        // 图的布局。[ default: 'none' ]
	        layout: "force",
	        // 'none' 不采用任何布局，使用节点中提供的 x， y 作为节点的位置。
	        // 'circular' 采用环形布局;'force' 采用力引导布局.
	        // 标记的图形
	        symbol: '',
	        // 关系边的公用线条样式。其中 lineStyle.color 支持设置为'source'或者'target'特殊值，此时边会自动取源节点或目标节点的颜色作为自己的颜色。
	         
	        lineStyle: {
	            // 线的颜色[ default: '#aaa' ]
	            //color: '#aaa',
	            color:  '#cde7e4' ,   
	            // 线宽[ default: 1 ]
	            width: 1,
	            // 线的类型[ default: solid实线 ]   'dashed'虚线    'dotted'
	            type: 'solid',
	            // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。[ default: 0.5 ]
	            opacity: 0.5,
	            // 边的曲度，支持从 0 到 1 的值，值越大曲度越大。[ default: 0 ]
	            curveness: 0
	        },
	         
	        // 关系对象上的标签
	        label: {
	            normal: {
	                // 是否显示标签
	                show: true,
	                // 标签位置:'top''left''right''bottom''inside''insideLeft''insideRight''insideTop''insideBottom''insideTopLeft''insideBottomLeft''insideTopRight''insideBottomRight'
	                position: "bottom",
	                // 文本样式
	                textStyle: {
	                    fontSize: 11,
	                    color: '#cde7e4'
	                }
	            }
	        },
	        // 连接两个关系对象的线上的标签
	        edgeLabel: {
	            normal: {
	                show: true,
	                textStyle: {
	                    // fontSize: 14
	                },
	                color: '#cde7e4',
	                // 标签内容
	                formatter: function(param) {
	                    //return param.data.category;
	                    return "";
	                }
	            }
	        }  
	    }]
	}
	
	myEchats.setOption(echartOptions, true);
}

function showEcharts(pers,links,categories)
{  
	//console.log( "pers " +  pers);
	myEchats.setOption({ 
        series: [{  
            data: pers,
            // 节点分类的类目，可选。如果节点有分类的话可以通过 data[i].category 指定每个节点的类目，类目的样式会被应用到节点样式上。图例也可以基于categories名字展现和筛选。
            // 类目名称，用于和 legend 对应以及格式化 tooltip 的内容。
            categories: categories,
            // 节点间的关系数据
            // 关系对象连接线上的标签内容
            links: links 
        }]
    });   
}
 
function pubdata(tarid,pers,links,categories) {
	 
    var androidMap = pers;
 
    var picList = [];//获取出全部图片
    for (var i = 0; i < androidMap.length; i++) { 
		
                
        var imgPath = "img/person.svg"
        
        if(androidMap[i].id==tarid)
        {
			imgPath = "img/personc.svg";
		}
		
		//把图片路径转成canvas 
		let p = getImgData(imgPath); 
        
        picList.push(p); 
    }
 
    Promise.all(picList).then(function (images) {
 
        //取出base64 图片 然后赋值到jsondata中
        for (var i = 0; i < images.length; i++) {
            var img = "image://" + images[i]; 
            //androidMap[i].symbol = img;
            androidMap[i].symbolSize = 30;
            
            if(androidMap[i].id==tarid)
            {
				androidMap[i].symbolSize = 50;
			}
        }
 
		// 把数据设置到Echart中data
		myEchats.setOption({
		    series: [{
		        data: androidMap,
		        categories: categories,
		        links:links,
		        lineStyle: {
		            // 线的颜色[ default: '#aaa' ]
		            //color: '#aaa',
		            color:  '#cde7e4' ,   
		            // 线宽[ default: 1 ]
		            width: 1,
		            // 线的类型[ default: solid实线 ]   'dashed'虚线    'dotted'
		            type: 'solid',
		            // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。[ default: 0.5 ]
		            opacity: 0.5,
		            // 边的曲度，支持从 0 到 1 的值，值越大曲度越大。[ default: 0 ]
		            curveness: 0
		        }
		    }]
		})
	})
 
}	 
 
function getImgData(imgSrc) {
 
	var fun = function (resolve) {
		const canvas = document.createElement('canvas');
		const contex = canvas.getContext('2d');
		const img = new Image();
		img.crossOrigin = '';
	 
	    img.onload = function () {
	        //设置图形宽高比例
	        center = {
	            x: img.width / 2,
	            y: img.height / 2
	        }
	        var diameter = img.width;//半径
	        canvas.width = diameter;
	        canvas.height = diameter;
	        contex.clearRect(0, 0, diameter, diameter);
	        contex.save();
	        contex.beginPath();
	        radius = img.width / 2;
	        contex.arc(radius, radius, radius, 0, 2 * Math.PI); //画出圆
	        contex.clip(); //裁剪上面的圆形
	        contex.drawImage(img, center.x - radius, center.y - radius, diameter, diameter, 0, 0,
	            diameter, diameter); // 在刚刚裁剪的园上画图
	        contex.restore(); // 还原状态
	        resolve(canvas.toDataURL('image/png', 1))
	    }
    
    	img.src = imgSrc;
	}
 
	var promise = new Promise(fun);
 
	return promise
}
 