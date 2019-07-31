 $(document).ready(function() {
     //attach autocomplete
    $("#tagQuery").autocomplete({
        minLength: 1,
        delay: 500,
        //define callback to format results
        source: function (request, response) {
            $.getJSON("/medicines/findByName", request, function(result) {
                response($.map(result, function(item) {
                    return {
                        // following property gets displayed in drop down
                        label: item.name + "(" + item.id + ")",
                        // following property gets entered in the textbox
                        value: item.name,
                        // following property is added for our own use
                        id: item.id
                    }
                }));
            });

        },
        //define select handler
        select : function(event, ui) {
            if (ui.item) {
                console.log(ui.item);
                //event.preventDefault();
                $("#selected_tags span").append('<a href=" + ui.item.tag_url + " target="_blank">'+ ui.item.label +'</a>');
                //$("#tagQuery").value = $("#tagQuery").defaultValue
                var defValue = $("#tagQuery").prop('defaultValue');
                $("#tagQuery").val(ui.item.value);
                $("#medicineIdInput").val(ui.item.id);
                console.log($("#medicineIdInput").value);
                $("#tagQuery").blur();
                return false;
            }
        }
    });
});