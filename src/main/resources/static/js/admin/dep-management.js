
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
                <a href="#" id="show-article">Xem</a>
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