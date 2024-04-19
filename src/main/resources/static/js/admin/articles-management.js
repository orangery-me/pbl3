fetch('/api/admin/getAllArticles')
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
