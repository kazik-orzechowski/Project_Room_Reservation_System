$( document ).ready(
		
	function goback(){
	history.back();
	}
	
	$( document ).on( 'click', '.nav-list li', function ( e ) {
	    $( this ).addClass( 'active' ).siblings().removeClass( 'active' );
	});
	
	function() {
		 $('.timepicker').timepicker({
		      timeFormat: 'HH:mm',
		      interval: 60,
		      minTime: '8',
		      maxTime: '22:00',
		      defaultTime: '8',
		      startTime: '8:00',
		      dynamic: true,
		      dropdown: true,
		      scrollbar: true
		  });
		}
	$( function() {
	   $( "#dateStart" ).datepicker();
	 } );
);


