autoLoading();

function autoLoading() {
	const url_sidebar = "http://localhost:8080/fragment/sidebar";

	fetch(url_sidebar)
		.then((response) => {
			
			(function()
					{
					  if( window.localStorage )
					  {
					    if( !localStorage.getItem('firstLoad') )
					    {
					      localStorage['firstLoad'] = true;
					      window.location.reload();
					    }  
					    else
					      localStorage.removeItem('firstLoad');
					  }
					})();

			
		})
		.catch((error) => console.log(error));	

}