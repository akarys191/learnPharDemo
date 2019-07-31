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

          } else if(event.target.id == "medQuery"){
                 var val = $("#medQuery").val();
                 $.ajax({
                          dataType: "json",
                         url: "/medicines/findByBarcode",
                          data: {term: val},
                         success:function(data) {
                               $("#medQuery").val('Лекарство с таким номером уже существует.');
                          },
                          error:function(data) {
                            $("#medQuery").val(val);
                             console.log('Лекарство не найдено')
                          }
                  });
          }
       return false;
    }
  });
});