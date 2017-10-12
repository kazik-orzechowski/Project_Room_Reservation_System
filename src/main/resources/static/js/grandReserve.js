function goback(){
history.back();
}
$( document ).on( 'click', '.nav-list li', function ( e ) {
    $( this ).addClass( 'active' ).siblings().removeClass( 'active' );
} );
