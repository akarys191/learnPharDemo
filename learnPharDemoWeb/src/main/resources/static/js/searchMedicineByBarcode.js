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
                 url:$medicineBarcodeUrl,
                  data: {term: val},
                 success:function(data) {
                       $("#tagQuery").val(data.name);
                       $("#medicineIdInput").val(data.id);
                  },
                  error:function(data) {
                     alert(data+' не найдено в базе ');
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
                              messagePrompt('Такое лекарство уже существует!');
                      },
                      error:function(data) {
                            $("#barCode").val(val);
                             console.log('Лекарство не найдено')
                      }
                  });
          } else if(event.target.id.lastIndexOf("medicineName") == 0){
                  event.preventDefault();
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
                           },
                           error:function(error) {
                               console.log(error);
                               barCodeMedicine.val('Не найдено!');
                               messagePrompt('Такая лекарства не найдено!');
                           }
                       });
                      }
       return false;
    }
  });
});