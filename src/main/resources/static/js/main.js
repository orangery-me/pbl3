var acc = document.getElementsByClassName("accordion");

window.onload = function() {
    var bodyHeight = document.body.scrollHeight;
    var nav = document.querySelector('nav');
    nav.style.height = bodyHeight + 'px';
};

Array.from(acc).forEach(element => {
    element.addEventListener("click", function() {
        this.classList.toggle("active");

        var panel = element.nextElementSibling
       
        var open = this.childNodes[5]

        var close = this.childNodes[7]

        if (panel.style.maxHeight) {
            open.style.display = 'inline';
            close.style.display = 'none';

            panel.style.maxHeight = null;
        } else {
            panel.style.maxHeight = panel.scrollHeight + "px";
            close.style.display = 'inline';
            open.style.display = 'none';
        }
    });
});
