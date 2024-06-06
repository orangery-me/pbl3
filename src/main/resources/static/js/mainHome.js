const NAV_INDEX_STORAGE_KEY = 'INDEX'
const SCROLL_POSITION_KEY = 'VALUE_POSITION'

var navLink = document.querySelectorAll('.link')
var popUp = document.querySelector('.pop-up');
var divStickynav = document.querySelector('.sticky-nav');
var btnClose = document.querySelector('.btn-close');
var btnDK = document.querySelector('#link-popup');
var formCheckToday = document.querySelector('#form-check-today');
var inputCheckToday = document.querySelector('#check-today');

console.log('hello')
console.log('test xem nhé');

const storage =  {
    get() {
        return JSON.parse(localStorage.getItem(NAV_INDEX_STORAGE_KEY));
    },
    set(index) {
        localStorage.setItem(NAV_INDEX_STORAGE_KEY, JSON.stringify(index));
    },
    setPositionScroll(scrollY) {
        localStorage.setItem(SCROLL_POSITION_KEY, JSON.stringify(scrollY));
    },
    getPositionScroll() {
        return JSON.parse(localStorage.getItem(SCROLL_POSITION_KEY));
    },
    delete() {
        localStorage.clear();
    }
}

if(storage.getPositionScroll()){
    window.scrollY = storage.getPositionScroll()
}

document.onscroll = () => {
    console.log(window.scrollY)
    storage.setPositionScroll(window.scrollY)
}

navLink[0].classList.value = 'link active';

for(let i=0; i < navLink.length; i++) {
    if(navLink[i].classList.value === 'link active' && storage.get()){
        navLink[i].classList.remove('active');
        navLink[storage.get()].classList.value = 'link active' 
        break;
    }
}

const _$ = document.querySelector('.link.active');
const lines = document.querySelector('.nav-link .line');

lines.style.left = _$.offsetLeft + 'px';
lines.style.width = _$.offsetWidth + 'px';
lines.style.top = _$.offsetTop + 42 + 'px';
lines.style.display = 'block';

window.addEventListener('resize', function(event) {
    const $$ = document.querySelector('.link.active');
    lines.style.left = $$.offsetLeft + 'px';
    lines.style.width = $$.offsetWidth + 'px';
});

navLink.forEach((link, index) =>{

    link.onclick = () =>{
        if (index == 0) {
            lines.style.display = 'none';
        }
        for (var i = 0; i < navLink.length; i++) {
            if (navLink[i].classList.value.includes('active')) {
                navLink[i].classList.remove('active');
            }
        }
        lines.style.left = navLink[index].offsetLeft + 'px';
        lines.style.width = navLink[index].offsetWidth + 'px';
        
        
        if(link.id === "link-popup"){
            popUp.style.display = 'block';
        }
        link.classList.add('active');
        storage.set(index)
    }
})

document.onclick = function(e){
    if(e.target !== btnDK && !popUp.contains(e.target) && popUp.style.display === 'block'){
        
        console.log(e.target)
        popUp.style.display = 'none';
    }
}

btnClose.onclick = function(e){
    console.log(e.target)
    popUp.style.display = 'none';
}

