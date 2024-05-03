
const tableBody = document.getElementById('tableBody');
function loadData (data) {
    tableBody.innerHTML = '';
    data.forEach(article => {
        const row = document.createElement('tr');
        row.innerHTML = `
        <td>${article.id}</td>
        <td>${article.title}</td>
        <td>${article.content}</td>
        <td>${article.author.username}</td>
        <td>${article.createAt}</td>
        <td>${article.updateAt}</td>
        <td contenteditable='true'>${article.status}</td>
        <td>
                <a href="#" id="show-article">Xem</a>
                <button class="btn btn-remove"><i class="fa fa-remove"></i></button>
        </td>
        `;
        tableBody.appendChild(row);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // click to show the article
    const showArticle = document.getElementById('show-article');
    const popup = document.getElementsByClassName('js-model');

    showArticle.addEventListener('click', function (event) {
        event.preventDefault();
        popup.style.display = 'block';
    });
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