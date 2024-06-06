// const tableBody = document.getElementById('tableBody');

function loadData (data) {
    // clear the exisiting table
    tableBody.innerHTML = '';

    data.forEach(schedule => {
        const row = document.createElement('tr');
        row.innerHTML = `
    <td>${schedule.id}</td>
    <td>${schedule.date}</td>
    <td>${schedule.state}</td>
    <td>${schedule.doctorName}</td>
    <td>${schedule.location}</td>
    <td>${schedule.patientId}</td>
    <td>${schedule.patientName}</td>
    <td>${schedule.shiftId}</td>
    <td>${schedule.timeStart}</td>
    <td>${schedule.timeEnd}</td>
    <td>
            <a href="#" id="trigger-modal-see-detail">Xem</a>
            <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
    </td>
    `;
        tableBody.appendChild(row);
    });
}

// Hiện ra bảng xem thông tin chi tiết
var row;
tableBody.addEventListener('click', function (event) {
    const target = event.target;
    console.log('')
    if (target.id === 'trigger-modal-see-detail') {
        row = target.closest('tr');
        const username = row.children[1].textContent;
        const password = row.children[2].textContent;
        const email = row.children[3].textContent;
        const phone = row.children[4].textContent;
        const fullname = row.children[5].textContent;
        const gender = row.children[6].textContent;
        const dob = row.children[7].textContent;
        const enabled = row.children[8].textContent;
        const role = row.children[9].textContent;
        console.log(role, enabled);

        document.getElementById('inputUsername4').value = username;
        document.getElementById('inputPassword4').value = password;
        document.getElementById('inputEmail4').value = email;
        document.getElementById('inputPhone4').value = phone;
        document.getElementById('inputFullname4').value = fullname;
        const form = document.getElementById('gender-form4');
        if (gender == 'true') {
            form.elements[1].checked = true;
        } else {
            form.elements[0].checked = true;
        }
        document.getElementById('birthday4').value = dob;
        document.getElementById('role-form-control4').value = role;
        document.getElementById('gridCheck4').checked = true;
        const modal = document.getElementById('detailAccountModalScrollable');
        $(modal).modal('show');

    }
});

// Lưu thay đổi trong bảng xem thông tin
// const saveChangesBtn = document.getElementById('save-changes-account');
// saveChangesBtn.addEventListener('click', e => {
//     console.log('save changes newkk')
//     // get editted text
//     const id = row.children[0].textContent;
//     const username = document.getElementById('inputUsername4').value;
//     const password = document.getElementById('inputPassword4').value;
//     const email = document.getElementById('inputEmail4').value;
//     const phone = document.getElementById('inputPhone4').value;
//     const fullname = document.getElementById('inputFullname4').value;
//     const dob = document.getElementById('birthday4').value;

//     const form = document.getElementById('gender-form4');
//     var gender = true;
//     if (form.elements[0].checked) {
//         gender = false
//     }
//     const role = document.getElementById('role-form-control4').value;
//     const enabled = document.getElementById('gridCheck4').checked;

//     fetch('/api/admin/updateUser/' + id,
//         {
//             method: 'PUT',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({
//                 id: id,
//                 username: username,
//                 password: password,
//                 email: email,
//                 phone: phone,
//                 fullname: fullname,
//                 gender: gender,
//                 birthday: dob,
//                 enabled: enabled,
//                 role: role
//             })
//         }).then(response => {
//             if (response.ok) {
//                 alert('Cập nhật thành công');
//                 window.location.reload();
//             } else {
//                 alert('Đã có lỗi xảy ra, vui lòng thử lại sau');
//             }
//         }).catch(error => console.error('Error fetching data:', error));

// });

function fetchDepartment () {
    fetch('/api/clinic/departments/all')
        .then(response => response.json()) // Step 2: Parse the response data
        .then(data => {
            // Step 3: Create new <option> elements
            const options = data.map(department => {
                const option = document.createElement('option');
                option.value = department.id; // Replace 'id' with the actual key for the value
                option.textContent = department.nameDepartment; // Replace 'name' with the actual key for the display text
                return option;
            });

            // Step 4: Append the new <option> elements to the <select> element
            const selectElement = document.getElementById('department-form-control');
            // Remove all existing <option> elements
            selectElement.innerHTML = '';
            options.forEach(option => selectElement.appendChild(option));
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

// function fetchDepartment () {
//     fetch('/api/clinic/shifts/all')
//         .then(response => response.json()) // Step 2: Parse the response data
//         .then(data => {
//             // Step 3: Create new <option> elements
//             const options = data.map(shift => {
//                 const option = document.createElement('option');
//                 option.value = shift.id; // Replace 'id' with the actual key for the value
//                 option.textContent = shift.id; // Replace 'name' with the actual key for the display text
//                 return option;
//             });

//             // Step 4: Append the new <option> elements to the <select> element
//             const selectElement = document.getElementById('shift-form-control');
//             // Remove all existing <option> elements
//             selectElement.innerHTML = '';
//             options.forEach(option => selectElement.appendChild(option));
//         })
//         .catch(error => {
//             console.error('Error fetching data:', error);
//         });
// }

function fetchDoctor () {
    const selectedDepartment = document.getElementById('department-form-control').value;
    console.log(selectedDepartment);
    if (selectedDepartment === '') {
        return;
    }
    fetch('/api/clinic/doctors?departmentId=' + selectedDepartment).then(response => response.json())
        .then(data => {
            const options = data.map(doctor => {
                const option = document.createElement('option');
                option.value = doctor.id; // Replace 'id' with the actual key for the value
                option.textContent = doctor.nameDoctor; // Replace 'name' with the actual key for the display text
                return option;
            });

            // Step 4: Append the new <option> elements to the <select> element
            const selectElement = document.getElementById('doctor-form-control');
            selectElement.innerHTML = '';
            options.forEach(option => selectElement.appendChild(option));
        }).catch(error => {
            console.error('Error fetching data:', error);
        });
}

// Mở bảng thêm lịch mới 
function openNewScheduleModal () {
    const modal = document.getElementById('newScheduleModalScrollable');
    $(modal).modal('show');
}

function saveNewSchedule () {
    const form = document.getElementById('newScheduleForm');
    const formData = new FormData(form);
    formData.forEach((value, key) => {
        console.log(key, value);
    });

    // const username = document.getElementById('inputUsername3').value;
    // const password = document.getElementById('inputPassword3').value;
    // const email = document.getElementById('inputEmail3').value;
    // const phone = document.getElementById('inputPhone3').value;

    var gender = true;
    if (form.elements[0].checked) {
        gender = false
    }
    const role = document.getElementById('role-form-control').value;
    fetch('/api/auth/register',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ formData })
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                if (response.status === 400) {
                    alert('Vui lòng nhập đầy đủ thông tin và đúng định dạng');
                } else {
                    alert('Đã có lỗi xảy ra, vui lòng thử lại sau');
                }
            }
        }).catch(error => console.error('Error fetching data:', error))
}
// Lưu tài khoản mới -----
// const saveNewAccountBtn = document.getElementById('save-new-account'); // save-new-schedule
// saveNewAccountBtn.addEventListener('click', function (event) {
//     const username = document.getElementById('inputUsername3').value;
//     const password = document.getElementById('inputPassword3').value;
//     const email = document.getElementById('inputEmail3').value;
//     const phone = document.getElementById('inputPhone3').value;
//     const fullname = document.getElementById('inputFullname3').value;
//     const dob = document.getElementById('birthday').value;

//     const form = document.getElementById('gender-form');
//     var gender = true;
//     if (form.elements[0].checked) {
//         gender = false
//     }
//     const role = document.getElementById('role-form-control').value;
//     fetch('/api/auth/register',
//         {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({
//                 username: username,
//                 password: password,
//                 birthday: dob,
//                 gender: gender,
//                 email: email,
//                 phone: phone,
//                 fullname: fullname,
//                 role: role
//             })
//         }).then(response => {
//             if (response.ok) {
//                 window.location.reload();
//             } else {
//                 if (response.status === 400) {
//                     alert('Vui lòng nhập đầy đủ thông tin và đúng định dạng');
//                 } else {
//                     alert('Đã có lỗi xảy ra, vui lòng thử lại sau');
//                 }
//             }
//         }).catch(error => console.error('Error fetching data:', error))
// });


// Load tất cả tài khoản để đổ vào bảng
fetch('/api/clinic/schedules/all')
    .then(response => response.json())
    .then(data => loadData(data))
    .catch(error => console.error('Error fetching data:', error));


// Xóa một tài khoản
tableBody.addEventListener('click', (e) => {
    const deletedRow = e.target.parentElement.parentElement;

    if (e.target.classList.contains('btn-remove') || e.target.classList.contains('fa-remove')) {
        if (e.target.classList.contains('fa-remove')) {
            const deletedRow = deletedRow.parentElement;
        }

        if (confirm('Are you sure you want to delete this user?') == true) {
            fetch('/api/admin/deleteUser/' + deletedRow.children[0].innerText)
                .then(() => {
                    window.location.reload();
                });
        }
    }

}
);

// // Tìm kiếm
// const searchBtn = document.querySelector('.btn-search');
// searchBtn.addEventListener('click', (e) => {
//     const index = document.querySelector('.options').selectedIndex;
//     console.log(index);
//     switch (index) {
//         case 0:
//             fetch('/api/admin/getUserById?id=' + document.querySelector('.search-input').value)
//                 .then(response => response.json())
//                 .then(data => loadData(data))
//         case 1:
//             fetch('/api/admin/getUserByEmail?email=' + document.querySelector('.search-input').value)
//                 .then(response => response.json())
//                 .then(data => loadData(data))
//         case 2:
//             fetch('/api/admin/getUserByPhone?phone=' + document.querySelector('.search-input').value)
//                 .then(response => response.json())
//                 .then(data => loadData(data))
//         case 3:
//             fetch('/api/admin/getUserByUsername?username=' + document.querySelector('.search-input').value)
//                 .then(response => response.json())
//                 .then(data => loadData(data))
//     }
// });