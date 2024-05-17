function openImagePicker() {
    let imageUrl = prompt("Vui lòng nhập URL của hình ảnh:");

    if (imageUrl) {
        let contentTextarea = document.getElementById("content");
        contentTextarea.value += `<img src="${imageUrl}" alt="Hình ảnh">`;
    }
}

//Gửi dữ liệu từ form frontend đến REST API endpoint của Spring Boot
function submitArticle() {
    let formData = new FormData(document.getElementById('articleForm'));
    fetch('/api/articles', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert('Article submitted successfully');
                document.getElementById('articleForm').reset(); // Clear form after submission
            } else {
                throw new Error('Error submitting article');
            }
        })
        .catch(error => console.error('Error submitting article:', error));
}


