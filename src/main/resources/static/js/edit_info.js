document.getElementById("form-2").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission
  
    // Fetch the input values
    var u = document.getElementById("username").value;
    var m = document.getElementById("fullname").value;
    var  e = document.getElementById("email").value;
    var p = document.getElementById("password").value;
    var  c = document.getElementById("birthday").value;
    var sdt = document.getElementById("sdt").value ;

    fetch("http://localhost:8080/edit", {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: u,
            fullname: m,
            email: e,
            birthday: c,
            password: p,
            telephone: sdt
        })
    })
        .then(response => {
            if (!response.ok) {
                alert("Update information no successful!");
                throw new Error("Failed to authenticate");
            }
            return response.json();
        })
        .then(data => {
            alert("Update information successful!");
            window.location.href = "http://localhost:8080/home";
            console.log("Authentication successful:", data);
        })
        .catch(error => {
            alert("Please try again later!");
            console.error("Authentication error:", error.message);
    });
  });