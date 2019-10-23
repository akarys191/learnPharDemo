
 //Editable key table classes
 const $tableAddClass = 'table-add';
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
 const $markupPercentage = $('#markupPercentage').val();


 $('#pagination-inventory').twbsPagination({
      totalPages: $totalPages == 0 ? 1 : $totalPages,
      visiblePages: $totalPages == 0 ? 1 : $totalPages%5,
      next: $nextPaginationMessage,
      prev: $prevPaginationMessage,
      first: $firstPaginationMessage,
      last: $lastPaginationMessage,
      initiateStartPageClick: false,
      onPageClick: function (event, page) {
        var urlLink = $urlLinklistInvoiceInventoryItems+'?page='+page+'&invoiceId='+$invoiceId;
        $tableID.load(urlLink);
      }
 });

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
    <td class="pt-3-half"></td>
    <td class="pt-3-half">
    <input type="hidden" name="invoiceInventoryItemId"/>
    <span class="table-up"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-up" aria-hidden="true"></i></a></span>
    <span class="table-down"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-down" aria-hidden="true"></i></a></span>
  </td>
  <td>
   <span class="table-edit"><button type="button"><i class="fas fa-edit"  style="font-size:20px;color:red" aria-hidden="true"></i></button></span>
   <span class="table-submit" style="display: none;"><button type="button"><i class="fa fa-paper-plane " style="color:red" aria-hidden="true"></i></button></span>
   <span class="table-remove" style="display: none;"><button type="button"><i class="fa fa-times-circle"  style="color:red" aria-hidden="true"></i></button></span>
  </td>
</tr> `;

  function addNewTr(medicine){
       const $rowLast =$('#table tr:last');
       var addFormIdTest = $rowLast.find('input[name=invoiceInventoryItemId]').attr('form');
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
        var priceInputId = 'priceInput'+0;
        var markupPercentageInputId = 'markupPercentageInput'+0;
        var suppliedCostInputId = 'suppliedCostInput'+0;
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
        $row.find("td:eq(3)").html('<input id="'+priceInputId+'" class="tableInput" name="price" form="'+addFormId+'"/>');
        $row.find("td:eq(4)").html('<input id="'+markupPercentageInputId+'" class="tableInput" name="markupPercentage" form="'+addFormId+'"/>');
        $row.find("td:eq(4)").children('input').val($markupPercentage);
        $row.find("td:eq(4)").children('input').attr('number',0);
        $row.find("td:eq(5)").html('<input id="'+suppliedCostInputId+'" class="tableInput" name="suppliedCost" form="'+addFormId+'"/>');
        $row.find("td:eq(5)").children('input').attr('number',0);
        $row.find("td:eq(6)").html('<input class="tableInput" type="datetime-local" name="suppliedDate" form="'+addFormId+'"/>');
        $row.find("td:eq(6)").children('input').val(currentIsoDate);
        $row.find("td:eq(7)").html('<input class="tableInput" type="text" name="quantity" form="'+addFormId+'"/>');
        var $rowCol8 =  $row.find("td:eq(8)");
        if(currentPharmacistRef.roles.includes("ADMIN")){
            console.log('currentPharmacistRef');
            console.log(currentPharmacistRef);
            $rowCol8.html('<select id="pharmacist"  name="acceptingPharmacist" form="'+addFormId+'" class="tableSelect"></select>');
            selectPharmacists($rowCol8.parents("tr"), currentPharmacistRef.id);
         } else {
            $rowCol8.removeClass($editWarningClass);
            $rowCol8.text(currentPharmacistRef.name);
            $rowCol8.append('<input id="'+medicineId+'" type="hidden" name="acceptingPharmacist" form="'+addFormId+'"/>');
            $rowCol8.children('input').val(currentPharmacistRef.id);
         }
         $row.find("td:eq(9)").children('input').attr('form',''+addFormId);
         $row.find('input[name=invoiceInventoryItemId]').attr('form',addFormId)
         $DIVCARD.append('<input type="hidden" name="invoice" form="'+addFormId+'" value="'+invoiceId+'"/>');
         $submitButton.attr('form',addFormId);
         selectSuppliers($('#table tr:last'), null);
 }

$tableID.on('click', '.'+$tableSubmitClass, function(event)  {
    event.preventDefault();
    const $row = $(this).parents('tr');
    $submitButton = $row.find('.'+$tableSubmitClass);
    var formId = $submitButton.attr('form');
    $form = $('#'+formId);
    console.log();
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
	var pharmacistId = $(this).parents("tr").find("td:eq(8)").children('input').val();
	var supplierId = $(this).parents("tr").find("td:eq(2)").children('input').val();
	var price = $(this).parents("tr").find("td:eq(3)").text();
	var markupPercentage = $(this).parents("tr").find("td:eq(4)").text();
	var suppliedCost = $(this).parents("tr").find("td:eq(5)").text();
	var dateLocal = $(this).parents("tr").find("td:eq(6)").text();
	var quantity = $(this).parents("tr").find("td:eq(7)").text();
    console.log("dateLocal: "+dateLocal);
    var medicineNumber = editFormNumber+1;
    var medicineNameId = 'medicineName'+medicineNumber;
    var medicineIdId = 'medicineId'+medicineNumber ;
    var markupPercentageInputId = 'markupPercentageInput'+medicineNumber ;
    var priceInputId = 'priceInput'+medicineNumber ;
    var suppliedCostInputId = 'suppliedCostInput'+medicineNumber ;
    $row.find("td:eq(0)").html('<input id="'+medicineNameId+'" medicineIdForm="'+medicineIdId+'" class="tableInput"/>');
    $row.find("td:eq(0)").append('<input id="'+medicineIdId+'" type="hidden" name="medicine" form="'+editFormId+'"/>');
    $row.find("td:eq(0)").children('input[id='+medicineNameId+']').val(medicineName);
    $row.find("td:eq(0)").children('input[id='+medicineIdId+']').val(medicineId);
    $row.find("td:eq(1)").text(invoiceId);
    $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(3)").html('<input id="'+priceInputId+'" class="tableInput" name="price" form="'+editFormId+'"/>');
    $row.find("td:eq(3)").children('input').val(price);
    $row.find("td:eq(4)").html('<input id="'+markupPercentageInputId+'" class="tableInput" name="markupPercentage" form="'+editFormId+'"/>');
    $row.find("td:eq(4)").children('input').val(markupPercentage);
    $row.find("td:eq(4)").children('input').attr('number',medicineNumber);
    $row.find("td:eq(5)").html('<input id="'+suppliedCostInputId+'" class="tableInput" name="suppliedCost" form="'+editFormId+'"/>');
    $row.find("td:eq(5)").children('input').val(suppliedCost);
    $row.find("td:eq(5)").children('input').attr('number',medicineNumber);
    $row.find("td:eq(6)").html('<input class="tableInput" type="datetime-local" name="suppliedDate" form="'+editFormId+'"/>');
    $row.find("td:eq(6)").children('input').val(dateLocal);
    $row.find("td:eq(7)").html('<input class="tableInput" type="text" name="quantity" form="'+editFormId+'"/>');
    $row.find("td:eq(7)").children('input').val(quantity);
    $row.find("td:eq(8)").html('<select id="pharmacist"  name="acceptingPharmacist" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(9)").children('input').attr('form',''+editFormId);
    $submitButton.attr('form',editFormId);

    selectPharmacists($(this).parents("tr"), pharmacistId);
    selectSuppliers($(this).parents("tr"), supplierId);
 });

 $tableID.on('click', '.'+$tableAddClass, () => {
    addNewTr(null);
 });

 $tableID.on('click', '.'+$tableRemoveClass, function () {
    event.preventDefault();
    const $row = $(this).parents('tr');
    $removeButton = $row.find('.'+$tableRemoveClass);
    var invoiceInventoryItemId = $row.find('input[name=invoiceInventoryItemId]').val();
    console.log('invoiceInventoryItemId: '+invoiceInventoryItemId);
    if(invoiceInventoryItemId == null || invoiceInventoryItemId == ''){
        $(this).parents('tr').detach();
    }
    else {
       ajaxRequestDelete($postInvoiceInventoryUrl+"/"+invoiceInventoryItemId, $tableID);
    }

    $row.find('input[name=invoiceInventoryItemId]').attr('form',null);
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

