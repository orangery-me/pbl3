function fetchData () {
    fetch("/api/articles/all")
        .then(response => response.json())
        .then(data => {
            const articlesContainer = document.getElementById('articles-container');
            articlesContainer.innerHTML = ''; // Clear any existing content
            console.log(data);
            data.forEach(article => {
                const articleCard = document.createElement('div');
                articleCard.className = 'col-md-4';
                const modalId = `exampleModal-${article.id}`;

                const createdAt = new Date(article.createdAt);
                const formattedDate = createdAt instanceof Date && !isNaN(createdAt) ? createdAt.toLocaleDateString() : 'Unknown';
                articleCard.innerHTML = `
                        <div class="card mb-4" onClick="openModal('${modalId}')">
                            ${article.firstImageUrl ? `<img src="${article.firstImageUrl}" class="card-img-top" alt="Article Image">` : ''}
                            <div class="card-body">
                                <h5 class="card-title">${article.title}</h5>
                               <p class="card-text">${formattedDate}</p>
                                
                            </div>
                        </div>
                        <!-- Modal -->
                        <div class="modal fade" id="${modalId}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                          <div class="modal-dialog" role="document" style="max-width: 900px">
                              <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">${article.title}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                              <div class="modal-body">
                                <p th:text="${article.content}"></p>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                              </div>
                            </div>
                          </div>
                        </div>
                    `;

                articlesContainer.appendChild(articleCard);
            });
            // Add event listeners for close buttons
            document.querySelectorAll('.close-modal-btn').forEach(button => {
                button.addEventListener('click', function () {
                    const modal = this.closest('.modal');
                    $(modal).modal('hide');
                });
            });
        })
        .catch(error => {
            console.error('Error fetching articles:', error);
        });
}

document.addEventListener('DOMContentLoaded', fetchData);

function openModal (modalId) {
    const modal = document.getElementById(modalId);
    $(modal).modal('show');
}

// <div className="modal-header">
//     <h5 className="modal-title" id="exampleModalLabel" th:text="${article.title}"></h5>
// </div>
// <p className="card-text">${new Date(article.createdAt).toLocaleDateString()}</p>