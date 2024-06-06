function openImagePicker() {
    //let imageUrl = prompt("Vui lòng nhập URL của hình ảnh:");
    getImageURL();

    if (imageUrl) {
        let contentDiv = document.getElementById("content");
        let imgTag = `<img src="${imageUrl}" alt="Hình ảnh" class="resizable">`;

        // Chèn thẻ <img> vào vị trí con trỏ hiện tại
        insertAtCaret(contentDiv, imgTag);
        enableResize();
    }
}
function getImageURL() {
    let imageUrl = prompt("Vui lòng nhập URL của hình ảnh:");
    return imageUrl;
}

function insertAtCaret(element, html) {
    element.focus();
    if (window.getSelection) {
        let sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            let range = sel.getRangeAt(0);
            range.deleteContents();

            // Tạo một div tạm để chứa HTML
            let el = document.createElement("div");
            el.innerHTML = html;
            let frag = document.createDocumentFragment(), node, lastNode;
            while ((node = el.firstChild)) {
                lastNode = frag.appendChild(node);
            }
            range.insertNode(frag);

            // Di chuyển con trỏ sau nội dung vừa chèn
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    }
}

function enableResize() {
    interact('.resizable')
        .resizable({
            edges: { left: true, right: true, bottom: true, top: true }
        })
        .draggable({  // Thêm khả năng kéo thả
            onmove: function (event) {
                let target = event.target;

                // Lấy vị trí hiện tại của ảnh
                let x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx;
                let y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

                // Cập nhật vị trí mới cho ảnh
                target.style.transform = `translate(${x}px, ${y}px)`;
                target.setAttribute('data-x', x);
                target.setAttribute('data-y', y);
            },
        })
        .on('resizemove', (event) => {
            let target = event.target;

            // Cập nhật kích thước mới cho ảnh
            let rect = event.rect;
            target.style.width = rect.width + 'px';
            target.style.height = rect.height + 'px';
        });
}
function submitArticle() {
    let formData = new FormData(document.getElementById('articleForm'));
    let contentHtml = document.getElementById('content').innerHTML;
    formData.append('content', contentHtml);

    fetch('/api/articles', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert('Article submitted successfully');
                document.getElementById('articleForm').reset();
                document.getElementById('content').innerHTML = ''; // Clear content editable div
            } else {
                throw new Error('Error submitting article');
            }
        })
        .catch(error => console.error('Error submitting article:', error));
}
