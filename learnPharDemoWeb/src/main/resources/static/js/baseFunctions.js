function loadScript(url, callback)
{
    // Adding the script tag to the head as suggested before
    var head = document.head;
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;
    // Then bind the event to the callback function.
    // There are several events for cross browser compatibility.
    script.onreadystatechange = callback;
    script.onload = callback;
    // Fire the loading
    head.appendChild(script);
}
function messagePrompt(msg){
      $( function() {
          bootbox.alert({
             message: msg,
             /* size: 'small',*/
              locale:'ru'
           }).css({'font-weight' : 'bold',  'color': '#F00', 'font-size': '22px', 'font-weight' : 'bold'} );;
      });
}

function calculatePrice(markupPercentage, suppliedCost){
    var markupPercentageNum = parseFloat(markupPercentage);
    var suppliedCostNum = parseFloat(suppliedCost);
    return ((markupPercentageNum*(0.01)*suppliedCostNum)+suppliedCostNum);
}

function checkQuantity(totalQuantity, checkQuantity){
     var checkQuantityNum = parseFloat(checkQuantity);
     var totalQuantityNum = parseFloat(totalQuantity);
     return checkQuantityNum <= totalQuantityNum;
}

function isEmpty(value){
   return value == null || value =='' || typeof value == 'undefined' || value == 'null';
}

function nonEmpty(value){
console.log('nonEmpty value');
console.log(value);
    return value != null && value != '' && typeof value != 'undefined' && value != 'null';
}

