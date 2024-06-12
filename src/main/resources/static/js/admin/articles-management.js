
const tableBody = document.getElementById('tableBody');
function loadData (data) {
    tableBody.innerHTML = '';
    data.forEach(article => {
        const row = document.createElement('tr');
        const modalId = `exampleModal-${article.id}`;
        row.innerHTML = `
        <td>${article.id}</td>
        <td>${article.title}</td>
        <td>${article.author.fullname}</td>
        <td>${article.createAt}</td>
        <td>${article.updateAt}</td>
        <td>
            <select onchange="updateStatus(${article.id}, this.value)">
                <option value="APPROVED" ${article.status === 'APPROVED' ? 'selected' : ''}>Approved</option>
                <option value="PENDING" ${article.status === 'PENDING' ? 'selected' : ''}>Pending</option>
                <option value="REJECTED" ${article.status === 'REJECTED' ? 'selected' : ''}>Rejected</option>
            </select>
        </td>
        <td>
                <a href="#" id="trigger-modal" onClick="openModal('${modalId}')" >Xem</a>
        </td>
        <!-- Modal -->
        <div class="modal fade" id="${modalId}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document" style="min-width: 900px">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" style="text-align: center; font-size: x-large; font-weight: bold;">${article.title}</h5>
                    </div>
                    <div class="modal-body">
                        <p style="font-size: 16px;" th:text="${article.content}"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" onClick="closeModal('${modalId}')">Close</button>
                    </div>
                </div>
            </div>
        </div>
        `;
        tableBody.appendChild(row);
    });
}
function openModal (modalId) {
    const modal = document.getElementById(modalId);
    $(modal).modal('show');
}

function closeModal (modalId) {
    const modal = document.getElementById(modalId);
    $(modal).modal('close');
}
function updateStatus (articleId, newStatus) {
    console.log('/api/admin/updateArticle/' + articleId + '?status=' + newStatus);
    fetch('/api/admin/updateArticle/' + articleId + '?status=' + newStatus, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                alert('Cập nhật thành công');
            } else {
                alert('Đã có lỗi xảy ra, vui lòng thử lại sau');
            }

        })
        .catch(error => {
            console.error('Error updating status:', error);
        });
}


// load all articles
fetch('/api/admin/getAllArticles')
    .then(response => response.json())
    .then(data => loadData(data))
    .catch(error => console.error('Error fetching data:', error));

// search
const searchBtn = document.querySelector('.btn-search');
searchBtn.addEventListener('click', (e) => {
    const index = document.querySelector('.options').selectedIndex;
    console.log(index);
    switch (index) {
        case 0:
            fetch('/api/admin/getArticleById?id=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 1:
            fetch('/api/admin/getArticleByTitle?title=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 2:
            fetch('/api/admin/getArticleByContent?content=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 3:
            fetch('/api/admin/getArticleByAuthor?author=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
        case 4:
            fetch('/api/admin/getArticleByStatus?status=' + document.querySelector('.search-input').value)
                .then(response => response.json())
                .then(data => loadData(data))
    }
});