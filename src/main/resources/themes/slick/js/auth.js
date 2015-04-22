// Edit Page

$("input:text[name=title]").blur(function() {
	var str = $(this).val();
	var slug = str.replace(/[^a-zA-Z0-9\s]/g, '') 	// Remove non alphanum except whitespace
		.replace(/^\s+|\s+$/, '')      				// Remove leading and trailing whitespace
		.replace(/\s+/g, '-')          				// Replace (multiple) whitespaces with a dash
		.toLowerCase();
	$("input:hidden[name=':name']").val(slug);
});

$("#draft").change(function() {
	var button = document.getElementById('submit-item-button');
    if(this.checked) {
        button.value = "Save";
    } else {
    	button.value = "Post";
    }
});

// Item List
$(".auth-list").on("click", "#item-delete", function() {
	var button = $(this);
	button.siblings(".confirm").toggleClass("horizontal-collapse");
	button.toggleClass("cancel");
});
