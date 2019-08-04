window.messagePrompt = function( msg, title, btnText ){
      $( function() {
        var domToBeAddedID = 'my-dialog-confirm';
        $('#' + domToBeAddedID).remove();

        var domToBeAdded = '<div id="'+domToBeAddedID+'" title="'+title+'">' +
            +'<p>'
            + msg
            +'</p>'
        +'</div>';

        $('body').append( domToBeAdded );

        $( "#dialog-confirm" ).dialog({
          resizable: false,
          height: "auto",
          width: 400,
          modal: true,
          buttons: {
            "Ok": function() {
              $('#' + domToBeAddedID).remove();
              $( this ).dialog( "close" );
            },
            Cancel: function() {
              $('#' + domToBeAddedID).remove();
              $( this ).dialog( "close" );
            }
          }
        });
      } );
}
