
const tableBody = document.getElementById('tableBody');
function loadData (data) {
    tableBody.innerHTML = '';
    data.forEach(article => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${article.id}</td>
        <td>${article.title}</td>
        <td>${article.content}</td>
        <td>${article.author.fullname}</td>
        <td>${article.createAt}</td>
        <td>${article.updateAt}</td>
        <td contenteditable='true'>${article.status}</td>
        <td>
                <a href="#" id="trigger-modal">Xem</a>
                <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
        </td>
        `;
        tableBody.appendChild(row);
    });
}
// Use event delegation to bind the click event to the tableBody element
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