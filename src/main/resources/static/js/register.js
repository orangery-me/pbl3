var radioButton = document.getElementsByClassName('btn-radio')
var checkbox = document.querySelector('.checkbox')
var Imgs = document.querySelectorAll('.first-img')

checkbox.onclick = () => {
    Array.from(radioButton).forEach((e) => {
        if (e.checked) {
            e.nextElementSibling.classList.add('active')
        }
        if (!e.checked) {
            e.nextElementSibling.classList.remove('active')
        }
    })
}

var img1 = Imgs[0]
var img2 = Imgs[1]
var img3 = Imgs[2]
var img4 = Imgs[3]

var left = -100
setInterval(()=>{
    left -= 1225
    if (left === -5000){
        left = -100
    }
    img1.style.left = left + 'px'
    img2.style.left = left + 'px'
    img3.style.left = left + 'px'
    img4.style.left = left + 'px'
    console.log(left)
}, 4000)
document.getElementById("form-1").addEventListener("submit", function (event) {
  event.preventDefault(); // Prevent default form submission

  // Fetch the input values
  var u = document.getElementById("username").value;
  var m = document.getElementById("fullname").value;
  var  e = document.getElementById("email").value;
  var p = document.getElementById("password").value;
  var  c = document.getElementById("birthday").value;
  fetch("http://localhost:8080/register", {
      method: 'POST',
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify({
          username: u,
          fullname: m,
          email: e,
          birthday: c,
          password: p
      })
  })
      .then(response => {
          if (!response.ok) {
              alert("This account already existed");
              throw new Error("Failed to authenticate");
          }
          return response.json();
      })
      .then(data => {
          alert("Register successful!");
          window.location.href = "http://localhost:8080/home";
          console.log("Authentication successful:", data);
      })
      .catch(error => {
          alert("Error with registration! Please try again later.");
          console.error("Authentication error:", error.message);
      });
});