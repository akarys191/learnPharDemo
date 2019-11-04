
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
 const $cashRegistryId = $('#cashRegistryId').val();
 const $totalPages = $('#totalPages').val();
 const $markupPercentage = $('#markupPercentage').val();

 $('#pagination-cashRegistry').twbsPagination({
      totalPages: $totalPages == 0 ? 1 : $totalPages,
      visiblePages: $totalPages == 0 ? 1 : $totalPages%5,
      next: $nextPaginationMessage,
      prev: $prevPaginationMessage,
      first: $firstPaginationMessage,
      last: $lastPaginationMessage,
      initiateStartPageClick: false,
      onPageClick: function (event, page) {
        var urlLink = $urlLinklistCashRegistrySales+'?page='+page+'&cashRegistryId='+$cashRegistryId;
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
    <input type="hidden" name="salesCashRegistryId"/>
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
       var addFormIdTest = $rowLast.find('input[name=salesCashRegistryId]').attr('form');
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

        var cashRegistryIdInput = $('#cashRegistryId');
        var cashRegistryId = cashRegistryIdInput.val();
        var medicineName = 'medicineName'+0;
        var medicineId = 'medicineId'+0;
        var medicinePriceId = 'medicinePriceId'+0;
        var priceInputId = 'priceInput'+0;
        var saleQuantityInputId = 'saleQuantityInput'+0;
        var currentIsoDate = getIsoDate(new Date());
        console.log('currentIsoDate: '+currentIsoDate);
        $row.find("td:eq(0)").html('<input id="'+medicineName+'" medicineIdForm="'+medicineId+'" class="tableInput"/>');
        $row.find("td:eq(0)").append('<input id="'+medicineId+'" type="hidden" name="medicine" form="'+addFormId+'"/>');

        $row.find("td:eq(1)").html('<select id="selectCustomer" name="customer" form="'+addFormId+'" class="tableSelect"></select>');
        selectCustomers($('#table tr:last'), null);

        $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="'+addFormId+'" class="tableSelect"></select>');
        selectSuppliers($('#table tr:last'), null);

        $row.find("td:eq(3)").html('<select id="selectCashType" name="cashType" form="'+addFormId+'" class="tableSelect"></select>');
        selectCashTypes($('#table tr:last'), null);

        $row.find("td:eq(4)").html('<input id="'+priceInputId+'" class="tableInput" name="price" form="'+addFormId+'"/>');
         if(medicine != null){
             $row.find("td:eq(0)").children('input[id='+medicineId+']').val(medicine.id);
             $row.find("td:eq(0)").children('input[id="'+medicineName+'"]').val(medicine.name);
             $row.find("td:eq(4)").children('input').val(medicine.price);
         }

        $row.find("td:eq(5)").html('<input id="'+saleQuantityInputId+'" class="tableInput" name="saleQuantity" form="'+addFormId+'"/>');
        $row.find("td:eq(5)").children('input').attr('number',0);

        $row.find("td:eq(6)").html('<input class="tableInput" name="soldSum" form="'+addFormId+'"/>');

        $row.find("td:eq(7)").html('<input class="tableInput" type="datetime-local" name="soldDate" form="'+addFormId+'"/>');
        $row.find("td:eq(7)").children('input').val(currentIsoDate);

        var $rowCol8 =  $row.find("td:eq(8)");
        if(currentPharmacistRef.roles.includes("ADMIN")){
            console.log('sellingPharmacist');
            console.log(currentPharmacistRef);
            $rowCol8.html('<select id="pharmacist"  name="sellingPharmacist" form="'+addFormId+'" class="tableSelect"></select>');
            selectPharmacists($rowCol8.parents("tr"), currentPharmacistRef.id);
         } else {
            $rowCol8.removeClass($editWarningClass);
            $rowCol8.text(currentPharmacistRef.name);
            $rowCol8.append('<input id="'+medicineId+'" type="hidden" name="sellingPharmacist" form="'+addFormId+'"/>');
            $rowCol8.children('input').val(currentPharmacistRef.id);
         }
         $row.find("td:eq(9)").children('input').attr('form',''+addFormId);
         $row.find('input[name=invoiceInventoryItemId]').attr('form',addFormId)
         $DIVCARD.append('<input type="hidden" name="invoice" form="'+addFormId+'" value="'+cashRegistryId+'"/>');
         $submitButton.attr('form',addFormId);
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

    var cashRegistryIdInput = $('#cashRegistryId');
	var cashRegistryId = cashRegistryIdInput.val();
    var components = $('input[form="'+editFormId+'"]');
    if(components.length > 0){
       editFormNumber=editFormNumber+1;
       editFormId = 'edit_form' + editFormNumber;
       $DIVCARD.append('<form method="POST" id="'+editFormId+'"><input type="hidden" name="cashRegistry" value="'+cashRegistryId+'"/></form>');
    } else {
       $DIVCARD.append('<input type="hidden" name="invoice" form="edit_form" value="'+cashRegistryId+'"/>');
    }

    $row.find("td:eq(1)").removeClass($editWarningClass);
	var medicineId = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineId]').val();
	var medicineName = $(this).parents("tr").find("td:eq(0)").children('input[id=medicineName]').val();
	var customerId = $(this).parents("tr").find("td:eq(1)").children('input').val();
	var supplierId = $(this).parents("tr").find("td:eq(2)").children('input').val();
	var price = $(this).parents("tr").find("td:eq(3)").text();
	var saleQuantity = $(this).parents("tr").find("td:eq(4)").text();
	var soldSum = $(this).parents("tr").find("td:eq(5)").text();
    var cashType = $(this).parents("tr").find("td:eq(6)").text();
    var dateLocal = $(this).parents("tr").find("td:eq(7)").text();
	var pharmacistId = $(this).parents("tr").find("td:eq(8)").children('input').val();

    console.log("dateLocal: "+dateLocal);
    var medicineNumber = editFormNumber+1;
    var medicineNameId = 'medicineName'+medicineNumber;
    var medicineIdId = 'medicineId'+medicineNumber ;
    var markupPercentageInputId = 'markupPercentageInput'+medicineNumber ;
    var priceInputId = 'priceInput'+medicineNumber ;
    var saleQuantityInputId = 'saleQuantityInput'+medicineNumber ;
    $row.find("td:eq(0)").html('<input id="'+medicineNameId+'" medicineIdForm="'+medicineIdId+'" class="tableInput"/>');
    $row.find("td:eq(0)").append('<input id="'+medicineIdId+'" type="hidden" name="medicine" form="'+editFormId+'"/>');
    $row.find("td:eq(0)").children('input[id='+medicineNameId+']').val(medicineName);
    $row.find("td:eq(0)").children('input[id='+medicineIdId+']').val(medicineId);
    $row.find("td:eq(1)").html('<select id="selectCustomer" name="customer" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(2)").html('<select id="selectSupplier" name="supplier" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(3)").html('<select id="selectCashType" name="supplier" form="'+editFormId+'" class="tableSelect"></select>');
    $row.find("td:eq(4)").html('<input id="'+priceInputId+'" class="tableInput" name="price" form="'+editFormId+'"/>');
    $row.find("td:eq(4)").children('input').val(price);
    $row.find("td:eq(5)").html('<input id="'+saleQuantityInputId+'" class="tableInput" name="saleQuantity" form="'+editFormId+'"/>');
    $row.find("td:eq(5)").children('input').val(saleQuantity);
    $row.find("td:eq(5)").children('input').attr('number',medicineNumber);
    $row.find("td:eq(6)").html('<input class="tableInput" type="text" name="soldSum" form="'+editFormId+'"/>');
    $row.find("td:eq(6)").children('input').val(soldSum);
    $row.find("td:eq(7)").html('<input class="tableInput" type="datetime-local" name="soldDate" form="'+editFormId+'"/>');
    $row.find("td:eq(7)").children('input').val(dateLocal);
    $submitButton.attr('form',editFormId);
    $row.find("td:eq(8)").html('<select id="sellingPharmacist"  name="sellingPharmacist" form="'+editFormId+'" class="tableSelect"></select>');
    selectPharmacists($(this).parents("tr"), pharmacistId);
    selectSuppliers($(this).parents("tr"), supplierId);
    selectCustomers($(this).parents("tr"), customerId);
    selectCashTypes($(this).parents("tr"), cashTypeId);

    $row.find("td:eq(9)").children('input').attr('form',''+editFormId);

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

$tableID.on('change', '#selectSupplier', function () {
      var $medicineId = $(this).parents("tr").find("td:eq(0)").children("input[name='medicine']").val();
      var $supplierId = this.value;
            console.log('out');
            console.log('out');
      if($medicineId!=='null' && $medicineId!=='undefined' && $medicineId!==null && $medicineId!==''){
            console.log($supplierId);
            console.log($medicineId);
        $.ajax({
              dataType: "json",
              url:$medicinePriceUrl,
              data: {
                 supplierId: $supplierId,
                 medicineId: $medicineId
                 },
                 success:function(data) {
                  messagePrompt('data: '+data);
              },
              error:function(data) {
                  if(data.status == 404){
                     messagePrompt($notFoundPriceException);
                  } else {
                     messagePrompt(data.statusText);
                  }
                  console.log(data);
              }
         });
      }
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

