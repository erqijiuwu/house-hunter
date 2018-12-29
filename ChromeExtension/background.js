console.log("Loading...");


document.targetRequest=null;
if(chrome && chrome.webRequest && chrome.webRequest.onBeforeSendHeaders){
	chrome.webRequest.onBeforeSendHeaders.addListener(
		function(details){		
			for(var i=0;i<details.requestHeaders.length;i++){
				var url=details.url;
				// console.log(url);

				var idx=url.indexOf('https://chalice-search-api.cloud.seek.com.au/search');

				if(idx!=0){
					continue;
				}

				idx=url.indexOf('?');

				var rst={
					'hostname': url.substr(0,idx),
					'parameters':{},
					'headers':{}
				}

				url=url.substr(idx+1);

				url.split('&').forEach(function(part){
					// console.log(part);
					var kv=part.split('=');
					if(kv[0]!='page'){
						rst.parameters[kv[0]]=kv[1];
					}
				});

				details.requestHeaders.forEach(function(header){
					rst.headers[header.name]=header.value;
				});

				document.targetRequest=rst;
			}
			// return {cancel:true};
		},

		{
			urls: ["<all_urls>"]
		},

		["blocking","requestHeaders"]
	);
}

if(chrome && chrome.runtime && chrome.runtime.onInstalled){
	chrome.runtime.onInstalled.addListener(function(){
	  chrome.contextMenus.create({
	    'id':'digdata',
	    'type':'normal',
	    'title':'Dig Data'
	  });
	});
}

var totalCount=1,readedCount=0,page=0;

//Add event of menu item
if(chrome && chrome.contextMenus && chrome.contextMenus.onClicked){
	chrome.contextMenus.onClicked.addListener(function(info, tab){
	  if(info.menuItemId == 'digdata'){
	  	totalCount=1;
	  	readedCount=0;
	  	page=0;
	  	download();
	  }
	});
}


function download(){
	console.log(document.targetRequest);

	if(!document.targetRequest || document.targetRequest==null){
		alert('Please click SEEK button to cache context!');
		return;
	}
	var req=document.targetRequest;
	var url=req.hostname;
	url+='?';
	var paras=req.parameters;
	for(var key in paras){
		var value=paras[key];
		url=url+key+'='+value+'&';
	}

	page++;
	console.log("totalCount="+totalCount+", readedCount="+readedCount+", page="+page);
	var reqUrl=url+'page='+page;
	httpRequest(reqUrl,null,upload);
}

function httpRequest(url,req=null,callback=null){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
    	var rsp=JSON.parse(xhttp.responseText);
      console.log(rsp);
      if(callback!=null){
      	callback(rsp);
      }
    }
	};

	if(req==null){
		xhttp.open("GET", url, true);
		xhttp.send();
	}else{
		xhttp.open("POST", url, true);
		xhttp.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
		xhttp.send(JSON.stringify(req));
	}
}

function upload(rsp){
	totalCount=rsp.totalCount;
	readedCount+=rsp.data.length;

	// UploadData to local db
	// rsp.data.forEach(function(jsonItem){
	// 	var req={
	// 		"id": jsonItem.id,
	// 		"jsonBody": JSON.stringify(jsonItem)
	// 	}
	// 	httpRequest('http://localhost:8080/jobdigdata',req,null);
	// });

	httpRequest('http://localhost:8080/jobdigdata',rsp.data,null);

	if(readedCount<totalCount){
		download();
	}
}

console.log("Load success");


