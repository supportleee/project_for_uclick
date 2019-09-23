/**
 * 
 */
$(document).on('show.bs.modal', '#phoneModal', function(event) {
	console.log("open modal");
	var button = $(event.relatedTarget); // Button that triggered the modal
	var type = button.data('type'); // Extract info from data-* attributes
	var phone_id = button.data('whatever');
	// If necessary, you could initiate an AJAX request here (and then do the
	// updating in a callback).
	// Update the modal's content. We'll use jQuery here, but you could use a
	// data binding library or other methods instead.
	var modal = $(this);
	modal.find('.modal-title').text("전화기 " + type);
	modal.find('.btn-primary').text(type);
	if (type == "수정") {
		modal.find('#phone_id').val(phone_id);
		$('#phone_id').attr('name','id');
	}
	if(type=="추가") {
		$('#phone_id').removeAttr('name');
	}

	console.log(phone_id);
	$.ajax({
		url : '/json_phone_search',
		method : 'post',
		data : {
			phone_id : phone_id
		},
		success : function(phone) {
			$('#type').val(phone.type);
			$('#tel').val(phone.tel);

		},
		error : function(xhr, ajaxSettings, thrownError) {
		}
	})
});

$(document).on('show.bs.modal', '#userModal', function(event) {
	console.log("open modal");
	var button = $(event.relatedTarget); // Button that triggered the modal
	var type = button.data('type'); // Extract info from data-* attributes
	// If necessary, you could initiate an AJAX request here (and then do the
	// updating in a callback).
	// Update the modal's content. We'll use jQuery here, but you could use a
	// data binding library or other methods instead.
	var modal = $(this);
	modal.find('.modal-title').text("사용자 " + type);
	modal.find('.btn-primary').text(type);
});

$(document).on('hide.bs.modal', '#phoneModal', function(event) {
	$('#type').val('');
	$('#tel').val('');
	$('span').text('');
});


$(document).on('hide.bs.modal', '#userModal', function(event) {
	$('#name').val('');
	$('#department').val('');
	$('#team').val('');
	$('#rank').val('');
	$('#age').val('');
	$('#email').val('');
	$('span').text('');
});