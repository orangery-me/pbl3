const tableBody = document.getElementById('tableBody');

function loadData (data) {
    console.log(data);
    // clear the exisiting table
    tableBody.innerHTML = '';
    data.forEach(doctor => {
        const row = document.createElement('tr');
        const modalId = `exampleModal-${doctor.id}`;
        row.innerHTML = `
    <td contenteditable="false">${doctor.id}</td>
    <td contenteditable="false">${doctor.fullname}</td>
    <td contenteditable="true">${doctor.birthday}</td>
    <td contenteditable="true">${doctor.departmentName}</td>
    <td contenteditable="true">${doctor.position}</td>
    <td contenteditable="true">${doctor.description}</td>
    <td contenteditable="true">${doctor.roomAddress}</td>
    <td contenteditable="true">${doctor.servicePrices}</td>
    <td>
            <a href="#" id="trigger-modal" onClick="openModal('${modalId}')" >Xem</a>
            <button class="btn btn-remove"><i class="fa fa-remove"></i></button>

            <div class="modal fade" id="${modalId}" tabindex="-1" role="dialog"
            aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="detailAccountModalScrollableTitle">
                            Sửa thông tin
                        </h3>
                        <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="detailAccountModalScrollableBody">
                        <!-- form hiện thông tin -->
                        <form>
                            <div class="form-group row">
                                <label for="inputDes3" class="col-sm-2 col-form-label">ID:
                                </label>
                                <div class="col-sm-10">
                                    <input type="Fullname" class="form-control" id="inputId4"
                                        placeholder="ID" value="${doctor.id}" >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputDes3" class="col-sm-2 col-form-label">Họ tên bác sĩ:
                                </label>
                                <div class="col-sm-10">
                                    <input type="Fullname" class="form-control" id="inputFullname4"
                                        placeholder="Fullname" value="${doctor.fullname}" >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputDes3" class="col-sm-2 col-form-label">Mô tả:
                                </label>
                                <div class="col-sm-10">
                                    <input type="Fullname" class="form-control" id="inputDes4"
                                        placeholder="Description" value="${doctor.description}" >
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputPosition3" class="col-sm-2 col-form-label">Vị
                                    trí
                                </label>
                                <div class="col-sm-10">
                                    <input type="Username" class="form-control"
                                        id="inputPosition4" placeholder="Position" value="${doctor.position}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputDepartment3"
                                    class="col-sm-2 col-form-label">Khoa: </label>
                                <div class="col-sm-10">
                                    <input type="Username" class="form-control"
                                        id="inputDepartment4" placeholder="Department" value="${doctor.departmentName}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputRoom3" class="col-sm-2 col-form-label">Phòng:
                                </label>
                                <div class="col-sm-10">
                                    <input type="Username" class="form-control" id="inputRoom4"
                                        placeholder="Room" value="${doctor.roomAddress}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="inputServicePrice3"
                                    class="col-sm-2 col-form-label">Giá dịch vụ: </label>
                                <div class="col-sm-10">
                                    <input type="Phone" class="form-control"
                                        id="inputServicePrice4" placeholder="Service Prices" value="${doctor.servicePrices}">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer" id="detailAccountModalScrollableFooter">
                        <button type="button" class="btn btn-secondary"
                            data-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-primary"
                            id="save-changes-account">Lưu thay đổi</button>
                    </div>
                </div>
            </div>
        </div>
    </td>
    `;
        tableBody.appendChild(row);
    });
}

function openModal (modalId) {
    const modal = document.getElementById(modalId);
    $(modal).modal('show');
}

// tableBody.addEventListener('click', function (event) {
//     const target = event.target;
//     if (target.id === 'trigger-modal') {
//         const row = target.closest('tr');
//         const title = row.children[1].textContent;
//         const content = row.children[2].textContent;
//         const author = row.children[3].textContent;
//         const createAt = row.children[4].textContent;
//         var modalTitle = document.getElementById('exampleModalScrollableTitle');
//         var modalBody = document.getElementById('exampleModalScrollableBody');
//         const modal = document.getElementById('exampleModalScrollable');
//         $(modal).modal('show');
//         modalTitle.innerHTML = `
//             <h4 style="text-align: center; font-weight: bold"> ${title}</h4>
//         `;
//         modalBody.innerHTML = `
//             <p style="text-align: right" >${createAt}</p>
//             <p>${content}</p>
//             <p style="text-align: right; font-weight: bold">Tác giả: ${author}</p>
//         `;
//     }
// });

// load all users
fetch('/api/clinic/doctors/all')
    .then(response => response.json())
    .then(data => loadData(data))
    .catch(error => console.error('Error fetching data:', error));

// add event listener when click button
// tableBody.addEventListener('click', (e) => {
//     // edit user
//     const row = e.target.parentElement.parentElement;

//     if (e.target.classList.contains('btn-save') || e.target.classList.contains('fa-save')) {
//         if (e.target.classList.contains('fa-save')) {
//             const row = row.parentElement;
//         }

//         // get editted text
//         const id = row.children[0].textContent;
//         const username = row.children[1].textContent;
//         const password = row.children[2].textContent;
//         const email = row.children[3].textContent;
//         const phone = row.children[4].textContent;
//         const fullname = row.children[5].textContent;
//         const gender = row.children[6].textContent;
//         const birthday = row.children[7].textContent;
//         const enabled = row.children[8].textContent;
//         const role = row.children[9].textContent;

//         fetch('/api/admin/updateUser/' + id,
//             {
//                 method: 'PUT',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify({
//                     id: id,
//                     username: username,
//                     password: password,
//                     email: email,
//                     phone: phone,
//                     fullname: fullname,
//                     gender: gender,
//                     birthday: birthday,
//                     enabled: enabled,
//                     role: role
//                 })
//             })
//             .then(response => response.json())
//             .catch(error => console.error('Error fetching data:', error));
//     }
//     // delete user
//     if (e.target.classList.contains('btn-remove') || e.target.classList.contains('fa-remove')) {
//         if (e.target.classList.contains('fa-remove')) {
//             const row = row.parentElement;
//         }

//         if (confirm('Are you sure you want to delete this user?') == true) {
//             fetch('/api/admin/deleteUser/' + row.children[0].innerText)
//                 .then(() => {
//                     window.location.reload();
//                 });
//         }
//     }

// }
// )

// // search
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