// load all users
fetch('/api/admin/getAllUsers')
    .then(response => response.json())
    .then(data => {
        const tableBody = document.getElementById('tableBody');

        data.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
    <td contenteditable="true" >${user.id}</td>
    <td contenteditable="true" >${user.username}</td>
    <td contenteditable="true" >${user.password}</td>
    <td contenteditable="true" >${user.email}</td>
    <td contenteditable="true" >${user.phone}</td>
    <td contenteditable="true" >${user.fullname}</td>
    <td contenteditable="true" >${user.gender}</td>
    <td contenteditable="true" >${user.birthday}</td>
    <td contenteditable="true" >${user.enabled}</td>
    <td contenteditable="true" >${user.role}</td>
    <td>
            <button class="btn btn-save"><i class="fa fa-save"></i></button>
            <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
    </td>
    `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => console.error('Error fetching data:', error));


tableBody.addEventListener('click', (e) => {
    const btn_save = document.getElementsByClassName("btn-save")[0];
    const row = btn_save.parentElement.parentElement;
    // edit user
    if (e.target.classList.contains('btn-save') || e.target.classList.contains('fa-save')) {
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
        console.log(id, username, password, email, phone)
        // fetch('/api/admin/updateUser/')
    }
    // delete user
    if (e.target.classList.contains('btn-remove') || e.target.classList.contains('fa-remove')) {
        if (confirm('Are you sure you want to delete this user?') == true) {
            fetch('/api/admin/deleteUser/' + row.children[0].innerText)
                .then(() => {
                    window.location.reload();
                });
        }
    }

}
)