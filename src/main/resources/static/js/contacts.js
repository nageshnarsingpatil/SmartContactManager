//show details of contact

console.log("contacts search view page");

const baseURL="http://localhost:8083";
const viewConstantModal=document.getElementById("view_contact_modal");

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal=new Modal(viewConstantModal,options,instanceOptions);

function openContactModal(){
	contactModal.show();
}

function closeContactModal(){
	contactModal.hide();
}

async function loadContactdata(id){
	//function call to load data 
	console.log(id);
	try{
	const data=await(await fetch(`${baseURL}/api/contacts/${id}`)).json();
	console.log(data);
	document.querySelector('#contact_name').innerHTML=data.name;
	document.querySelector('#contact_email').innerHTML=data.email;
	document.querySelector('#contact_number').innerHTML=data.phoneNumber;
	document.querySelector('#contact_address').innerHTML=data.address;
	document.querySelector('#contact_webLink').innerHTML=data.websiteLink;
	document.querySelector('#contact_LinkedInLink').innerHTML=data.linkedInLink;
	document.querySelector('#contact_descreption').innerHTML=data.	description
;
	
	openContactModal();
	}catch{
		
	}
	
}


//delete contact
async function deleteContact(id){
	
	const swalWithBootstrapButtons = Swal.mixin({
	  customClass: {
		confirmButton: "px-6 py-2 bg-green-500 text-white font-semibold rounded-lg shadow-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-400",
		      cancelButton: "px-6 py-2 bg-red-500 text-white font-semibold rounded-lg shadow-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-400"
	  },
	  buttonsStyling: false
	});
	swalWithBootstrapButtons.fire({
	  title: "Are you sure?",
	  text: "You won't be able to revert this!",
	  icon: "warning",
	  showCancelButton: true,
	  confirmButtonText: "Yes, delete it!",
	  cancelButtonText: "No, cancel!",
	  reverseButtons: true
	}).then((result) => {
	  if (result.isConfirmed) {
		const url=`${baseURL}/user/contacts/delete/`+id;
		window.location.replace(url);
	    
	  } else if (
	    /* Read more about handling dismissals below */
	    result.dismiss === Swal.DismissReason.cancel
	  ) {
	    swalWithBootstrapButtons.fire({
	      title: "Cancelled",
	      text: "Your contact is safe :)",
	      icon: "error"
	    });
	  }
	});
	
}