console.log("script loaded");

let currentTheme=getTheme();
//intial 
changeTheme()
function changeTheme(){
	//set to web page
	document.querySelector('html').classList.add(currentTheme);
	
	//set the listner to change theme
	const changeThemeButton=document.querySelector('#theme_change_button');
	changeThemeButton.addEventListener("click",(event)=>{
		const oldTheme=currentTheme;
		
		if(currentTheme==="dark"){
			currentTheme="Light";
		}else{
			currentTheme="dark";
		}
		//local storage update value
		setTheme(currentTheme);
		
		//remove the old theme 
		document.querySelector('html').classList.remove(oldTheme);
		//set the current theme
		document.querySelector('html').classList.add(currentTheme);
		
		//change the text of current button
		changeThemeButton.querySelector("span").textContent=currentTheme;
	});
}

//set theme to local storage
function setTheme(theme){
	localStorage.setItem("theme",theme);
}

//get theme  from local storage
function getTheme(){
	let theme=localStorage.getItem("theme");
	if(theme){
		return theme;
	}else{
		return "light";
	}
}