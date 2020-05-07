var replyManager = (function(){

	var getAll  = function(obj, callback){
		$.getJSON('/replies/'+obj,callback );
	};

	var add = function(obj, callback){
		$.ajax({
			type:'post',
			url: '/replies/'+ obj.bno,
			data:JSON.stringify(obj),
			dataType:'json',
			beforeSend : function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
			},
			contentType: "application/json",
			success:callback
		});
	};

	var update = function(obj, callback){
		$.ajax({
			type:'put',
			url: '/replies/'+ obj.bno,
			dataType:'json',
			data: JSON.stringify(obj),
			contentType: "application/json",
			beforeSend : function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
			},
			success:callback
		});
	};

	var remove = function(obj, callback){
		$.ajax({
			type:'delete',
			url: '/replies/'+ obj.bno+"/" + obj.rno,
			dataType:'json',
			beforeSend : function(xhr){
				xhr.setRequestHeader(obj.csrf.headerName, obj.csrf.token);
			},
			success:callback
		});
	};
	return {
		getAll: getAll,
		add:add,
		update:update,
		remove:remove
	}
})();
