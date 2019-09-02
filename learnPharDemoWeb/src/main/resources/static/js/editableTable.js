 const $tableID = $('#table');
 const $BTN = $('#export-btn');
 const $EXPORT = $('#export');
 const $SUBMIT = $('#submitTableData');

 const newTr = `
<tr class="hide">
  <td class="pt-3-half"></td>
    <td class="pt-3-half" ></td>
    <td class="pt-3-half">
        <select id="selectSupplier" class="tableSelect"></select>
    </td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"><input type="datetime-local" id="dateLocal"/></td>
    <td class="pt-3-half"><input type="text" name="quantity"/></td>
    <td class="pt-3-half">
       <select id="acceptingPharmacist" class="tableSelect"></select>
    </td>
    <td class="pt-3-half">
    <span class="table-up"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-up" aria-hidden="true"></i></a></span>
    <span class="table-down"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-down" aria-hidden="true"></i></a></span>
  </td>
  <td>
   <span class="table-edit"><button type="button"><i class="fas fa-edit"  style="font-size:20px;color:red" aria-hidden="true"></i></button></span>
   <span class="table-submit" style="display: none;"><button type="button"><i class="fa fa-paper-plane " style="color:red" aria-hidden="true"></i></button></span>
   <span class="table-remove" style="display: none;"><button type="button"><i class="fa fa-times-circle"  style="color:red" aria-hidden="true"></i></button></span>
  </td>
</tr> `;

$( function() {
     $("#datepicker" ).datepicker();
} );

$tableID.on('click', '.table-submit', function(event)  {
    event.preventDefault();
    const $row = $(this).parents('tr');
    $form = $('#edit_form');
    console.log('edit_form:::');
    console.log($form);
    ajaxRequestPost('/invoices/inventory', $form.serialize(), $tableID)

 });

$tableID.on('click', '.table-edit', function(event)  {
    event.preventDefault();

    const $row = $(this).parents('tr');
    $row.find('.table-edit').hide();
    $row.find('.table-remove').show();
    $row.find('.table-submit').show();
	//make the whole row editable
	$row.find('.pt-3-half')
	.attr('contenteditable', 'true')
	.attr('edit_type', 'button')
	.addClass('bg-warning')
	.css('padding','3px')

    console.log($('#edit_form'));

	var medicineId = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineId]').val();
	var medicineName = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineName]').val();
	var pharmacistId = $(this).parents("tr").find("td:eq(7)").children('input').val();
	var supplierId = $(this).parents("tr").find("td:eq(2)").children('input').val();
	var invoiceId = $(this).parents("tr").find("td:eq(1)").text();
	var price = $(this).parents("tr").find("td:eq(3)").text();
	var suppliedCost = $(this).parents("tr").find("td:eq(4)").text();
	var dateLocal = $(this).parents("tr").find("td:eq(5)").text();
	var quantity = $(this).parents("tr").find("td:eq(6)").text();

    $row.find("td:eq(0)").html('<input id="medicineName" class="tableInput"/>');
    $row.find("td:eq(0)").append('<input id="medicineId" type="hidden" name="medicine" form="edit_form"/>');
    $row.find("td:eq(0)").children('input[id=medicineName]').val(medicineName);
    $row.find("td:eq(0)").children('input[id=medicineId]').val(medicineId);
    $row.find("td:eq(1)").html('<input class="tableInput" name="invoice" form="edit_form"/>');
    $row.find("td:eq(1)").children('input').val(invoiceId);
    $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="edit_form" class="tableSelect"></select>');
    $row.find("td:eq(3)").html('<input class="tableInput" name="price" form="edit_form"/>');
    $row.find("td:eq(3)").children('input').val(price);
    $row.find("td:eq(4)").html('<input class="tableInput" name="suppliedCost" form="edit_form"/>');
    $row.find("td:eq(4)").children('input').val(suppliedCost);
    $row.find("td:eq(5)").html('<input class="tableInput" type="datetime-local" name="suppliedDate" form="edit_form"/>');
    $row.find("td:eq(5)").children('input').val(dateLocal);
    $row.find("td:eq(6)").html('<input class="tableInput" type="text" name="quantity" form="edit_form"/>');
    $row.find("td:eq(6)").children('input').val(quantity);
    $row.find("td:eq(8)").children('input').attr('form','edit_form');

    $row.find("td:eq(7)").html('<select id="acceptingPharmacist"  name="acceptingPharmacist" form="edit_form" class="tableSelect"></select>');

    selectPharmacists($(this).parents("tr"), pharmacistId);
    selectSuppliers($(this).parents("tr"), supplierId);

 });

 $('.table-add').on('click', 'i', () => {

    $tBody = $('tbody');
    $tBody.append(newTr);
    const $row =$('#table tr:last');
    $row.find('.table-edit').hide();

   	//make the whole row editable
   	$row.find('.pt-3-half')
   	.attr('contenteditable', 'true')
   	.attr('edit_type', 'button')
   	.addClass('bg-warning')
   	.css('padding','3px')

     selectPharmacists($('#table tr:last'), null);
     selectSuppliers($('#table tr:last'), null);
 });

 $tableID.on('click', '.table-remove', function () {

 });

 $tableID.on('click', '.table-up', function () {
   const $row = $(this).parents('tr');
   if ($row.index() === 1) {
     return;
   }
   $row.prev().before($row.get(0));
 });

 $tableID.on('click', '.table-down', function () {
   const $row = $(this).parents('tr');
   $row.next().after($row.get(0));
 });

 // A few jQuery helpers for exporting only
 jQuery.fn.pop = [].pop;
 jQuery.fn.shift = [].shift;

 $BTN.on('click', () => {
   const $rows = $tableID.find('tr:not(:hidden)');
   const headers = [];
   const data = [];

   // Get the headers (add special header logic here)
   $($rows.shift()).find('th:not(:empty)').each(function () {
     headers.push($(this).text().toLowerCase());
   });

   // Turn all existing rows into a loopable array
   $rows.each(function () {
     const $td = $(this).find('td');
     const h = {};

     // Use the headers from earlier to name our hash keys
     headers.forEach((header, i) => {
       h[header] = $td.eq(i).text();
     });
     data.push(h);
   });
   // Output the result
   $EXPORT.text(JSON.stringify(data));
 });

   function selectPharmacists(component, defaultSelectId){
      selectOptions(component, "acceptingPharmacist" ,"/rest/pharmacists/all",{},defaultSelectId);
   }

   function selectSuppliers(component, defaultSelectId){
       selectOptions(component, "selectSupplier", "/rest/suppliers/all",{},defaultSelectId);
   }

  function selectOptions(component, targetComponentId, url, params, defaultSelectId){
        var items = "";
        var itemsJson = syncAjaxRequest(url,params);
        $.each(itemsJson ,function(index,item){
                if(defaultSelectId == item.id){
                   items+="<option selected value='"+item.id+"'>"+item.name+"</option>";
                } else {
                   items+="<option value='"+item.id+"'>"+item.name+"</option>";
                }
                component.find("#"+targetComponentId).html(items);
         });
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

  function syncAjaxRequest(urlLink, params){
    var theResponse = null;
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
