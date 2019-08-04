function messagePrompt(msg){
      $( function() {
          bootbox.alert({
             message: msg,
              size: 'small',
              locale:'ru'
           });
      });
}
