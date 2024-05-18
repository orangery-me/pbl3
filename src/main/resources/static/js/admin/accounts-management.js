const tableBody = document.getElementById('tableBody');

function loadData (data) {
    // clear the exisiting table
    tableBody.innerHTML = '';

    data.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
    <td contenteditable="false">${user.id}</td>
    <td contenteditable="false">${user.username}</td>
    <td contenteditable="true">${user.password}</td>
    <td contenteditable="true">${user.email}</td>
    <td contenteditable="true">${user.phone}</td>
    <td contenteditable="true">${user.fullname}</td>
    <td contenteditable="true">${user.gender}</td>
    <td contenteditable="true">${user.birthday}</td>
    <td contenteditable="true">${user.enabled}</td>
    <td contenteditable="true">
        <select class="form-control">
            <option ${user.role === 'PATIENT' ? 'selected' : ''}>PATIENT</option>
            <option ${user.role === 'DOCTOR' ? 'selected' : ''}>DOCTOR</option>
            <option ${user.role === 'ADMIN' ? 'selected' : ''}>ADMIN</option>
        </select>
    </td>
    <td>
            <a href="#" id="trigger-modal">Xem</a>
            <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
    </td>
    `;
        tableBody.appendChild(row);
    });
}
tableBody.addEventListener('click', function (event) {
    const target = event.target;
    if (target.id === 'trigger-modal') {
        const row = target.closest('tr');
        const title = row.children[1].textContent;
        const content = row.children[2].textContent;
        const author = row.children[3].textContent;
        const createAt = row.children[4].textContent;
        var modalTitle = document.getElementById('exampleModalScrollableTitle');
        var modalBody = document.getElementById('exampleModalScrollableBody');
        const modal = document.getElementById('exampleModalScrollable');
        $(modal).modal('show');
        modalTitle.innerHTML = `
            <h4 style="text-align: center; font-weight: bold"> ${title}</h4>
        `;
        modalBody.innerHTML = `
            <p style="text-align: right" >${createAt}</p>
            <p>${content}</p>
            <p style="text-align: right; font-weight: bold">Tác giả: ${author}</p>
        `;
    }
});

// load all users
fetch('/api/admin/getAllUsers')
    .then(response => response.json())
    .then(data => loadData(data))
    .catch(error => console.error('Error fetching data:', error));

// add event listener when change content of table
[...document.getElementsByTagName('td')].forEach(td => td.addEventListener('search', listener));
function listener (e) {
    e.console.log(e.target.textContent);
    e.target.style.setProperty('border', 'yellow');
}

// add event listener when click button
tableBody.addEventListener('click', (e) => {
    // edit user
    const row = e.target.parentElement.parentElement;

    if (e.target.classList.contains('btn-save') || e.target.classList.contains('fa-save')) {
        if (e.target.classList.contains('fa-save')) {
            const row = row.parentElement;
        }

        // get editted text
        const id = row.children[0].textContent;
        const username = row.children[1].textContent;
        const password = row.children[2].textContent;
        const email = row.children[3].textContent;
        const phone = row.children[4].textContent;
        const fullname = row.children[5].textContent;
        const gender = row.children[6].textContent;
        const birthday = row.children[7].textContent;
        const enabled = row.children[8].textContent;
        const role = row.children[9].textContent;

        fetch('/api/admin/updateUser/' + id,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: id,
                    username: username,
                    password: password,
                    email: email,
                    phone: phone,
                    fullname: fullname,
                    gender: gender,
                    birthday: birthday,
                    enabled: enabled,
                    role: role
                })
            })
            .then(response => response.json())
            .catch(error => console.error('Error fetching data:', error));
    }
    // delete user
    if (e.target.classList.contains('btn-remove') || e.target.classList.contains('fa-remove')) {
        if (e.target.classList.contains('fa-remove')) {
            const row = row.parentElement;
        }

        if (confirm('Are you sure you want to delete this user?') == true) {
            fetch('/api/admin/deleteUser/' + row.children[0].innerText)
                .then(() => {
                    window.location.reload();
                });
        }
    }

}
)

// search
const searchBtn = document.querySelector('.btn-search');
searchBtn.addEventListener('click', (e) => {
    const index = document.querySelector('.options').selectedIndex;
    console.log(index);
    switch (index) {
        case 0:
            fetch('/api/admin/getUserById?id=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 1:
            fetch('/api/admin/getUserByEmail?email=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 2:
            fetch('/api/admin/getUserByPhone?phone=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 3:
            fetch('/api/admin/getUserByUsername?username=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
    }
});