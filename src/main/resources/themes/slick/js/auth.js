$("input:text[name=title]").blur(function() {
	var str = $(this).val();
	var slug = str.replace(/[^a-zA-Z0-9\s]/g, '') 	// Remove non alphanum except whitespace
		.replace(/^\s+|\s+$/, '')      				// Remove leading and trailing whitespace
		.replace(/\s+/g, '-')          				// Replace (multiple) whitespaces with a dash
		.toLowerCase();
	$("input:hidden[name=':name']").val(slug);
});