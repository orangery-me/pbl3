function openImagePicker() {
    let imageUrl = prompt("Vui lòng nhập URL của hình ảnh:");

    if (imageUrl) {
        let contentDiv = document.getElementById("content");
        let imgTag = `<img src="${imageUrl}" alt="Hình ảnh" class="resizable">`;

        // Chèn thẻ <img> vào vị trí con trỏ hiện tại
        insertAtCaret(contentDiv, imgTag);
        enableResize();
    }
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

            // Di chuyển con tro sau nội dung vừa chènn
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

    // Set default width to 800px and adjust height accordingly for all resizable images
    document.querySelectorAll('.resizable').forEach(img => {
        img.onload = function() {
            let aspectRatio = img.naturalHeight / img.naturalWidth;
            img.style.width = '800px';
            img.style.height = (800 * aspectRatio) + 'px';
        };

        // Nếu ảnh đã tải xong trước khi thêm sự kiện onload
        if (img.complete) {
            img.onload();
        }
    });
}

//lấy userId từ sessionStorage
function submitArticle() {
    const userId = document.getElementById("user").value;
     console.log(userId);
    if (!userId) {
        alert('User not logged in.');
        return;
    }

    let formData = new FormData(document.getElementById('articleForm'));
    let contentHtml = document.getElementById('content').innerHTML;
    formData.append('content', contentHtml);
    formData.append('userId', userId); // Thêm userId vào formData

    console.log(JSON.stringify(Object.fromEntries(formData)));

    fetch('/api/articles/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(Object.fromEntries(formData))
       // credentials: 'same-origin' // Đảm bảo gửi cookie cùng với request
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