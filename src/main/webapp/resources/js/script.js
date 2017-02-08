function applyMaskTelefone(){
	if (jQuery('.telefoneStyle')){
		var element, phone;
		element = jQuery('.telefoneStyle');
        element.unmask();
        
        phone = element.val().replace("_", "");
        
        if(phone.length > 14) {
            element.mask("(99) 99999-999?9");
        } else {
            element.mask("(99) 9999-9999?9");
        }
        
    }
}