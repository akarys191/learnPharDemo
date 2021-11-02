//Catalogues
var pharmacistsRef= {json : null };
var suppliersRef={json : null };
var customersRef={json : null };
var cashTypesRef={json : null };
var currentPharmacistRef;

//Ajax queries
$( window ).on("load", function() {
    selectPharmacists(null, pharmacistsRef, null);
    selectSuppliers(null, suppliersRef, null);
    selectCustomers(null, customersRef, null);
    selectCashTypes(null, cashTypesRef, null);
    currentPharmacistRef = syncAjaxRequest($currentPharmUserUrl, {});
});


  function getIsoDate(date){
     return date.getFullYear()+'-'+attachZeroIfNecessary(date.getMonth())+'-'+attachZeroIfNecessary(date.getDate())+'T'+attachZeroIfNecessary(date.getHours())+':'+attachZeroIfNecessary(date.getMinutes())+':'+attachZeroIfNecessary(date.getSeconds());
  }

  function attachZeroIfNecessary(time){
     var timeString = time+'';
     if(timeString.length == 1){
           return '0'+time;
     }
     return time;
  }

  function selectPharmacists(component, defaultSelectId){
      selectOptions(component, pharmacistsRef,  "pharmacist" ,$allPharmacistsUrl,{},defaultSelectId);
   }

  function selectSuppliers(component, defaultSelectId){
       selectOptions(component, suppliersRef, "selectSupplier", $allSuppliersUrl,{},defaultSelectId);
  }

  function selectCustomers(component, defaultSelectId){
       selectOptions(component, customersRef, "selectCustomer", $allCustomersUrl,{},defaultSelectId);
  }

  function selectCashTypes(component, defaultSelect){
       selectOptions(component, cashTypesRef, "selectCashType", $allCashTypesUrl,{},defaultSelect);
  }

  function selectOptions(component, fillItems,  targetComponentId, url, params, defaultSelect){
        var items = "";
        if(fillItems.json == null){
            fillItems.json = syncAjaxRequest(url,params);
        }
        if(component != null){
            $.each(fillItems.json ,function(index,item){
                        console.log(fillItems.json);
                        console.log(component);
                        console.log(targetComponentId);

                    if(item.id == undefined || item.id == null || item.id == "undefined" || item.id == ""){
                        if(defaultSelect == item){
                            items+="<option selected value='"+item+"'>"+item+"</option>";
                         } else {
                           items+="<option value='"+item+"'>"+item+"</option>";
                        }
                    }
                    else{
                        if(defaultSelect == item.id){
                             items+="<option selected value='"+item.id+"'>"+item.name+"</option>";
                        } else {
                             items+="<option value='"+item.id+"'>"+item.name+"</option>";
                        }
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
           divComponent.html( data );
        },
        error: function(error){
           console.log(error);
           if(nonEmpty(error.responseText)){
              messagePrompt(error.responseText);
           }
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

  function ajaxRequestDelete(urlLink, divComponent){
        $.ajax({
           url: urlLink,
           type: 'delete',
           success: function(data){
               console.log('deleted row');
               divComponent.html( data );
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
           console.log(json);
             theResponse = json;
           },
           error:function (xhr) {
           console.log(xhr);
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
