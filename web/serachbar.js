// getting all required elements
const searchWrapper = document.querySelector(".search-input");
const inputBox = searchWrapper.querySelector("input");
const suggBox = searchWrapper.querySelector(".autocom-box");
const icon = searchWrapper.querySelector(".icon");
let linkTag = searchWrapper.querySelector("a");
let webLink;

// if user press any key and release
inputBox.onkeyup = (e)=>{
    let userData = e.target.value; //user enetered data
    if(userData){
		var xhr ;
		if (window.XMLHttpRequest) 
			xhr = new XMLHttpRequest();
		else 
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		xhr.onreadystatechange = function(){
			if(this.readyState == 4 && this.status==200){
				var res = this.responseText;
				var emptyArray = res.split("||");
//				var bid = [];
//				var bname = [];
	//			for(i=0;i<val.length-1;i++){
	//				var ival = val[i].split("|");
	//				bid.append(ival[0]);
	//				bname.append(ival[1]);
	//			}
				emptyArray = emptyArray.map((data)=>{
					temp = data.split("|");
					url  =  "/blogCreatingSystem/viewblog?bid="+temp[1];
					return data = '<li class="ui item" >'+ '<a class="header" href='+url+'>'+temp[0] +'</a></li>';
				});
				console.log(emptyArray);
				searchWrapper.classList.add("active"); //show autocomplete box
				showSuggestions(emptyArray);
			}
		};
		url = "/blogCreatingSystem/suggestblog?bname="+userData;
		xhr.open("get", url, true);
		xhr.send();
    }
	else{
        searchWrapper.classList.remove("active"); //hide autocomplete box
    }
}



function showSuggestions(list){
    let listData;
    if(!list.length){
        userValue = inputBox.value;
        listData = '<li>'+ userValue +'</li>';
    }else{
        listData = list.join('');
    }
    suggBox.innerHTML = listData;
}

function select(element){
    let selectData = element.textContent;
    inputBox.value = selectData;
    icon.onclick = ()=>{
        webLink = "https://www.google.com/search?q=" + selectData;
        linkTag.setAttribute("href", webLink);
        linkTag.click();
    }
    searchWrapper.classList.remove("active");
}


let suggestions = [
    "Channel",
    "CodingLab",
    "CodingNepal",
    "YouTube",
    "YouTuber",
    "YouTube Channel",
    "Blogger",
    "Bollywood",
    "Vlogger",
    "Vechiles",
    "Facebook",
    "Freelancer",
    "Facebook Page",
    "Designer",
    "Developer",
    "Web Designer",
    "Web Developer",
    "Login Form in HTML & CSS",
    "How to learn HTML & CSS",
    "How to learn JavaScript",
    "How to became Freelancer",
    "How to became Web Designer",
    "How to start Gaming Channel",
    "How to start YouTube Channel",
    "What does HTML stands for?",
    "What does CSS stands for?",
];

let links = [
    
]
