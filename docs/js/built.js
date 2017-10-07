function onButtonSubmit() {
    debugger;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (isNullOrWhitespace(email) || isNullOrWhitespace(password)) {
        alert("Neither the email or password can be empty or whitespace");
        return;
    }

    
}

function isNullOrWhitespace( input ) {

    if (typeof input === 'undefined' || input == null) return true;

    return input.replace(/\s/g, '').length < 1;
}
