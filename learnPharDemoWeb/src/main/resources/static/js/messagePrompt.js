function messagePrompt(msg){
      $( function() {
          bootbox.alert({
             message: msg,
              locale:'ru'
           }).css({'font-weight' : 'bold',  'color': '#F00', 'font-size': '22px', 'font-weight' : 'bold'} );;
      });
}
