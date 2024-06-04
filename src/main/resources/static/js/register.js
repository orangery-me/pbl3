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


