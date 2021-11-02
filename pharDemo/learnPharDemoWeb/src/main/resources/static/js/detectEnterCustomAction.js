$(document).ready(function() {
  $(window).keydown(function(event){
    if(event.keyCode == 13) {
    console.log('event 13');
    console.log(event);
    console.log(event.target);
         if(event.target.id == "tagQuery"){
          event.preventDefault();
         var val = $("#tagQuery").val();
         $.ajax({
                 dataType: "json",
                 url: $medicineBarcodeUrl,
                  data: {term: val},
                 success:function(data) {
                       $("#tagQuery").val(data.name);
                       $("#medicineIdInput").val(data.id);
                  },
                  error:function(data) {
                     messagePrompt(data+': '+$medicineDoesNotExistMessage);
                  }
          });
          } else if(event.target.id == "barCode"){
                 event.preventDefault();
                 var val = $("#barCode").val();
                 $.ajax({
                      dataType: "json",
                      url:$medicineBarcodeUrl,
                      data: {term: val},
                      success:function(data) {
                          messagePrompt($medicineAlreadyExistMessage);
                          $("#addMedicineBtn").attr("disabled", true);
                      },
                      error:function(data) {
                            $("#barCode").val(val);
                            $("#addMedicineBtn").attr("disabled", false);
                            console.log($medicineDoesNotExistMessage)
                      }
                  });
          }  else if(event.target.id.includes("medicineName")){
                        event.preventDefault();
                        var eventTargetId = event.target.id;
                           var barCodeMedicine = $('#'+event.target.id);
                           console.log(event.target.id);
                           var barCodeMedicineVal = barCodeMedicine.val();
                           var medicineIdForm = barCodeMedicine.attr('medicineIdForm');
                           var barCodeMedicineId = $('#'+medicineIdForm);
                           $.ajax({
                               dataType: "json",
                               url:$medicineBarcodeUrl,
                               data: {term: barCodeMedicineVal},
                                     success:function(data) {
                                     console.log(data);
                                     barCodeMedicine.val(data.name);
                                     barCodeMedicineId.val(data.id);
                                     if(eventTargetId.includes("ForCash")){
                                        findSetMedicinePrice(barCodeMedicine);
                                     }
                                },
                                 error:function(error) {
                                     console.log(error);
                                     barCodeMedicine.val($medicineDoesNotExistMessage);
                                     messagePrompt($medicineDoesNotExistMessage);
                                  }
                             });
                   }
                   else if(event.target.id.includes("markupPercentageInput") || event.target.id.includes("suppliedCostInput")){
                       event.preventDefault();
                       var number = $('#'+event.target.id).attr('number');
                       var suppliedCostInput = $("#suppliedCostInput"+number).val();
                       var markupPercentageInput = $("#markupPercentageInput"+number).val();

                       if(suppliedCostInput==null || suppliedCostInput == ''){
                             messagePrompt($suppliedCostInputEmptyMessage);
                             return ;
                       } else if(markupPercentageInput==null || markupPercentageInput == ''){
                             messagePrompt($markupPercentageInputEmptyMessage);
                             return ;
                        }

                        var $calcPrice = calculatePrice(markupPercentageInput, suppliedCostInput);
                        $("#priceInput"+number).val($calcPrice);
                        $("#pPrice"+number).text($calcPrice);

                 }  else if(event.target.id.includes("saleQuantityInput")){
                        event.preventDefault();
                        var number = $('#'+event.target.id).attr('number');
                        var saleQuantityInput = $("#saleQuantityInput"+number).val();
                        var priceInput = $("#priceInput"+number).val();
                        if(saleQuantityInput==null || saleQuantityInput == ''){
                            saleQuantityInput = 0;
                        } else if(priceInput==null || priceInput == ''){
                             messagePrompt($notFoundPriceException);
                             return ;
                        }
                         var medicineId = $('#medicineId'+number);
                         var medicineIdVal = medicineId.val();
                         $.ajax({
                            dataType: "json",
                            url:$medicineQuantityUrl,
                            data: {medicineId: medicineIdVal},
                              success:function(data) {
                                  if(nonEmpty(data)){
                                     if(checkQuantity(data, saleQuantityInput )){
                                       $("#soldSum"+number).val(saleQuantityInput*priceInput);
                                       $('#pSoldSum'+number).text(saleQuantityInput*priceInput);
                                     } else {
                                         messagePrompt($saleQuantityExceedsMessage);
                                      }
                                   } else {
                                        messagePrompt($saleQuantityExceedsMessage);
                                   }
                              },
                              error:function(error) {
                                  console.log(error);
                                  messagePrompt($medicineDoesNotExistInventoryMessage);
                              }
                          });
                  }
                return false;
    }
  });
});