{
    const bkmodels = document.querySelectorAll('.view-booking-model')
    const leftClick = document.getElementById('left-click')
    const rightClick = document.getElementById('right-click')

    const shiftHidden = document.querySelectorAll('.shift-hidden-available')
    const date = document.querySelectorAll('.date-and-countAvailable')
    const listShift = document.querySelector('.list-shift')
    const morning = document.querySelector('.morning')
    const afternoon = document.querySelector('.afternoon')
    var inputDate = document.getElementById('input-date')
    var txtMorning = document.querySelector('.txt-morning')
    var txtAfternoon = document.querySelector('.txt-afternoon')
    const btnSubmit = document.querySelector('.btn-submit')
    const form = document.querySelector('.form-hidden')
    const confirm = document.querySelector('.btn-confirm')
    const btnClose = document.querySelector('.btn-closeee')
    const exampleModal = document.getElementById('exampleModal')

    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');
    var currentDate = yyyy + '-' + mm + '-' + dd;

    if(inputDate) inputDate.value = currentDate;


    const maxDate = new Date();
    maxDate.setDate(maxDate.getDate() + 29);
    const maxDateString = maxDate.toISOString().slice(0, 10);

    inputDate && inputDate.setAttribute("min", currentDate);
    inputDate && inputDate.setAttribute("max", maxDateString);

    let plus = 0
    rightClick.onclick = () => {
        if (plus > -3132) {
            plus -= 119 * 3
            bkmodels.forEach((element, index) => {
                element.style.left = plus + 'px'
            })
        }
    }

    leftClick.onclick = () => {
        if (plus < 0) {
            plus += 119 * 3
            bkmodels.forEach((element, index) => {
                element.style.left = plus + 'px'
            })
        }
        console.log(plus)
    }

    inputDate.onchange = () => {
        const a_offset = 3213
        const b_offset = -3213
        var inputDateValue = new Date(inputDate.value)
        var now = new Date().getTime()
        var index = Math.floor((inputDateValue.getTime() - now) / (1000 * 60 * 60 * 24)) + 1

        if (index >= 0 && index <= 29) {
            var current = b_offset + (a_offset - Math.floor(index / 3) * 119 * 3)
            console.log(index)

            plus = current

            bkmodels.forEach(element => element.style.left = current + 'px')

            afternoon.classList.contains('active') && afternoon.classList.remove('active')

            !morning.classList.contains('active') && morning.classList.add('active')

            removeActive()

            date[index].classList.add('active')
            var morningShiftElements = shiftHidden[index].querySelector('.morning-shift')
            var afternoonShiftElements = shiftHidden[index].querySelector('.afternoon-shift')

            var countM = morningShiftElements.querySelectorAll('.shift-item').length
            var countA = afternoonShiftElements.querySelectorAll('.shift-item').length

            txtMorning.textContent = `Sáng (${countM})`
            txtAfternoon.textContent = `Chiều (${countA})`

            console.log(date[index])
            console.log(morningShiftElements)
            listShift.innerHTML = null
            listShift.appendChild(morningShiftElements.cloneNode(true))
            if (countM === 0) {
                listShift.innerHTML = `<div class="full">Không có ca trống</div>`
            }
        }

    }

    function removeActive() {
        date.forEach(date => date.classList.contains('active') && date.classList.remove('active'))
    }


    (function viewShiftOfmorningOrAfternoon() {
        morning.onclick = () => {
            afternoon.classList.remove('active');
            morning.classList.add('active');
            console.log("abcde")
            date.forEach((e, i) => {
                if (e.classList.contains('active')) {
                    var morningShiftElements = shiftHidden[i].querySelector('.morning-shift')
                    var countM = morningShiftElements.querySelectorAll('.shift-item').length

                    listShift.innerHTML = null
                    listShift.appendChild(morningShiftElements.cloneNode(true))

                    if (countM === 0) {
                        listShift.innerHTML = `<div class="full">Không có ca trống</div>`
                    }
                }
            })
        }

        afternoon.onclick = () => {
            morning.classList.remove('active')
            afternoon.classList.add('active')
            console.log("abc")
            date.forEach((e, i) => {
                if (e.classList.contains('active')) {
                    var afternoonShiftElements = shiftHidden[i].querySelector('.afternoon-shift')
                    var countA = afternoonShiftElements.querySelectorAll('.shift-item').length

                    listShift.innerHTML = null
                    listShift.appendChild(afternoonShiftElements.cloneNode(true))

                    if (countA === 0) {
                        listShift.innerHTML = `<div class="full">Không có ca trống</div>`
                    }
                }
            })
        }
    })()

    date.forEach((element, index) => {

        if (element.classList.contains('active')) {
            !morning.classList.contains('active') && morning.classList.add('active')

            var morningShiftElements = shiftHidden[index].querySelector('.morning-shift')
            var afternoonShiftElements = shiftHidden[index].querySelector('.afternoon-shift')

            var countM = morningShiftElements.querySelectorAll('.shift-item').length
            var countA = afternoonShiftElements.querySelectorAll('.shift-item').length

            txtMorning.textContent = `Sáng (${countM})`
            txtAfternoon.textContent = `Chiều (${countA})`


            listShift.innerHTML = null
            listShift.appendChild(morningShiftElements.cloneNode(true))
            if (countM === 0) {
                listShift.innerHTML = `<div class="full">Không có ca trống</div>`
            }
        }

        element.onclick = () => {

            afternoon.classList.contains('active') && afternoon.classList.remove('active')

            !morning.classList.contains('active') && morning.classList.add('active')

            removeActive()
            element.classList.add('active')

            var morningShiftElements = shiftHidden[index].querySelector('.morning-shift')
            var afternoonShiftElements = shiftHidden[index].querySelector('.afternoon-shift')

            var countM = morningShiftElements.querySelectorAll('.shift-item').length
            var countA = afternoonShiftElements.querySelectorAll('.shift-item').length

            txtMorning.textContent = `Sáng (${countM})`
            txtAfternoon.textContent = `Chiều (${countA})`

            listShift.innerHTML = null
            listShift.appendChild(morningShiftElements.cloneNode(true))
            if (countM === 0) {
                listShift.innerHTML = `<div class="full">Không có ca trống</div>`
            }
        }
    })


    listShift.onclick = (e) => {
        var target = e.target
        if (target.classList.contains('shift-item')) {
            listShift.querySelectorAll('.shift-item').forEach(element => {
                element.classList.contains('active') && element.classList.remove('active')
            })
            target.classList.add('active')
        }
    }

    btnSubmit.onclick = () => {
        var listSHiftOnScreen = document.querySelectorAll('.list-shift .shift-item')

        var shiftValue = '';
        var isChooseDate = [...date].find(element => element.classList.contains('active'));
        var isChooseShift = [...listSHiftOnScreen].find(element => element.classList.contains('active'));
        isChooseShift && [...listSHiftOnScreen].forEach((e) => {
            if(e.classList.contains('active')){
                shiftValue = e.textContent;
            }
        })
        const modal = document.querySelector('.modal-body');

        if (isChooseShift && isChooseDate) {
            confirm.style.display = 'block'

            const date = document.getElementById('Date');

            date.value = isChooseDate.dataset.value

            modal.innerHTML = '';
            modal.innerHTML = `<h6>Đặt lịch hẹn vào : Ngày ${date.value} Lúc ${shiftValue}
                                </h6>`
        }else{
            
            modal.innerHTML = '';
            modal.innerHTML = `<h5>Vui lòng chọn lịch hẹn</h5>`
            confirm.style.display = 'none'
        }
        confirm.onclick = () => {
            const ipputIdShift = document.getElementById('id_shift');
            const date = document.getElementById('Date');
            const state = document.getElementById('State');
            const id_doctor = document.getElementById('id_doctor');
            const id_benhnhan = document.getElementById('id_benhnhan');
            const id_benhnhan_toGet = document.getElementById('id_benhnhan_toGet');
            

            id_doctor.value = id_doctor.dataset.value
            id_benhnhan.value = id_benhnhan_toGet.dataset.value
            state.value = state.dataset.value
            date.value = isChooseDate.dataset.value
            ipputIdShift.value = isChooseShift.dataset.value
            
            var _url = form.getAttribute('action');
            var formData = new FormData(form);

            btnClose.click()
            
            setTimeout(() => {
                toast({
                    title: 'Thành công',
                    msg: 'Lịch khám đã được thêm thành công!',
                    type: 'succeces',
                    duration: 7000
                })

                $.ajax({
                    url: _url,
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        console.log("abcd")
                        $("#dynamic-booking-div").html(result);
    
                        $('script[src="/js/handleBookingPage.js"]').remove();
    
                        var script = document.createElement('script');
                        script.src = '/js/handleBookingPage.js'; 
                        script.onload = function () {
                            console.log('Script loaded and executed.');
                        };
                        document.head.appendChild(script);
                    }
                });
            }, 500)
        }
    }

}