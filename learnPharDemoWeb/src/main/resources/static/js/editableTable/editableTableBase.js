 //key get Urls
 const $medicineBarcodeUrl =  "/medicines/findByBarcode";
 const $allPharmacistsUrl = "/rest/pharmacists/all";
 const $acceptingPharmUserUrl = "/rest/pharmacists/currentPharmUser";
 const $allSuppliersUrl = "/rest/suppliers/all";
 const $postInvoiceInventoryUrl = "/invoices/inventory"


//Catalogues
var pharmacistsRef= {json : null };
var suppliersRef={json : null };
var acceptingPharmacistRef;


//Ajax queries
$( window ).on("load", function() {
    selectPharmacists(null, pharmacistsRef, null);
    selectSuppliers(null, suppliersRef, null);
    acceptingPharmacistRef = syncAjaxRequest($acceptingPharmUserUrl, {});
});



  function getIsoDate(date){
     return date.getFullYear()+'-'+attachZeroIfNecessary(date.getMonth())+'-'+attachZeroIfNecessary(date.getDate())+'T'+attachZeroIfNecessary(date.getHours())+':'+attachZeroIfNecessary(date.getMinutes())+':'+attachZeroIfNecessary(date.getSeconds());
  }

  function attachZeroIfNecessary(time){
     var timeString = time+'';
     if(timeString.length == 1){
        if(!timeString.includes('0'))
           return '0'+time;
     }
     return time;
  }

  function selectPharmacists(component, defaultSelectId){
      selectOptions(component, pharmacistsRef,  "acceptingPharmacist" ,$allPharmacistsUrl,{},defaultSelectId);
   }

  function selectSuppliers(component, defaultSelectId){
       selectOptions(component, suppliersRef, "selectSupplier", $allSuppliersUrl,{},defaultSelectId);
  }

  function selectOptions(component, fillItems,  targetComponentId, url, params, defaultSelectId){
        var items = "";
        if(fillItems.json == null){
            fillItems.json = syncAjaxRequest(url,params);
        }
        if(component != null){
            $.each(fillItems.json ,function(index,item){
                        console.log(fillItems.json);
                        console.log(component);
                        console.log(targetComponentId);

                    if(defaultSelectId == item.id){
                       items+="<option selected value='"+item.id+"'>"+item.name+"</option>";
                    } else {
                       items+="<option value='"+item.id+"'>"+item.name+"</option>";
                    }
                    component.find("#"+targetComponentId).html(items);
             });
         }
  }
  function ajaxRequestPost(urlLink, data, divComponent){
    $.ajax({
       url: urlLink,
       type: 'post',
       data: data,
       success: function(data){
           console.log( data );
           divComponent.html( data );
        },
        error: function( jqXhr, textStatus, errorThrown ){
           console.log(textStatus);
        }
     });
  }

  function ajaxRequestGet(urlLink, divComponent){
      $.ajax({
         url: urlLink,
         type: 'GET',
         success: function(data){
             divComponent.html( data );
          },
          error: function( jqXhr, textStatus, errorThrown ){
             console.log(textStatus);
          }
       });
      }

    function ajaxRequestDelete(urlLink, $component){
        $.ajax({
           url: urlLink,
           type: 'delete',
           success: function(data){
               console.log('deleted row');
               console.log(data);
               $component.parents('tr').detach();
            },
            error: function( jqXhr, textStatus, errorThrown ){
               console.log(textStatus);
               messagePrompt(textStatus);
            }
         });
        }

  function syncAjaxRequest(urlLink, params){
    var theResponse = null;
    console.log("urlLink::: ");
    console.log(urlLink);
    console.log(params);
    $.ajax({
           url: urlLink,
           async: false,
           data: params,
           dataType: "json",
           success: function (json) {
             theResponse = json;
           },
           error:function (xhr) {
             messagePrompt('Ошибка: '+ xhr.statusText);
           }
    });
    return theResponse;
  }
  $(document).scannerDetection({
  	timeBeforeScanTest: 200, // wait for the next character for upto 200ms
  	//startChar: [120], // Prefix character for the cabled scanner (OPL6845R)
  	//endChar: [13], // be sure the scan is complete if key 13 (enter) is detected
  	avgTimeByChar: 30, // it's not a barcode if a character takes longer than 40ms
  	onComplete: function(barcode, qty){
  	    var medicine = syncAjaxRequest($medicineBarcodeUrl, {term:barcode});
  	    if(medicine == null)
  	    {
  	         messagePrompt('Лекарство не найдено!');
  	         return;
  	    }
  	    console.log("medicine: ");
  	    console.log(barcode);
  	    console.log(medicine);
  	   	addNewTr(medicine);
  	} // main callback function
  });
