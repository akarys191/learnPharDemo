const $tableID = $('#table');
 const $BTN = $('#export-btn');
 const $EXPORT = $('#export');

 const newTr = `
<tr class="hide">
  <td class="pt-3-half"></td>
                     <td class="pt-3-half" ></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half"></td>
                     <td class="pt-3-half">
    <span class="table-up"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-up" aria-hidden="true"></i></a></span>
    <span class="table-down"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-down" aria-hidden="true"></i></a></span>
  </td>
  <td>
   <span class="table-edit" style="display: none;"><button type="button"><i class="fas fa-edit"  style="font-size:20px;color:red" aria-hidden="true"></i></button></span>
   <span class="table-submit"><button type="button"><i class="fa fa-plus" style="color:red" aria-hidden="true"></i></button></span>
   <span class="table-remove"><button type="button"><i class="fa fa-times-circle"  style="color:red" aria-hidden="true"></i></button></span>
  </td>
</tr> `;

$tableID.find('.table-remove').hide();
$tableID .find('.table-submit').hide();


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
 });

 $tableID.on('click', '.table-remove', function () {

   $(this).parents('tr').detach();
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