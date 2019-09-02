$(document).ready(function() {
  $(window).keydown(function(event){
    if(event.keyCode == 13) {
        if(event.target.id == "tagQuery"){
         var val = $("#tagQuery").val();
         $.ajax({
                  dataType: "json",
                 url: "/medicines/findByBarcode",
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
                 var val = $("#barCode").val();
                 $.ajax({
                      dataType: "json",
                      url: "/medicines/findByBarcode",
                      data: {term: val},
                      success:function(data) {
                              messagePrompt('Такое лекарство уже существует!');
                      },
                      error:function(data) {
                            $("#barCode").val(val);
                             console.log('Лекарство не найдено')
                      }
                  });
          } else if(event.target.id == "medicineName"){
                  var barCodeMedicine = $("#medicineName");
                  var barCodeMedicineVal = $("#medicineName").val();
                  var barCodeMedicineId = $("#medicineId");
                  $.ajax({
                       dataType: "json",
                       url: "/medicines/findByBarcode",
                       data: {term: barCodeMedicineVal},
                           success:function(data) {
                               console.log(data);
                               barCodeMedicine.val(data.name);
                               barCodeMedicineId.val(data.id);
                           },
                           error:function(error) {
                               barCodeMedicine.val("Unknown");
                               console.log(error);
                           }
                       });
                      }
       return false;
    }
  });
});