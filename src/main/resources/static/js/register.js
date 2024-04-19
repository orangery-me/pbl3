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

const form1 = document.querySelector('form');
form1.addEventListener('submit',event => {
    event.preventDefault();
    const formData = new  FormData(form1);
    // formData.append(form1);
    const data = Object.fromEntries(formData);
    fetch('http://localhost:8080/register', {
        method:'POST',
        headers:{
            'content-type':'application/json'
          },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
          return response.json(); 
        } else {
          throw new Error('API request failed');
        }
      })
      .then(data => {
        console.log(data); 
      })
      .catch(error => {
        console.error(error); 
        })});