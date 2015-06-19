// Edit Page

$("input:text[name=title]").blur(function() {
	var str = $(this).val();
	var slug = str.replace(/[^a-zA-Z0-9-\s]/g, '') 	// Remove non alphanum except whitespace
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

// Check Checkboxes for default post servlet (seems to not support the default checked param)
function validateCheckbox(checkbox) {
	if (checkbox.checked) {
	    checkbox.setAttribute("value","true");
	} else {
		checkbox.removeAttribute("value");
	    checkbox.removeAttribute("checked");
	}
}

$(".clever-input").on("focus", "input", function() {
	var imp = $(this);
	imp.next().addClass("has-value");
});

$(".clever-input").on("focusout", "input", function() {
	var imp = $(this);
	var label = imp.next();
	if(!imp.val()) {
		label.removeClass("has-value");
		if(imp.parent().hasClass("required")) {
			label.addClass("red");
		}
	} else {
		label.removeClass("red");
	}
});

$(".validate").submit(function( event ) {
	event.preventDefault();
	var stuff = $(".required > input").filter(function( index ) {
	    return $(this).val().length === 0;
	});
	if (!stuff.length) {
		this.submit();
	} else {
		console.log(stuff.length + " Don't Submit");
	}
});