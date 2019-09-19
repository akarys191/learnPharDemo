
//Urls
 const $urlLinkListInventory = '/invoices/listInventory'

 //Editable key table classes
 const $tableEditClass = 'table-edit';
 const $tableRemoveClass = 'table-remove';
 const $tableSubmitClass = 'table-submit';
 const $tableEditRowClass = 'pt-3-half';
 const $editWarningClass = 'bg-warning'


 //General ids
 const $tableID = $('#table');
 const $BTN = $('#export-btn');
 const $EXPORT = $('#export');
 const $SUBMIT = $('#submitTableData');
 const $DIVCARD = $('#cardDiv');

// Global variables
 const $invoiceId = $('#invoiceId').val();
 const $totalPages = $('#totalPages').val();
//Language bundle values
 const $nextPaginationMessage = $("#nextPaginationMessage").val();
 const $prevPaginationMessage = $("#prevPaginationMessage").val();
 const $firstPaginationMessage = $("#firstPaginationMessage").val();
 const $lastPaginationMessage = $("#lastPaginationMessage").val();
 const $addRecordIsInProcessMessage = $("#addRecordIsInProcessMessage").val();

 var editFormNumber = 0;
 var editFormId = 'edit_form'
 var addFormId = 'add_form'

const newTr = `
<tr class="hide">
  <td class="pt-3-half"></td>
    <td class="pt-3-half" ></td>
    <td class="pt-3-half"></select>
    </td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half"></td>
    <td class="pt-3-half">
    <input type="hidden" name="inventoryId"/>
    <span class="table-up"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-up" aria-hidden="true"></i></a></span>
    <span class="table-down"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-down" aria-hidden="true"></i></a></span>
  </td>
  <td>
   <span class="table-edit"><button type="button"><i class="fas fa-edit"  style="font-size:20px;color:red" aria-hidden="true"></i></button></span>
   <span class="table-submit" style="display: none;"><button type="button"><i class="fa fa-paper-plane " style="color:red" aria-hidden="true"></i></button></span>
   <span class="table-remove" style="display: none;"><button type="button"><i class="fa fa-times-circle"  style="color:red" aria-hidden="true"></i></button></span>
  </td>
</tr> `;

$tableID.on('click', '.'+$tableSubmitClass, function(event)  {
    event.preventDefault();
    const $row = $(this).parents('tr');
    $submitButton = $row.find('.'+$tableSubmitClass);
    var formId = $submitButton.attr('form');
    $form = $('#'+formId);
    ajaxRequestPost($postInvoiceInventoryUrl, $form.serialize(), $tableID);
});

$tableID.on('click', '.'+$tableEditClass, function(event)  {
    event.preventDefault();
    const $row = $(this).parents('tr');
    $row.find('.'+$tableEditClass).hide();
    $row.find('.'+$tableRemoveClass).show();
    $submitButton = $row.find('.'+$tableSubmitClass);
    $submitButton.show();
	//make the whole row editable
	$row.find('.'+$tableEditRowClass)
	.attr('edit_type', 'button')
	.addClass($editWarningClass)
	.css('padding','3px')

    var invoiceIdInput = $('#invoiceId');
	var invoiceId = invoiceIdInput.val();
    var components = $('input[form="'+editFormId+'"]');
    if(components.length > 0){
       editFormNumber=editFormNumber+1;
       editFormId = 'edit_form' + editFormNumber;
       $DIVCARD.append('<form method="POST" id="'+editFormId+'"><input type="hidden" name="invoice" value="'+invoiceId+'"/></form>');
    } else {
       $DIVCARD.append('<input type="hidden" name="invoice" form="edit_form" value="'+invoiceId+'"/>');
    }

    $row.find("td:eq(1)").removeClass($editWarningClass);
	var medicineId = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineId]').val();
	var medicineName = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineName]').val();
	var pharmacistId = $(this).parents("tr").find("td:eq(7)").children('input').val();
	var supplierId = $(this).parents("tr").find("td:eq(2)").children('input').val();
	var price = $(this).parents("tr").find("td:eq(3)").text();
	var suppliedCost = $(this).parents("tr").find("td:eq(4)").text();
	var dateLocal = $(this).parents("tr").find("td:eq(5)").text();
	var quantity = $(this).parents("tr").find("td:eq(6)").text();
    console.log("dateLocal: "+dateLocal);
    var medicineNumber = editFormNumber+1;
    var medicineNameId = 'medicineName'+medicineNumber;
    var medicineIdId = 'medicineId'+medicineNumber ;
    $row.find("td:eq(0)").html('<input id="'+medicineNameId+'" medicineIdForm="'+medicineIdId+'" class="tableInput"/>');
    $row.find("td:eq(0)").append('<input id="'+medicineIdId+'" type="hidden" name="medicine" form="'+editFormId+'"/>');
    $row.find("td:eq(0)").children('input[id='+medicineNameId+']').val(medicineName);
    $row.find("td:eq(0)").children('input[id='+medicineIdId+']').val(medicineId);
    $row.find("td:eq(1)").text(invoiceId);
    $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(3)").html('<input class="tableInput" name="price" form="'+editFormId+'"/>');
    $row.find("td:eq(3)").children('input').val(price);
    $row.find("td:eq(4)").html('<input class="tableInput" name="suppliedCost" form="'+editFormId+'"/>');
    $row.find("td:eq(4)").children('input').val(suppliedCost);
    $row.find("td:eq(5)").html('<input class="tableInput" type="datetime-local" name="suppliedDate" form="'+editFormId+'"/>');
    $row.find("td:eq(5)").children('input').val(dateLocal);
    $row.find("td:eq(6)").html('<input class="tableInput" type="text" name="quantity" form="'+editFormId+'"/>');
    $row.find("td:eq(6)").children('input').val(quantity);
    $row.find("td:eq(7)").html('<select id="acceptingPharmacist"  name="acceptingPharmacist" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(8)").children('input').attr('form',''+editFormId);
    $submitButton.attr('form',editFormId);

    selectPharmacists($(this).parents("tr"), pharmacistId);
    selectSuppliers($(this).parents("tr"), supplierId);
 });

 $('.table-add').on('click', 'i', () => {
    addNewTr(null);
 });

 $tableID.on('click', '.'+$tableRemoveClass, function () {
     event.preventDefault();
     const $row = $(this).parents('tr');
     $removeButton = $row.find('.'+$tableRemoveClass);
     var inventoryId = $row.find('input[name=inventoryId]').val();
     ajaxRequestDelete($postInvoiceInventoryUrl+"/"+inventoryId, $tableID);
     $row.find('input[name=inventoryId]').attr('form',null);
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

  function addNewTr(medicine){
       const $rowLast =$('#table tr:last');
       var addFormIdTest = $rowLast.find('input[name=inventoryId]').attr('form');
       console.log('addFormIdTest');
       console.log(addFormIdTest);
       if(addFormIdTest == addFormId){
          messagePrompt($addRecordIsInProcessMessage);
         return;
       }
       $tBody = $('tbody');
       $tBody.append(newTr);
       const $row =$('#table tr:last');
       $row.find('.'+$tableEditClass).hide();

       $submitButton = $row.find('.'+$tableSubmitClass);
       $submitButton.show();
       $row.find('.'+$tableRemoveClass).show();
       //make the whole row editable
       $row.find('.'+$tableEditRowClass)
       	.attr('edit_type', 'button')
       	.addClass($editWarningClass)
       	.css('padding','3px');

        var invoiceIdInput = $('#invoiceId');
        var invoiceId = invoiceIdInput.val();
        console.log(invoiceId);
        var medicineName = 'medicineName'+0;
        var medicineId = 'medicineId'+0;
        var medicineIdForm = 'medicineId'+0;
        var currentIsoDate = getIsoDate(new Date());
        console.log('currentIsoDate: '+currentIsoDate);
        $row.find("td:eq(1)").removeClass($editWarningClass);
        $row.find("td:eq(0)").html('<input id="'+medicineName+'" medicineIdForm="'+medicineIdForm+'" class="tableInput"/>');
        $row.find("td:eq(0)").append('<input id="'+medicineId+'" type="hidden" name="medicine" form="'+addFormId+'"/>');
        if(medicine != null){
           $row.find("td:eq(0)").children('input[id='+medicineId+']').val(medicine.id);
           $row.find("td:eq(0)").children('input[id="'+medicineName+'"]').val(medicine.name);
        }
        $row.find("td:eq(1)").text(invoiceId);
        $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="'+addFormId+'" class="tableSelect"></select>');
        $row.find("td:eq(3)").html('<input class="tableInput" name="price" form="'+addFormId+'"/>');
        $row.find("td:eq(4)").html('<input class="tableInput" name="suppliedCost" form="'+addFormId+'"/>');
        $row.find("td:eq(5)").html('<input class="tableInput" type="datetime-local" name="suppliedDate" form="'+addFormId+'"/>');
        $row.find("td:eq(5)").children('input').val(currentIsoDate);
        $row.find("td:eq(6)").html('<input class="tableInput" type="text" name="quantity" form="'+addFormId+'"/>');
        var $rowCol7 =  $row.find("td:eq(7)");
        if(acceptingPharmacistRef.roles.includes("ADMIN")){
            console.log('acceptingPharmacistRef');
            console.log(acceptingPharmacistRef);
            $rowCol7.html('<select id="acceptingPharmacist"  name="acceptingPharmacist" form="'+addFormId+'" class="tableSelect"></select>');
            selectPharmacists($rowCol7.parents("tr"), acceptingPharmacistRef.id);
         } else {
            $rowCol7.removeClass($editWarningClass);
            $rowCol7.text(acceptingPharmacistRef.name);
            $rowCol7.append('<input id="'+medicineId+'" type="hidden" name="acceptingPharmacist" form="'+addFormId+'"/>');
            $rowCol7.children('input').val(acceptingPharmacistRef.id);
         }
         $row.find("td:eq(8)").children('input').attr('form',''+addFormId);
         $row.find('input[name=inventoryId]').attr('form',addFormId)
         $DIVCARD.append('<input type="hidden" name="invoice" form="'+addFormId+'" value="'+invoiceId+'"/>');
         $submitButton.attr('form',addFormId);
         selectSuppliers($('#table tr:last'), null);
  }

  $('#pagination-demo').twbsPagination({
      totalPages: $totalPages == 0 ? 1 : $totalPages,
      visiblePages: $totalPages == 0 ? 1 : $totalPages%5,
      next: $nextPaginationMessage,
      prev: $prevPaginationMessage,
      first: $firstPaginationMessage,
      last: $lastPaginationMessage,
      onPageClick: function (event, page) {
        var urlLink = $urlLinkListInventory+'?page='+page+'&invoiceId='+$invoiceId;
        ajaxRequestGet(urlLink, $tableID);
      }
   });