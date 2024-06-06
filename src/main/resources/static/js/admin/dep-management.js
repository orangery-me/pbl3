const tableBody = document.getElementById('tableBody');
function loadData (data) {
    tableBody.innerHTML = '';
    data.forEach(dep => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${dep.id}</td>
        <td>${dep.nameDepartment}</td>
        <td>${dep.descriptionDepartment}</td>
        <td>${dep.location}</td>
        <td>
                <a href="#" id="show-deps">Xem</a>
                <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
        </td>
        `;
        tableBody.appendChild(row);
    });
}

// Mở bảng thêm khoa mới
const newAccountBtn = document.getElementById('trigger-modal-new-account');
newAccountBtn.addEventListener('click', function (event) {
    const modal = document.getElementById('newDepartmentModalScrollable');
    $(modal).modal('show');
});

// Lưu khoa mới thêm
const saveNewAccountBtn = document.getElementById('save-new-dep');
saveNewAccountBtn.addEventListener('click', function (event) {
    const name = document.getElementById('departmentName3').value;
    console.log(name);
    const des = document.getElementById('departmentDescription3').value;
    console.log(des);
    const loc = document.getElementById('location3').value;
    console.log(loc);
    const form = document.getElementById('form').value;
    const formData = new FormData(form, saveNewAccountBtn);

    fetch('/api/clinic/departments/newDepartment',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nameDepartment: name,
                descriptionDepartment: des,
                location: loc
            })
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Đã có lỗi xảy ra, vui lòng thử lại sau');
            }
        }).catch(error => console.error('Error fetching data:', error))
});

// Xem chi tiết bài viết
tableBody.addEventListener('click', function (event) {
    const target = event.target;
    if (target.id === 'show-deps') {
        const row = target.closest('tr');
        const depName = row.children[1].textContent;
        const depDes = row.children[2].textContent;
        const loc = row.children[3].textContent;
        var modalTitle = document.getElementById('newDepartmentModalScrollableTitle');
        var modalBody = document.getElementById('newDepartmentModalScrollableBody');
        const modal = document.getElementById('newDepartmentModalScrollable');
        $(modal).modal('show');
        modalTitle.innerHTML = `
            <h4 style="text-align: center; font-weight: bold">Thông tin về khoa ${depName}</h4>
        `;
        modalBody.innerHTML = `
            <p style="text-align: left">Mô tả: ${depDes}</p>
            <p style="text-align: left">Địa điểm: ${loc}</p>
        `;
    }
});


// load all articles
fetch('/api/clinic/departments/all')
    .then(response => response.json())
    .then(data => loadData(data))
    .catch(error => console.error('Error fetching data:', error));

// search
const searchBtn = document.querySelector('.btn-search');
searchBtn.addEventListener('click', (e) => {
    const index = document.querySelector('.options').selectedIndex;
    console.log(index);
    // switch (index) {
    //     case 0:
    //         fetch('/api/admin/getArticleById?id=' + document.querySelector('.search-input').value)
    //             .then(response => response.json())
    //             .then(data => loadData(data))
    //     case 1:
    //         fetch('/api/admin/getArticleByTitle?title=' + document.querySelector('.search-input').value)
    //             .then(response => response.json())
    //             .then(data => loadData(data))
    //     case 2:
    //         fetch('/api/admin/getArticleByContent?content=' + document.querySelector('.search-input').value)
    //             .then(response => response.json())
    //             .then(data => loadData(data))
    //     case 3:
    //         fetch('/api/admin/getArticleByAuthor?author=' + document.querySelector('.search-input').value)
    //             .then(response => response.json())
    //             .then(data => loadData(data))
    //     case 4:
    //         fetch('/api/admin/getArticleByStatus?status=' + document.querySelector('.search-input').value)
    //             .then(response => response.json())
    //             .then(data => loadData(data))
    // }
});