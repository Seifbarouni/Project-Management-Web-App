let draggables = document.querySelectorAll(".draggable")
let containers = document.querySelectorAll(".container")
const loading = document.getElementById("loading")
let projectid = document.getElementsByTagName("main")[0].id
let draggingId


draggables.forEach(draggable => {
    draggable.addEventListener('dragstart', () => {
        draggingId = draggable.parentElement.id
        draggable.classList.add('dragging')
    })

    draggable.addEventListener('dragend', () => {
        draggable.classList.remove('dragging')
    })

    draggable.addEventListener('click', () => {
        draggable.parentNode.lastElementChild.classList.remove("hidden")
    })
})

containers.forEach(container => {
    container.addEventListener('dragover', e => {
        e.preventDefault()
        const afterElement = getDragAfterElement(container, e.clientY)
        const draggable = document.querySelector('.dragging')
        if (afterElement == null) {
            container.appendChild(draggable)
        } else {
            container.insertBefore(draggable, afterElement)
        }

    })

    container.addEventListener('drop', () => {
        const draggable = document.querySelector('.dragging')
        if (container.id != draggingId) {
            loading.style.visibility = "visible"
            let payload = {
                id: draggable.id,
                projectId: projectid,
                description: draggable.children[0].children[0].innerHTML,
                isTask: false,
                isInProgress: false,
                isInReview: false,
                isDone: false,
            }
            switch (container.id) {
                case "tasks":
                    payload.isTask = true;
                    break;
                case "In progress":
                    payload.isInProgress = true;
                    break;
                case "In review":
                    payload.isInReview = true;
                    break;
                case "Done":
                    payload.isDone = true;
                    break;

                default:
                    break;
            }
            // PUT REQUEST
            async function UpdateData() {
                let res = await fetch(`http://localhost:9001/tasks/${draggable.id}`, {
                    method: "PUT",
                    headers: {
                        'Content-type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                })
                if (res.ok) {
                    let txt = await res.text()
                    return txt
                } else {
                    return `HTTP error: ${res.status}`
                }
            }

            UpdateData().then(data => {
                if (data != "Success" && data != "") {
                    alert(data)
                }
            })
            updateEventListener()
            payload = null
            setTimeout(() => {
                location.reload()
            }, 1000)
        }
    })
})


function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')]
    if (draggableElements.length === 0) {
        return null;
    }

    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect()
        const offset = y - box.top - box.height / 2
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child }
        } else {
            return closest
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element
}

function updateEventListener() {
    draggables = document.querySelectorAll(".draggable")
    draggables.forEach(draggable => {
        draggable.addEventListener('dragstart', () => {
            draggable.classList.add('dragging')
        })

        draggable.addEventListener('dragend', () => {
            draggable.classList.remove('dragging')
        })
    })
    containers = document.querySelectorAll(".container")
}