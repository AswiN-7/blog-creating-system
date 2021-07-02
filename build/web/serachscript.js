btn = document.getElementById('button');
//country = document.getElementById('country');
//btn.addEventListener('click', show);
var blist = document.getElementById("blogs");
//function show(){
//	location.replace(");
//}

var url;
function loadText(blogs){
    console.log('button clicked');
	 var xhr ;
	if (window.XMLHttpRequest) 
		xhr = new XMLHttpRequest();
	else 
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	// xhr.responseType = 'json';
	xhr.onreadystatechange = function(){
		if(this.readyState == 4 && this.status==200){
			// console.log("response came");
			var res = this.responseText;
			var val = res.split("||");
			for(i=0;i<val.length-1;i++){
				var ival = val[i].split("|");
				var option = document.createElement('option');
				option.value = ival[0];
				// option.addEventListener("click", linkId(ival[1]));
				blist.appendChild(option);
				console.log(ival[0], ival[1]);
			}
		}
	};
	console.log(blogs);
	url = "/blogCreatingSystem/suggestblog?bname="+blogs;
	xhr.open("get", url, true);
	xhr.send();
    console.log(xhr);
}

function linkId(bid){
	var linker = "/blogCreatingSystem/viewblog?bid="+bid;
	location.replace(linker);
}