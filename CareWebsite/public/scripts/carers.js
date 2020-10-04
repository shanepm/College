$('[name="btn_delete"]').click(function() {
	var card = $(this).closest('[name="card_carer"]')
	var carer_id = card.data('carer-id')
	bootbox.confirm({
		message: "Are you sure you want to delete this carer?",
		buttons: {
			confirm: {
				label: 'Yes'
			},
			cancel: {
				label: 'No'
			}
		},
		callback: function (shouldDelete) {
			if (!shouldDelete) {
				return
			}
			$.ajax({
				method: 'DELETE',
				url: '/api/carers/' + carer_id + '/invite',
				success: function(response, textStatus, xhr, form) {
					new PNotify({
						title: 'Delete successful',
						text: 'You will be redirected soon.',
						type: 'success'
					})
					document.location.reload()
				},
				error: function(xhr, textStatus, errorThrown) {
					var response = { errors: [] }
					var message = ''
					if (xhr.readyState == 0) {
						message = 'Request timed out.'
					} else {
						try {
							response = JSON.parse(xhr.responseText)
						} catch (e) {
							message = 'Endpoint gave non-JSON response.'
						}
					}
					for (var i = 0; i < response.errors.length; i++) {
						var error = response.errors[i]
						message += error.message
						if (i != response.errors.length - 1) {
							message += '<br/>'
						}
					}
					new PNotify({
						title: 'Delete failed',
						text: message,
						type: 'error'
					})
				}
			})
		}
	})
})