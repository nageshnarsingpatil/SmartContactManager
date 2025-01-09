console.log("admin user");

document.querySelector('#image_file_input').addEventListener('change',function(event){
	
	let file=event.target.files[0];
	let reader=new FileReader();
	reader.onload=function(){
		/*document.getElementById("previewImage").src=reader.result;*/
		document.querySelector('#upload_image_preview').setAttribute("src",reader.result);
	};
	reader.readAsDataURL(file);
	
})